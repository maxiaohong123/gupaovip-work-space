package com.gupaoedu.vip.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.awt.*;

/**
 * Created by Maxiaohong on 2018-11-10.
 */
public class Server {
    public static final String IP = "127.0.0.1";
    public static final int PORT  = 6666;
    public static void main(String[] args) {
        //创建两个EventLoopGroup对象
        //创建boss线程组，用于服务端接受客户端的请求
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //创建work线程组，用于进行SocketChannel的数据读写
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //创建ServerBootstrap对象
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置使用的EventLoopGroup
            serverBootstrap.group(bossGroup,workGroup)
                    //设置要被实例化为的NioServerSocketChannel类
                    .channel(NioServerSocketChannel.class)
                    //设置连入服务端的client的SocketChannel的处理器
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline cp = channel.pipeline();
                            //添加帧限定符来防止粘包现象
                            cp.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                            //解码和编码应和客户端一致
                            cp.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            cp.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            //业务逻辑实现类
                            cp.addLast(new TcpServerHandler());
                        }
                    });
            //绑定端口，并同步等待成功，即启动服务端
         ChannelFuture channelFuture =  serverBootstrap.bind(IP,PORT).sync();
         //监听服务端关闭，并阻塞等待，如果不加sync，即服务端会启动自动关闭
         channelFuture.channel().closeFuture().sync();
            System.out.println("服务端启动...");

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
