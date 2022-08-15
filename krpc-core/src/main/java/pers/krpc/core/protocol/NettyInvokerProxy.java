package pers.krpc.core.protocol;


import io.netty.channel.Channel;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import pers.krpc.core.InterfaceContextDetails;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.protocol.netty.NettyApplicationContext;
import pers.krpc.core.role.Provider;
import pers.krpc.core.role.ServerInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * krpc
 * 2022/8/12 14:57
 *
 * @author wangsicheng
 * @since
 **/
@Slf4j
public class NettyInvokerProxy implements InvocationHandler {

    private final InterfaceContextDetails interfaceContextDetails;

    private final Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    public NettyInvokerProxy(InterfaceContextDetails interfaceContextDetails) {
        this.interfaceContextDetails = interfaceContextDetails;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Channel channel = getChannel(interfaceContextDetails.getProviderList());
        InterfaceInfo interfaceInfo = interfaceContextDetails.getInterfaceInfo();
        KrpcMsg krpcMsg = KrpcMsg.build(interfaceInfo.getInterfaceClass(), method, args, interfaceInfo.getVersion());
        Promise<Object> promise = new DefaultPromise<>(new DefaultEventLoop());
        NettyApplicationContext.getMSG_CACHE().put(krpcMsg.getUniqueIdentifier(), promise);
        channel.writeAndFlush(krpcMsg);
        return promise.get(Long.parseLong(interfaceInfo.getTimeout()), TimeUnit.MILLISECONDS);
    }

    private Channel getChannel(List<Provider> providerList) {
        if (Objects.isNull(providerList) || providerList.isEmpty()) {
            log.info("该接口未找到生产者");
            return NettyApplicationContext.getChannel("127.0.0.1", 8082);
        }
        int index = new Random().nextInt(providerList.size());
        Provider provider = providerList.get(index);
        Channel channel = channelMap.get(provider.toChannelKey());
        if (Objects.isNull(channel)) {
            ServerInfo serverInfo = provider.getServerInfo();
            channel = NettyApplicationContext.getChannel(serverInfo.getIp(), Integer.parseInt(serverInfo.getPort()));
            channelMap.put(provider.toChannelKey(), channel);
        }
        return channel;
    }
}
