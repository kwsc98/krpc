package pers.krpc.core.role;


import lombok.Getter;

/**
 * krpc
 * 2022/7/25 15:50
 *
 * @author wangsicheng
 * @since
 **/
@Getter
public class ServerInfo {

    private String ip;

    private String port;

    private String timeOut;

    public static ServerInfo build(){
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

    public ServerInfo setTimeOut(String timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public ServerInfo getCopyByTimeOut(String timeOut){
        return ServerInfo.build().setIp(this.ip).setPort(this.port).setTimeOut(timeOut);
    }

    public String toString(){
        return this.ip+":"+this.port+":"+this.timeOut;
    }
}
