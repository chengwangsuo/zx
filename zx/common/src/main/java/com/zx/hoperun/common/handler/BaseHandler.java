package com.zx.hoperun.common.handler;

import com.zx.hoperun.common.vo.Result;

import java.util.Map;
//建造者模式（相当于builder)
public interface BaseHandler {
    //对请求参数进行转换（json转换成map）
    public Map<String, Object> parseRequestParams(String json) throws Exception;
    //验证请求参数是否合法
    public Map<String,String> validParam(Map<String, String> map) throws Exception;
    //请求认证方式（包括token、证书认证、密钥认证）
    public Map<String, String> certifite(Map<String, String> param) throws Exception;
    //发送请求
    public String  sendRequest(Map<String, String> param) throws Exception;
    //解析结果
    public Map<String,Object> parseRespose(String result) throws Exception;

}