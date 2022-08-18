package pres.krpc.spring;


import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * krpc
 * 2022/8/18 17:51
 *
 * @author wangsicheng
 * @since
 **/
@ConfigurationProperties(prefix = "krpc")
@Data
public class KrpcProperties {

    private String registeredPath;

    private String port;

}
