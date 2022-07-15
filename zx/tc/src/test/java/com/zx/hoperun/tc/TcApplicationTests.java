package com.zx.hoperun.tc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
@SpringBootTest
class TcApplicationTests {

    @Test
    void contextLoads() {
        Map<String, Object> map = new HashMap<String, Object>();
        // 最外层解析
        JSONObject object = object = JSONObject.fromObject("{ \n" +
                "    \"id\":\"23\",\n" +
                "    \"name\":\"ewd\",\n" +
                "    \"param\":{\"phone\":\"1234567\",\n" +
                "    \"bankCard\":\"62100999\"}\n" +
                "}");
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            map.put(k.toString(), v);
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
        System.out.println(map2);

    }

}
