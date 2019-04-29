package com.example.gamedemo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author: wengj
 * @date: 2019/4/28
 * @description: 游戏客户端
 */
public class MyClient {
    private static final Logger logger = LoggerFactory.getLogger(MyClient.class);

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                    pipeline.addLast("decoder", new StringDecoder());
                    pipeline.addLast("encoder", new StringEncoder());
                    pipeline.addLast("handler", new SimpleChannelInboundHandler<String>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                            System.out.println(msg);
                        }



                    });
                }
            });

            Channel ch = b.connect("127.0.0.1", 7777).sync().channel();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("================请输入指令===================");
            System.out.println("账户：create->创建(create a1003 user03)；login->登陆(login a1001)；get->账户信息(get a1002)");
            System.out.println("场景：list->所有场景(list)；goto->进入场景(goto a1001 s1001)");
            System.out.println("=============================================");

            String line = null;
            while (true) {
                line = in.readLine();
                if ("close".equals(line)) {
                    break;
                }
                ch.writeAndFlush(line + '\n');
            }
            ch.close().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}