package com.zx.hoperun.zt.service;


import com.zx.hoperun.common.vo.Result;
import com.zx.hoperun.zt.handler.ZtHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ZtService {
    @Autowired
    private ZtHandler launcher;
    public Map<String,Object> getResult(Map<String,String> map) throws Exception{
        return launcher.parseRespose(launcher.sendRequest(map));
    }


}
