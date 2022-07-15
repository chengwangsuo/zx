package com.zx.hoperun.common.handler.impl;

import com.zx.hoperun.common.handler.BaseHandler;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

//抽象类实现BaseHandler的公共方法
@Slf4j
public abstract class BaseHandlerImpl implements BaseHandler {
    //统一将json转换成map
    public Map<String, Object> parseRequestParams(String json) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 最外层解析
        JSONObject object  = JSONObject.fromObject(json);
        try {
            for (Object k : object.keySet()) {
                Object v = object.get(k);
                map.put(k.toString(), v);
            }
        }catch (Exception e){
            log.info("object循环遍历失败；",e.getStackTrace());
        }

        Map<String, Object> map2 = new HashMap<String, Object>();
        //第二层解析 第二层可能是 也可能不是
        for(Map.Entry<String, Object> entry:map.entrySet()){
            try {
                org.json.JSONArray array = new  org.json.JSONArray(entry.getValue().toString());  //判断是否是json数组

                //是json数组
                for (int i = 0; i < array.length(); i++) {
                    org.json.JSONObject object2 = array.getJSONObject(i);//json数组对象
                    JSONObject object3 = JSONObject.fromObject(object2.toString());  //json对象
                    for (Object k : object3.keySet()) {
                        Object v = object3.get(k);
                        map2.put(k.toString(), v);
                    }
                }
            } catch (Exception e) {  //不是json串数组
                map2.put(entry.getKey(), entry.getValue());
            }
        }
        return map2;
    }

    public abstract Map<String,String> validParam(Map<String, String> map) throws Exception;

    public abstract Map<String, String> certifite(Map<String, String> param) throws Exception;

    public abstract String  sendRequest(Map<String, String> param) throws Exception ;

    public abstract Map<String,Object> parseRespose(String result) throws Exception ;
}