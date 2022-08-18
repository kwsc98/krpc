package pers.krpc.core.proxy;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import pers.krpc.core.InterfaceContextDetails;
import pers.krpc.core.InterfaceInfo;
import pers.krpc.core.protocol.NettyInvokerProxy;
import java.lang.reflect.Method;

/**
 * krpc 动态代理处理类
 * 2022/8/5 15:10
 *
 * @author wangsicheng
 **/
@Slf4j
public class ProxyService {

    public static Object getProxy(InterfaceContextDetails interfaceContextDetails) {
        InterfaceInfo interfaceInfo = interfaceContextDetails.getInterfaceInfo();
        log.info("使用cglib代理目标:[{}]", interfaceInfo.getInterfaceClass().getName());
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(interfaceInfo.getInterfaceClass());
        //setCallbacks,支持多重代理
        enhancer.setCallback(new CglibProxyInterceptor(interfaceContextDetails));
        return enhancer.create();
    }


    private static class CglibProxyInterceptor implements MethodInterceptor {
        //在这个拦截器里放一个过滤器链 SpringCglibProxy
        NettyInvokerProxy nettyInvokerProxy;

        public CglibProxyInterceptor(InterfaceContextDetails interfaceContextDetails) {
            this.nettyInvokerProxy = new NettyInvokerProxy(interfaceContextDetails);
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return this.nettyInvokerProxy.invoke(o, method, objects);
        }
    }
}
