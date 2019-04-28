package com.example.gamedemo.server;

import com.example.gamedemo.server.game.account.model.Account;
import com.example.gamedemo.server.game.account.service.AccountManager;
import com.example.gamedemo.server.game.common.IController;
import com.example.gamedemo.server.game.manager.ControllerManager;
import com.example.gamedemo.server.game.scene.model.Scene;
import com.example.gamedemo.server.game.scene.service.SceneManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 服务端
 *
 * @author junaha
 * @date 2019/04/25
 */

public class MyServer {
    private static final Logger logger = LoggerFactory.getLogger(MyServer.class);

    /**
     * 初始化spring容器
     * <p>
     * public static ApplicationContext ac = null;
     * <p>
     * static {
     * ac = new ClassPathXmlApplicationContext("applicationContext.xml");
     * }
     * <p>
     * public static ApplicationContext getAc() {
     * return ac;
     * }
     */
    @Autowired
    private ControllerManager controllerManager;


    public void start(String[] args) {
        initServer();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                    pipeline.addLast("decoder", new StringDecoder());
                    pipeline.addLast("encoder", new StringEncoder());
                    pipeline.addLast("handler", new SimpleChannelInboundHandler<String>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

                            logger.info("===>receive msg:" + msg);
                            //执行指令分发

                            ControllerManager controllerManager = new ControllerManager();
                            //根据指令获取当前的指令多对应的controller
                            IController controller = controllerManager.get(msg.split(" ")[0]);
                            String returnMsg = "指令有误";
                            if (controller != null) {
                                returnMsg = controllerManager.execute(controller, msg).toString();
                            }
                            ctx.channel().writeAndFlush("server return msg:" + returnMsg + "\r\n");
                        }
                    });
                }
            });
            ChannelFuture future = bootstrap.bind(7777).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 初始化
     */
    private void initServer() {
        //初始化账户
        Account account = new Account();
        account.setCountId("a1001");
        account.setCountName("tom");

        Account account1 = new Account();
        account1.setCountId("a1002");
        account1.setCountName("jerry");

        AccountManager.setAccount(account);
        AccountManager.setAccount(account1);

        //初始化场景
        Scene scene = new Scene();
        scene.setSceneId("s1001");
        scene.setSceneName("村落");
        scene.setNeighbors("a1002");

        Scene scene1 = new Scene();
        scene1.setSceneId("s1002");
        scene1.setSceneName("山洞");
        scene1.setNeighbors("s1001,s1003");

        Scene scene2 = new Scene();
        scene2.setSceneId("s1003");
        scene2.setSceneName("城堡");
        scene2.setNeighbors("s1002");

        SceneManager.setScene(scene);
        SceneManager.setScene(scene1);
        SceneManager.setScene(scene2);
    }

}
