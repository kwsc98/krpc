package pers.krpc.core.role;


/**
 * krpc
 * 2022/7/25 15:47
 *
 * @author wangsicheng
 * @since
 **/
public class Customer {

    private ServerInfo serverInfo;

    public static Customer build(String s){
        String[] strings = s.split(":");
        Customer customer = new Customer();
        customer.serverInfo = ServerInfo.build().setIp(strings[0]).setPort(strings[1]).setTimeOut(strings[2]);
        return customer;
    }
}
