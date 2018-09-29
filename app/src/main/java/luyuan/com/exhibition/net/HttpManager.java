package luyuan.com.exhibition.net;

import com.zhouyou.http.request.GetRequest;
import com.zhouyou.http.request.PostRequest;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */

public class HttpManager {
    /**
     * get请求
     */
    public static GetRequest get(String url) {
        return new CustomGetRequest(url);
    }

    /**
     * post请求
     */
    public static PostRequest post(String url) {
        return new CustomPostRequest(url);
    }
}
