package pers.krpc.core.registry;


import lombok.Data;

/**
 * krpc注册中心参数
 * 2022/7/28 17:09
 *
 * @author wangsicheng
 **/
@Data
public class RegistryClientInfo {

    private String serverAddr;

    private Client client;

    public static RegistryClientInfo build(){
        return new RegistryClientInfo();
    }

    public RegistryClientInfo setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
        return this;
    }

    public RegistryClientInfo setClient(String client) {
        this.client = Client.getClient(client);
        return this;
    }

    public enum Client{
        /**
         * Zookeeper
         * Nacos
         **/
        Zookeeper,
        Nacos;

        public static Client getClient(String s){
            if(Nacos.name().equalsIgnoreCase(s)){
                return Nacos;
            }
            return Zookeeper;
        }
    }


}
