package im.jspp.iqserver.annotation;

import java.lang.annotation.*;

/**
 * IQ NS注解
 * User: lamfire
 * Date: 13-11-8
 * Time: 下午5:06
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE })
public @interface NS {
    public abstract String name();
}
