package com.lamfire.jsppserver.anno;

import java.lang.annotation.*;

/**
 * Created by linfan on 2017/9/13.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target( { java.lang.annotation.ElementType.TYPE })
public @interface NS {
    public abstract String name();
}
