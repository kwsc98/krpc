package pers.krpc.core;


import lombok.Getter;
import pers.krpc.core.role.Customer;
import pers.krpc.core.role.Provider;
import pers.krpc.core.role.Role;
import pers.krpc.core.role.ServerInfo;

import java.util.List;

/**
 * krpc服务接口详细Context
 * 2022/7/27 15:20
 *
 * @author wangsicheng
 **/

public class InterfaceContextDetails {

    @Getter
    protected InterfaceInfo interfaceInfo;

    @Getter
    protected Role role;

    @Getter
    protected List<Provider> providerList;
    @Getter
    protected List<Customer> customerList;

    protected Object object;

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

    public void setProviderList(List<Provider> providerList) {
        this.providerList = providerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
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
