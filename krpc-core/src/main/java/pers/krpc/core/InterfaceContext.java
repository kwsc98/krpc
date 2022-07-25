package pers.krpc.core;


import lombok.Getter;
import pers.krpc.core.role.Customer;
import pers.krpc.core.role.Provider;

import java.util.List;
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
    private String path;

    private Map<String,InterfaceContext> interfaceContextMap;

    public static class InterfaceContextDetails{

        private String version;

        private List<Provider> providerList;

        private List<Customer> customerList;

    }
}
