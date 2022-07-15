package com.zx.hoperun.zt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class ZtApplicationTests {

    @Test
    void test() throws Exception {
        //正则表达式
        String regex = "^((13[0-9])|(14[0|5|6|7|9])|(15[0-3])|(15[5-9])|(16[6|7])|(17[2|3|5|6|7|8])|(18[0-9])|(19[1|8|9]))\\d{8}$";
        boolean flag = Pattern.matches(regex, "15695567186");
        System.out.println(flag);
    }


}
