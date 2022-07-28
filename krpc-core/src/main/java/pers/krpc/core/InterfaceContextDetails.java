package pers.krpc.core;


import lombok.Data;
import org.springframework.beans.factory.FactoryBean;
import pers.krpc.core.role.Customer;
import pers.krpc.core.role.Provider;

import java.util.List;

/**
 * krpc
 * 2022/7/27 15:20
 *
 * @author wangsicheng
 * @since
 **/

public class InterfaceContextDetails {

    protected InterfaceInfo interfaceInfo;

    protected Object object;

    protected List<Provider> providerList;

    protected List<Customer> customerList;

    public Object getObject() {
        return this.object;
    }

    public static InterfaceContextDetails build(InterfaceInfo interfaceInfo){
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
}
