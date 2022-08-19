package pres.krpc.spring.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static jdk.nashorn.internal.runtime.Version.version;

/**
 * @author kwsc98
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface KrpcResource {

    String version() default "1.0.0";

    long timeout() default 1000;


}
