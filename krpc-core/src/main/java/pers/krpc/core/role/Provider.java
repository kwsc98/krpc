package pers.krpc.core.role;


/**
 * krpc
 * 2022/7/25 15:46
 *
 * @author wangsicheng
 * @since
 **/
public class Provider {

    private ServerInfo serverInfo;

    public static Provider build(String s){
        String[] strings = s.split(":");
        Provider provider = new Provider();
        provider.serverInfo = ServerInfo.build().setIp(strings[0]).setPort(strings[1]).setTimeOut(strings[2]);
        return provider;
    }

}
