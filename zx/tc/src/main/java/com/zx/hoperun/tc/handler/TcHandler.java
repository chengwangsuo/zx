package com.zx.hoperun.tc.handler;

import com.alibaba.fastjson.JSONObject;
import com.zx.hoperun.common.handler.BaseHandler;
import com.zx.hoperun.common.handler.impl.BaseHandlerImpl;
import com.zx.hoperun.tc.util.AESUtil;
import com.zx.hoperun.tc.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//天创的handler实现BaseHandler
public class TcHandler extends BaseHandlerImpl implements BaseHandler {
    /**
     * 登录客户管理中心可查看获取appId和tokenId
     */
    private final static String appId = "";
    private final static String tokenId = "";
    //具体实现参数验证方式
    @Override
    public Map<String,String> validParam(Map<String, String> param) throws Exception{
       return null;
    }

    @Override
    public Map<String,String> certifite(Map<String, String> param) throws Exception{
        return paramConvert(param);
    }
    //发送请求并获取结果
    @Override
    public String sendRequest(Map<String, String> param) throws Exception {
        return getRemoteResult(param,param.get("url"));
    }
    //对结果进行解析
    @Override
    public Map<String,Object> parseRespose(String result) throws Exception {
        return null;
    }

    public Map<String, String> paramConvert(Map<String, String> map) throws Exception {
        // 所需入参，请参照具体接口文档调整入参, 请注意区分大小写
        JSONObject paramJSON = new JSONObject();
        paramJSON.put("idcard", map.get("idcard"));
        paramJSON.put("name", map.get("name"));
        paramJSON.put("mobile", map.get("mobile"));
        //AES加密
        String paramStr = AESUtil.encode(tokenId.replace("-", ""), paramJSON.toJSONString());

        Map<String, String> params = new HashMap<String, String>();
        params.put("param", paramStr);
        params.put("authId", map.get("authId"));
        params.put("isCpt", map.get("isCpt"));
        params.put("tmcName", map.get("tmcName"));

        // 生成TokenKey
        // 注意：进行MD5摘要时，不能传入appId参数
        String tokenKey = getTokenKey(map.get("url"), tokenId, params);
        params.put("tokenKey", tokenKey);

        // 注意：appId参数需要在请求的时候作为入参传入，appId不参与MD5摘要
        params.put("appId", appId);
        return params;
    }

    public String getRemoteResult(Map<String,String>params,String url) throws Exception {
        // http的post请求
        String result = HttpClientUtil.doPost(url, params);
        //如果命中了, 需将data字段解密
        if (!StringUtils.isBlank(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            //成功
            if ("0".equals(jsonObject.getString("status"))) {
                String data = jsonObject.getString("data");
                //AES解密
                data = AESUtil.decode(tokenId.replace("-", ""), data, "UTF-8");
                jsonObject.put("data", JSONObject.parseObject(data));
                result = jsonObject.toString();

            }
        }
        // 打印返回结果
        return result;
    }

    /**
     * 生成TokenKey
     *
     * @param url
     * @param tokenId
     * @param params
     * @return
     */
    public String getTokenKey(String url, String tokenId, Map<String, String> params) {
        String paramStr = sortParam(params);
        return md5Hex(url + tokenId + paramStr);
    }

    /**
     * 生成参数字符串，参数key按字典序排列
     *
     * @param param
     * @return
     */
    public String sortParam(Map<String, String> param) {
        if (null == param || 0 == param.size()) {
            return "";
        }
        // 排序键，按照字母先后进行排序
        Iterator<String> iterator = param.keySet().iterator();
        String[] arr = new String[param.size()];
        for (int i = 0; iterator.hasNext(); i++) {
            arr[i] = iterator.next();
        }
        Arrays.sort(arr);
        // 生成进行MD5摘要的字符串
        StringBuffer sb = new StringBuffer();
        for (String key : arr) {
            String value = param.get(key);
            if (StringUtils.isNotBlank(value)) {
                sb.append(key).append("=").append(value).append(",");
            }
        }
        // 检查结果
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        } else {
            return "";
        }
    }

    /**
     * 对字符串进行md5摘要，然后转化成16进制字符串
     *
     * @param text
     * @return
     */
    public String md5Hex(String text) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(text.trim().getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                int high = (bytes[i] >> 4) & 0x0f;
                int low = bytes[i] & 0x0f;
                sb.append(high > 9 ? (char) ((high - 10) + 'a') : (char) (high + '0'));
                sb.append(low > 9 ? (char) ((low - 10) + 'a') : (char) (low + '0'));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("系统不支持MD5算法");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("系统不支持指定的编码格式");
            e.printStackTrace();
        }
        return null;
    }
}