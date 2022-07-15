package com.zx.hoperun.common.service;

import com.zx.hoperun.common.handler.BaseHandler;
import com.zx.hoperun.common.vo.Result;

import java.util.HashMap;
import java.util.Map;

//相当于建造者的Derector
public class BaseService {
    private BaseHandler launcher;

    public BaseService(BaseHandler baseHandler) {
        this.launcher = baseHandler;
    }

    //按步骤执行handler方法
    public Map<String, Object> handler(String json) throws Exception {
        Map<String, Object> map = launcher.parseRequestParams(json);
        Map<String, String> params = (Map<String, String>) map.get("params");
        Map<String, String> valid = launcher.validParam(params);
        if (valid.size() == 0) {
            String result = launcher.sendRequest(launcher.certifite(params));
            return launcher.parseRespose(result);
        }else{
            Map<String,Object> result= new HashMap<String, Object>();
            StringBuffer sb = new StringBuffer();
            for (Map.Entry entry : valid.entrySet()) {
                  sb.append(" " +entry.getValue());
            }
            result.put("message",sb);
            result.put("status","404");
            result.put("errNo","-2000");
            return result;
        }
    }
}
