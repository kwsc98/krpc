package pers.krpc.core.protocol.netty;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Promise;
import pers.krpc.core.KrpcApplicationContext;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * krpcNettyNettyApplicationContext
 * 2022/8/12 15:37
 * @author wangsicheng
 **/
public class NettyApplicationContext {
    /**
     * netty客户端
     **/
    private final static NettyClient NETTY_CLIENT;
    /**
     * netty服务端
     **/
    private NettyService nettyService;

    /**
     * 采用共享变量的方式，进行异步回调处理
     **/
    public static final Cache<String, Promise<Object>> MSG_CACHE = Caffeine.newBuilder().expireAfterAccess(10000L, TimeUnit.MILLISECONDS).build();


    static {
        NETTY_CLIENT = new NettyClient();
    }
    /**
     * 通过客户端获取Channel
     **/
    public static Channel getChannel(String host, int port) {
        return NETTY_CLIENT.getChannel(host, port);
    }
    /**
     * 初始化服务端
     **/
    public void initService(int port, KrpcApplicationContext krpcApplicationContext) {
        //防止初始化多个netty服务
        NettyService tempNettyService = this.nettyService;
        this.nettyService = new NettyService(port,krpcApplicationContext);
        if (Objects.nonNull(tempNettyService)) {
            tempNettyService.close();
        }
    }


}
