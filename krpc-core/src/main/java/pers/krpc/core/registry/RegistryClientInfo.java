package pers.krpc.core.registry;


import lombok.Data;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;

/**
 * krpc
 * 2022/7/28 17:09
 *
 * @author wangsicheng
 * @since
 **/
@Data
public class RegistryClientInfo {

    private String ip;

    private Client client;

    public static RegistryClientInfo build(){
        return new RegistryClientInfo();
    }

    public RegistryClientInfo setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public RegistryClientInfo setClient(Client client) {
        this.client = client;
        return this;
    }

    public static enum Client{
        /**
         * Zookeeper
         **/
        Zookeeper
    }


}
