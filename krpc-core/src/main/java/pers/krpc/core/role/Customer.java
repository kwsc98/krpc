package pers.krpc.core.role;


import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.Getter;

import java.util.Map;

/**
 * krpc消费者
 * 2022/7/25 15:47
 *
 * @author wangsicheng
 **/
public class Customer {

    @Getter
    private ServerInfo serverInfo;

    public static Customer build(ServerInfo serverInfo){
        return new Customer().setServerInfo(serverInfo);
    }

    public Customer setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
        return this;
    }

    public String toChannelKey() {
        return serverInfo.toStringV2();
    }

    public static Customer build(String s) {
        return build(ServerInfo.build(s));
    }

    public static Customer build(Instance instance) {
        return build(ServerInfo.build(instance));
    }
}
