package com.lamfire.imserver.store;

import com.lamfire.utils.FileUtils;
import com.lamfire.utils.FilenameUtils;
import com.lamfire.utils.StringUtils;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Index
 * Created by linfan on 2017/9/26.
 */
class Index {
    private static final String INDEX_FILE_NAME = "message.idx";
    private static final String INDEX_KEY_NAME = "INDEXES";

    private String dir;
    private DB db;
    private Map<String,String> map;

    Index(String dir){
        this.dir = dir;
    }

    public synchronized void open() throws IOException {
        if(this.db != null){
            return;
        }
        String path = FilenameUtils.concat(dir,INDEX_FILE_NAME);
        File file = new File(path);
        if(!FileUtils.exists(file)) {
            FileUtils.makeParentDirsIfNotExists(file);
        }
        this.db = DBMaker.fileDB(file).checksumHeaderBypass().fileChannelEnable().fileMmapEnable().executorEnable().closeOnJvmShutdown().make();
        this.map = (Map<String,String>)db.hashMap(INDEX_KEY_NAME).counterEnable().createOrOpen();
    }

    public void add(String key, String mid){
        String mids = this.map.get(key);
        if(StringUtils.isBlank(mids)){
            mids = mid;
        }else{
            StringBuffer buffer = new StringBuffer(mids);
            buffer.append(",");
            buffer.append(mid);
            mids = buffer.toString();
        }
        this.map.put(key,mids);
    }

    public void remove(String key){
        this.map.remove(key);
    }


    public String[] get(String key){
        String ids =  this.map.get(key);
        if(StringUtils.isBlank(ids)){
            return null;
        }
        return StringUtils.split(ids,",");
    }

    public synchronized void close(){
        this.db.close();
        this.db = null;
    }
}
