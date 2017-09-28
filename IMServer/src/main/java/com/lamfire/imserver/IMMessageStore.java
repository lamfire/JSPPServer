package com.lamfire.imserver;

import com.lamfire.jspp.JSPP;

import java.util.List;

/**
 * IMMessageStore
 * Created by linfan on 2017/9/28.
 */
public interface IMMessageStore {
    void add(String key, JSPP value);
    void remove(String key);
    List<JSPP> get(String key);
}
