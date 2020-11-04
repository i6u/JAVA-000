package org.geekbang;

import org.geekbang.week02.utils.HttpUtil;

/**
 * @author :  witt on 2020/10/28 9:08 下午 星期三 :)
 */
public class Main {

    public static void main(String[] args) {
        // 使用 OkHttp 访问 http://localhost:8881/
        String responseBody = HttpUtil.INSTANCE.loadString("http://localhost:8881/");
        System.out.println(responseBody);
    }
}
