package pres.krpc.spring.annotation;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author kwsc98
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Resource
public @interface KrpcService {

    String version() default "1.0.0";

    String timeout() default "1000";

}
