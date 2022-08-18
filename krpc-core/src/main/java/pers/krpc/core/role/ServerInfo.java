package pers.krpc.core.role;


import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;

/**
 * krpc服务ip信息
 * 2022/7/25 15:50
 *
 * @author wangsicheng
 **/
@Data
public class ServerInfo {

    private String ip;

    private String port;

    private String timeOut;

    public static ServerInfo build(String s) {
        String[] strings = s.split(":");
        return ServerInfo.build().setIp(strings[0]).setPort(strings[1]).setTimeOut(strings[2]);
    }

    public static ServerInfo build(Instance instance) {
        Map<String, String> map = instance.getMetadata();
        String timeOut = map.getOrDefault(Role.Customer.name(), map.getOrDefault(Role.Provider.name(), null));
        return ServerInfo.build().setIp(instance.getIp()).setPort(String.valueOf(instance.getPort())).setTimeOut(timeOut);
    }

    public static ServerInfo build() {
        return new ServerInfo();
    }

    public ServerInfo setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public ServerInfo setPort(String port) {
        this.port = port;
        return this;
    }

    public int getPortByInt() {
        return StringUtils.isEmpty(this.port) ? 0 : Integer.parseInt(this.port);
    }

    public ServerInfo setTimeOut(String timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public ServerInfo getCopyByTimeOut(String timeOut) {
        return ServerInfo.build().setIp(this.ip).setPort(this.port).setTimeOut(timeOut);
    }

    public String toString() {
        return this.ip + ":" + this.port + ":" + this.timeOut;
    }

    public String toStringV2() {
        return this.ip + ":" + this.port;
    }

}
