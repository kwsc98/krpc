package pres.krpc.example.customer.controller;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import pers.krpc.core.protocol.netty.NettyInitializer;
import pers.krpc.core.protocol.netty.NettyServerHandler;

/**
 * krpc
 * 2022/8/9 16:48
 *
 * @author wangsicheng
 * @since
 **/
@Slf4j
public class NettyService {

    public void init(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new NettyInitializer<>(new NettyServerHandler()));
            Channel channel = b.bind(port).sync().channel();
            log.info("server channel:{}", channel);
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("创建netty服务异常");
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }




    public static void main(String[] args) {
        NettyService nettyService = new NettyService();
        nettyService.init(8082);
    }

}
