package com.zx.hoperun.zt.task;

import com.zx.hoperun.zt.base.BaseDataUtil;
import com.zx.hoperun.zt.base.HttpPostMap;
import com.zx.hoperun.zt.base.KeyHelp;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StatTask {
    public void doSomething() throws Exception {
        //组装参数，第一处修改密码ptypwd传空，非第修改密码ptypwd不能为空或者不传
        String data = "ptyacct=" + BaseDataUtil.ptyacct + "&ptycd=" + BaseDataUtil.ptycd + "&newptypwd=" + "xnt123456" + "&ptypwd=";
        String funcNo = "2000006";//修改密码功能号
        prepareParams(data, funcNo);
        //组装参数，会话密钥为随便大于16位小于64位的字符串（生成规则机构自定义）
        data = "ptyacct=" + BaseDataUtil.ptyacct + "&ptycd=" + BaseDataUtil.ptycd + "&ptypwd=xnt123456" + "&encrykey=" + BaseDataUtil.ptyKey;
        funcNo = "2000002";//修改密钥功能号
        prepareParams(data, funcNo);
    }

    public void prepareParams(String data, String funcNo) throws Exception {
        //公钥加密且做对应的base64，URLEncoder处理
        data = KeyHelp.getStrByPublic(BaseDataUtil.publicKey, data);

        //组装参数调用接口
        Map<String, String> map = new HashMap<String, String>();
        map.put("funcNo", funcNo);
        map.put("reqdata", data);
        sendMessage(data, map);
    }
/*    public void modifySecretKey() throws Exception{
        //组装参数，会话密钥为随便大于16位小于64位的字符串（生成规则机构自定义）
        String data = "ptyacct="+BaseDataUtil.ptyacct+"&ptycd="+BaseDataUtil.ptycd+"&ptypwd=xnt123456"+"&encrykey="+BaseDataUtil.ptyKey;

        //公钥加密且做对应的base64，URLEncoder处理
        data = KeyHelp.getStrByPublic(BaseDataUtil.publicKey, data);

        //组装参数调用接口
        Map<String, String> map = new HashMap<String,String>();
        map.put("funcNo", "2000002");//修改会话密钥功能号
        map.put("reqdata", data);

        //发送请求
        String result = HttpPostMap.post(BaseDataUtil.url, map,2);
        JSONObject jsonObject = new JSONObject(result);
        //解析参数
        String error_no =(String) jsonObject.get("error_no");//error_no=0代表成功
        if(!error_no.equals("0")){
            System.out.println("操作失败，失败原因："+jsonObject.get("error_info"));
        }else {
            System.out.println("修改会话密钥操作成功！");
        }
    }
    public void modifyPassword() throws Exception{
        //组装参数，第一处修改密码ptypwd传空，非第修改密码ptypwd不能为空或者不传
        String data = "ptyacct="+ BaseDataUtil.ptyacct+"&ptycd="+BaseDataUtil.ptycd+"&newptypwd="+"xnt123456"+"&ptypwd=";
        //公钥加密且做对应的base64，URLEncoder处理
        data = KeyHelp.getStrByPublic(BaseDataUtil.publicKey, data);

        //组装参数调用接口
        Map<String, String> map = new HashMap<String,String>();
        map.put("funcNo", "2000006");//修改密码功能号
        map.put("reqdata", data);

        //请求解析参数
        String result = HttpPostMap.post(BaseDataUtil.url, map,2);
        JSONObject jsonObject = new JSONObject(result);
        String error_no =(String) jsonObject.get("error_no");
        if(!error_no.equals("0")){
            System.out.println("操作失败，失败原因："+jsonObject.get("error_info"));
        }else {
            System.out.println("修改密码操作成功！");
        }
    }*/

    public void sendMessage(String data, Map<String, String> map) throws Exception {
        //请求解析参数
        String result = HttpPostMap.post(BaseDataUtil.url, map, 2);
        JSONObject jsonObject = new JSONObject(result);
        String error_no = (String) jsonObject.get("error_no");
        if (!error_no.equals("0")) {
            System.out.println("操作失败，失败原因：" + jsonObject.get("error_info"));
        } else {
            System.out.println("操作成功！");
        }
    }
}
