package pers.krpc.core;


import pres.krpc.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * krpc
 * 2022/7/25 15:31
 *
 * @author wangsicheng
 * @since
 **/
public class ApplicationContext {

    private final Map<String, InterfaceContext> context;

    ApplicationContext() {
        this.context = new HashMap<>();
    }

    public InterfaceContext getInterfaceContext(String interfaceName) {
        return context.get(interfaceName);
    }

    public void addInterfaceContext(InterfaceContext interfaceContext) {
        context.put(interfaceContext.getPath(), interfaceContext);
    }


}
