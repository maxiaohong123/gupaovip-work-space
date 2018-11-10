package com.gupaoedu.vip.netty.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetAddress;
import java.util.Date;

/**
 * Created by Maxiaohong on 2018-11-10.
 */
public class TcpServerHandler extends ChannelInboundHandlerAdapter {

    //每个客户端建立连接时，发送一条庆祝消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
          ctx.write("Welcome to "+ InetAddress.getLocalHost().getHostName()+"!/r/n");
          ctx.write("Today is "+new Date()+"  now./r/n");
          ctx.flush();
    }

    //核心业务逻辑处理
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server accept message:"+msg);
        ctx.channel().writeAndFlush("accept message"+msg);
        ctx.close();
    }


   //连接服务异常时的处理
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
        System.out.println("get server exception"+throwable.getMessage());
    }
}
