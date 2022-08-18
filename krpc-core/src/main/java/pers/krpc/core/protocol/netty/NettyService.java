package pers.krpc.core.protocol.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import pers.krpc.core.KrpcApplicationContext;

/**
 * 服务端
 * 2022/8/9 16:48
 *
 * @author wangsicheng
 **/
@Slf4j
public class NettyService {
    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;

    public NettyService(int port, KrpcApplicationContext krpcApplicationContext) {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new NettyInitializer<>(new NettyServerHandler(krpcApplicationContext)));
            Channel channel = b.bind(port).sync().channel();
            log.info("server channel:{}", channel);
        } catch (Exception e) {
            log.error("创建netty服务异常");
        }
    }

    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
