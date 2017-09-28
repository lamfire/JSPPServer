package com.lamfire.imserver.store;

import com.lamfire.utils.FileUtils;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Data
 * Created by linfan on 2017/9/26.
 */
class Data {
    private static final String DATA_KEY_NAME = "MESSAGES";

    private File dbFile;
    private DB db;
    private Map<String,String> map;

    public Data(File dbFile){
        this.dbFile = dbFile;
    }

    public synchronized void open() throws IOException {
        if(this.db != null){
            return;
        }
        if(!FileUtils.exists(dbFile)) {
            FileUtils.makeParentDirsIfNotExists(dbFile);
        }
        this.db = DBMaker.fileDB(dbFile).checksumHeaderBypass().fileChannelEnable().fileMmapEnable().executorEnable().closeOnJvmShutdown().make();
        this.map = (Map<String,String>)db.hashMap(DATA_KEY_NAME).createOrOpen();
    }


    public void add(String id, String data){
        this.map.put(id,data);
    }

    public void remove(String key){
        this.map.remove(key);
    }

    public String get(String id){
        return this.map.get(id);
    }

    public synchronized void close(){
        this.db.close();
        this.db = null;
    }
}
