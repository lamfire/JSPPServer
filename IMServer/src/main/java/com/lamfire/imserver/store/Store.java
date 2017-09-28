package com.lamfire.imserver.store;

import com.lamfire.code.Base64;
import com.lamfire.code.PUID;
import com.lamfire.imserver.IMMessageStore;
import com.lamfire.jspp.JSPP;
import com.lamfire.jspp.JSPPUtils;
import com.lamfire.logger.Logger;
import com.lamfire.utils.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Store
 * Created by linfan on 2017/9/26.
 */
public class Store implements IMMessageStore {
    private static final Logger LOGGER = Logger.getLogger(Store.class);
    private static final String DATA_FILE_NAME = "message";
    private final Map<String,Data> datas = Maps.newConcurrentMap();

    private String dir;
    private Index index;


    public Store(String dir){
        this.dir = dir;
        FileUtils.makeDirs(dir);
        this.index = new Index(dir);
        try {
            this.index.open();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(),e);
        }
    }

    public void add(String key, JSPP value){
        PUID puid = PUID.make();
        Data data = getOrCreateData(puid.getTime(),true);
        String did = puid.toString();
        data.add(did,encode(value));
        this.index.add(key,did);
    }

    private String encode(JSPP jspp){
        byte[] bytes = JSPPUtils.encode(jspp);
        return Base64.encode(bytes);
    }

    private JSPP decode(String data){
        byte[] bytes = Base64.decode(data);
        return JSPPUtils.decode(bytes);
    }

    public void remove(String key){
        this.index.remove(key);
    }

    public List<JSPP> get(String key){
        List<JSPP> list = new ArrayList<JSPP>();
        String[] ids = this.index.get(key);
        if(ids == null){
            return list;
        }

        for(String id : ids){
            PUID puid = PUID.valueOf(id);
            Data data = getData( puid.getTime());
            if(data != null) {
                String message = data.get(id);
                if (StringUtils.isNotBlank(message)) {
                    JSPP jspp = decode(message);
                    if(jspp != null) {
                        list.add(jspp);
                    }
                }
            }
        }

        return list;
    }

    private synchronized Data getOrCreateData(){
        return getOrCreateData(System.currentTimeMillis(),true);
    }

    private synchronized Data getData(long datetime){
        return getOrCreateData(datetime,false);
    }

    private synchronized Data getOrCreateData(long datetime,boolean createNew){
        String date = DateFormatUtils.format(datetime,"yyyyMMdd");
        Data data = datas.get(date);
        if(data == null){
            String file = FilenameUtils.concat(dir,DATA_FILE_NAME +"." + date);
            File dataFile = new File(file);
            if(dataFile.exists() || createNew) {
                data = new Data(dataFile);
                try {
                    data.open();
                    datas.put(date, data);
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(),e);
                }
            }
        }
        return data;
    }


}
