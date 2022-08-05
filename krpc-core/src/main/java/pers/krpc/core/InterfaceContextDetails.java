package pers.krpc.core;


import lombok.Getter;
import pers.krpc.core.role.Customer;
import pers.krpc.core.role.Provider;
import pers.krpc.core.role.Role;
import pers.krpc.core.role.ServerInfo;

import java.util.List;

/**
 * krpc
 * 2022/7/27 15:20
 *
 * @author wangsicheng
 * @since
 **/

public class InterfaceContextDetails {

    @Getter
    protected InterfaceInfo interfaceInfo;

    @Getter
    protected Role role;


    protected Object object;

    protected List<Provider> providerList;

    protected List<Customer> customerList;

    public Object getObject() {
        return this.object;
    }

    public static InterfaceContextDetails build(InterfaceInfo interfaceInfo) {
        return new InterfaceContextDetails().setInterfaceInfo(interfaceInfo);
    }

    public InterfaceContextDetails setInterfaceInfo(InterfaceInfo interfaceInfo) {
        this.interfaceInfo = interfaceInfo;
        return this;
    }

    public InterfaceContextDetails setObject(Object object) {
        this.object = object;
        return this;
    }

    public InterfaceContextDetails setProviderList(List<Provider> providerList) {
        this.providerList = providerList;
        return this;
    }

    public InterfaceContextDetails setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
        return this;
    }

    public InterfaceContextDetails setRole(Role role) {
        this.role = role;
        return this;
    }

    public String getPreNodePath() {
        return "/" + interfaceInfo.getInterfaceClass().getName() + "/" + interfaceInfo.getVersion();
    }

    public String getNodePath() {
        ServerInfo serverInfo = KrpcApplicationContext.getServerInfo().getCopyByTimeOut(interfaceInfo.getTimeout());
        return getPreNodePath() + "/" + role + "/" + serverInfo.toString();
    }

}
