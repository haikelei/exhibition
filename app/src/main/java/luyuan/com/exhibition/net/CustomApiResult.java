package luyuan.com.exhibition.net;

import com.zhouyou.http.model.ApiResult;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class CustomApiResult<T> extends ApiResult<T> {
    String des;


    @Override
    public String getMsg() {
        return des;
    }
    @Override
    public void setMsg(String msg) {
        des = msg;
    }

   @Override
    public boolean isOk() {
        return getCode()==1;//如果不是0表示成功，请重写isOk()方法。
    }
}


