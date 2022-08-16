package pers.krpc.core.protocol.netty;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import io.netty.channel.Channel;
import io.netty.util.concurrent.Promise;
import lombok.Getter;
import pers.krpc.core.KrpcApplicationContext;


import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * krpc
 * 2022/8/12 15:37
 *
 * @author wangsicheng
 **/
public class NettyApplicationContext {

    private final static NettyClient NETTY_CLIENT;

    private NettyService nettyService;


    @Getter
    private static final Cache<String, Promise<Object>> MSG_CACHE = Caffeine.newBuilder().expireAfterAccess(1000L, TimeUnit.MILLISECONDS).build();


    static {
        NETTY_CLIENT = new NettyClient();
    }

    public static Channel getChannel(String host, int port) {
        return NETTY_CLIENT.getChannel(host, port);
    }

    public void initService(int port, KrpcApplicationContext krpcApplicationContext) {
        NettyService tempNettyService = this.nettyService;
        this.nettyService = new NettyService(port,krpcApplicationContext);
        if (Objects.nonNull(tempNettyService)) {
            tempNettyService.close();
        }
    }


}
