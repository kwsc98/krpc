package pers.krpc.core;


import lombok.Getter;
import org.springframework.beans.factory.FactoryBean;

import java.util.Map;

/**
 * krpc
 * 2022/7/25 15:38
 *
 * @author wangsicheng
 * @since
 **/
public class InterfaceContext{

    @Getter
    private Class<?> path;

    public Map<String, InterfaceContextDetails> interfaceContextMap;

    public Object getObject(InterfaceInfo interfaceInfo) {
        return interfaceContextMap.get(interfaceInfo.getVersion()).getObject();
    }

    public static InterfaceContext build(Class<?> path){
        return new InterfaceContext().setPath(path);
    }

    public InterfaceContext setPath(Class<?> path) {
        this.path = path;
        return this;
    }


}
