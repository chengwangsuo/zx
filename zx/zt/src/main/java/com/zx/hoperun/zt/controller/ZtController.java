package com.zx.hoperun.zt.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSONObject;
import com.zx.hoperun.common.service.BaseService;
import com.zx.hoperun.common.utils.ExceptionUtil;
import com.zx.hoperun.common.vo.Result;
import com.zx.hoperun.zt.handler.ZtHandler;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/zt", produces = {"application/json;charset=UTF-8"})
public class ZtController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送普通消息
     * convertAndSend(String destination, Object payload) 发送字符串比较方便
     */
    @RequestMapping("/send")
    public void send() {
        rocketMQTemplate.convertAndSend("test-topic", "test-message");
    }

    @RequestMapping("/getResult")
    @SentinelResource(value = "hello", blockHandler = "handleException", blockHandlerClass = ExceptionUtil.class)
    @ResponseBody
    public Map<String, Object> getResult(@RequestBody String req) throws Exception {
        BaseService baseService = new BaseService(new ZtHandler());
        return baseService.handler(req);
    }
}