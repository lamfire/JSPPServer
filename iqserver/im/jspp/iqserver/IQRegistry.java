package im.jspp.iqserver;

import com.lamfire.logger.Logger;
import com.lamfire.utils.ClassLoaderUtils;
import com.lamfire.utils.Maps;
import im.jspp.iqserver.annotation.NS;

import java.util.Map;
import java.util.Set;


/**
 * IQ注册处
 * User: lamfire
 * Date: 13-11-8
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 */
public class IQRegistry {
    private static final Logger LOGGER = Logger.getLogger(IQRegistry.class);
    private static final String SERVICE_PACKAGE_NAME = "im.jspp";
    private static final IQRegistry instance = new IQRegistry();

    public static IQRegistry get(){
        return  instance;
    }

    private final Map<String,IQHandler> serviceRegistry = Maps.newHashMap();

    private IQRegistry(){
        registerPackage(SERVICE_PACKAGE_NAME);
    }

    /**
     * 注册包下的所有IQ服务
     * @param packageName
     */
    public void registerPackage(String packageName){
        try {
            Set<Class<?>> classes = ClassLoaderUtils.getClasses(packageName);
            for(Class<?> clazz : classes){
                NS s = clazz.getAnnotation(NS.class);
                if(s != null){
                    String nsname = s.name();
                    register(nsname,(IQHandler)clazz.newInstance());
                    LOGGER.info("Registry IQ:["+ nsname +"] to " + clazz.getName());
                }
            }
        }catch (Throwable e) {
            LOGGER.warn(e);
        }
    }

    /**
     * 注册单个IQ服务
     * @param ns
     * @param handler
     */
    public void register(String ns,IQHandler handler){
         if(handler != null){
             serviceRegistry.put(ns,handler);
         }
    }

    /**
     * 查找IQ服务
     * @param ns
     * @return
     */
    public IQHandler lookup(String ns){
        return serviceRegistry.get(ns);
    }
}
