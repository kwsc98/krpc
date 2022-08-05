package pers.krpc.core;


import lombok.Getter;
import org.springframework.beans.factory.FactoryBean;

import java.util.HashMap;
import java.util.Map;

/**
 * krpc
 * 2022/7/25 15:38
 *
 * @author wangsicheng
 * @since
 **/
public class InterfaceContext {

    @Getter
    private Class<?> path;
    @Getter
    public Map<String, InterfaceContextDetails> interfaceContextMap;

    public Object getObject(InterfaceInfo interfaceInfo) {
        return interfaceContextMap.get(interfaceInfo.getVersion()).getObject();
    }

    public boolean contains(InterfaceInfo interfaceInfo){
        return interfaceContextMap.containsKey(interfaceInfo.getVersion());
    }

    public void put(String key, InterfaceContextDetails value) {
        interfaceContextMap.put(key, value);
    }

    public static InterfaceContext build(Class<?> path) {
        return new InterfaceContext().setPath(path).setInterfaceContextMap(new HashMap<>());
    }

    public InterfaceContext setPath(Class<?> path) {
        this.path = path;
        return this;
    }

    public InterfaceContext setInterfaceContextMap(Map<String, InterfaceContextDetails> interfaceContextMap) {
        this.interfaceContextMap = interfaceContextMap;
        return this;
    }
}
