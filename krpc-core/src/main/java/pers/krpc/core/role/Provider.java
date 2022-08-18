package pers.krpc.core.role;


import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.Getter;

import java.util.Map;

/**
 * krpc生产者
 * 2022/7/25 15:46
 *
 * @author wangsicheng
 **/
public class Provider {
    @Getter
    private ServerInfo serverInfo;

    public static Provider build(ServerInfo serverInfo){
        return new Provider().setServerInfo(serverInfo);
    }

    public Provider setServerInfo(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
        return this;
    }

    public String toChannelKey() {
        return serverInfo.toStringV2();
    }

    public static Provider build(String s) {
        return build(ServerInfo.build(s));
    }

    public static Provider build(Instance instance) {
        return build(ServerInfo.build(instance));
    }

}
