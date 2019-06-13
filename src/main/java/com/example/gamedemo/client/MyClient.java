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
      b.handler(
          new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
              ChannelPipeline pipeline = ch.pipeline();
              pipeline.addLast(
                  "framer", new DelimiterBasedFrameDecoder(1024 * 8, Delimiters.lineDelimiter()));
              pipeline.addLast("decoder", new StringDecoder());
              pipeline.addLast("encoder", new StringEncoder());

              pipeline.addLast(
                  "handler",
                  new SimpleChannelInboundHandler<String>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, String msg)
                        throws Exception {
                      logger.info(msg);
                    }
                  });
            }
          });

      Channel ch = b.connect("127.0.0.1", 7777).sync().channel();
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      System.out.println("==========请输入指令===========");
      System.out.println("创建账户：createAccount a2002 user2002");
      System.out.println("登陆账户：loginAccount a2002");
      System.out.println("创建玩家：createPlayer p2001");
      System.out.println("选择玩家：selectPlayer p2001");
      System.out.println("查看背包：showBag");
      System.out.println("新增道具：addItem i1001");
      System.out.println("使用道具：useItem guid");
      System.out.println("道具数量：getItemNum guid");
      System.out.println("穿上装备：equip guid");
      System.out.println("脱下装备：unEquip position");
      System.out.println("查看装备：getEquip guid");
      System.out.println("查看装备栏：showBar");
      System.out.println("查看账户：get");
      System.out.println("所在场景：where");
      System.out.println("所有场景：list");
      System.out.println("传送到场景：goto s2002 ");
      System.out.println("去相邻场景：move s2002 ");
      System.out.println("场景实例：aoi");
      System.out.println("退出账户：logout");
      System.out.println("===============================");

      String line = null;

      while (true) {
        line = in.readLine();
        ch.writeAndFlush(line + "\r\n");
        if ("logout".equals(line)) {
          break;
        }
      }
      ch.close().sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      group.shutdownGracefully();
    }
  }
}
