package com.lamfire.jsppserver;

import com.lamfire.jsppserver.anno.NS;
import com.lamfire.logger.Logger;
import com.lamfire.utils.AnnotationUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * NS 注册器
 */
public class NSRegistry {
    private static final Logger LOGGER = Logger.getLogger(NSRegistry.class);
    private final Map<String,NSService> mapping = new HashMap<String, NSService>();

    public void register(Class<? extends NSService> nsClass){
        NS nsAnno = nsClass.getAnnotation(NS.class);
        if(nsAnno == null){
            LOGGER.warn("["+nsClass.getName() + "] is assignable from Action,but not found 'NS' annotation.");
            return;
        }
        String name = nsAnno.name();
        try {
            NSService instance = nsClass.newInstance();
            mapping.put(name, instance);
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }
    }

    public NSService lookup(String name){
        return mapping.get(name);
    }

    public void registerPackage(String packageName){
        Set<Class<?>> set = AnnotationUtils.getClassesWithAnnotation(packageName,NS.class);
        for(Class<?> clzz : set){
            if(!NSService.class.isAssignableFrom(clzz)){
                continue;
            }
            Class<? extends NSService> nsClass =  (Class<? extends NSService>)clzz;
            register(nsClass);
        }
    }

    public boolean isEmpty(){
        return mapping.isEmpty();
    }
}
