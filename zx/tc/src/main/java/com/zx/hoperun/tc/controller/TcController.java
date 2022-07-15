package com.zx.hoperun.tc.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.zx.hoperun.common.utils.ExceptionUtil;
import com.zx.hoperun.tc.entity.TcData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/tc")
public class TcController {
    @RequestMapping("/getResult")
    @SentinelResource(value = "hello", blockHandler = "handleException", blockHandlerClass = ExceptionUtil.class)
    @ResponseBody
    public Map<String,Object> getResult(@RequestBody String req) throws Exception {
        //BaseService baseService = new BaseService(new TcHandler());
        //return baseService.handler(req);
        Map<String,Object> result = new HashMap<>();
        result.put("message","请求成功");
        result.put("status","200");
        result.put("errorNo","0");
        TcData data = new TcData();
        data.setXyp_cpl0001("31");
        data.setFee_status("0");
        data.setXyp_cpl0002("2e");
        data.setXyp_cpl0007("");
        data.setXyp_cpl0008("34");
        data.setXyp_t01aagzbz("22");
        data.setXyp_cpl0009("ee");
        data.setXyp_cpl0010("rtr");
        result.put("data",data);
        return result;
    }
}
