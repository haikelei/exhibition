package luyuan.com.exhibition;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.converter.SerializableDiskConverter;
import com.zhouyou.http.model.HttpHeaders;
import com.zhouyou.http.model.HttpParams;

import luyuan.com.exhibition.net.interceptor.TokenInterceptor;

/**
 * @author: lujialei
 * @date: 2018/9/27
 * @describe:
 */


public class MyApplication extends Application {
    private static Application app = null;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initNet();
        initHX();
    }

    private void initHX() {
        EMOptions options = new EMOptions();

//        EMClient.getInstance().init(this,options);
        EaseUI.getInstance().init(this, options);
    }

    private void initNet() {
        EasyHttp.init(this);

        //这里涉及到安全我把url去掉了，demo都是调试通的
        String Url = "http://zh.online-sh.com/api.php/";


        //设置请求头
        HttpHeaders headers = new HttpHeaders();
//        headers.put("User-Agent", SystemInfoUtils.getUserAgent(this, AppConstant.APPID));
        //设置请求参数
        HttpParams params = new HttpParams();
//        params.put("appId", AppConstant.APPID);
        EasyHttp.getInstance()
                .debug("RxEasyHttp", true)
                .setReadTimeOut(10 * 1000)
                .setWriteTimeOut(10 * 1000)
                .setConnectTimeout(10 * 1000)
                .setRetryCount(3)//默认网络不好自动重试3次
                .setRetryDelay(500)//每次延时500ms重试
                .setRetryIncreaseDelay(500)//每次延时叠加500ms
                .setBaseUrl(Url)
                .setCacheDiskConverter(new SerializableDiskConverter())//默认缓存使用序列化转化
                .setCacheMaxSize(50 * 1024 * 1024)//设置缓存大小为50M
                .setCacheVersion(1)//缓存版本为1
                .setCertificates()//信任所有证书
                //.addConverterFactory(GsonConverterFactory.create(gson))//本框架没有采用Retrofit的Gson转化，所以不用配置
                .addCommonHeaders(headers)//设置全局公共头
                .addCommonParams(params)//设置全局公共参数
                .addInterceptor(new TokenInterceptor());//处理自己业务的拦截器
    }

    /**
     * 获取Application的Context
     **/
    public static Context getAppContext() {
        if (app == null)
            return null;
        return app.getApplicationContext();
    }
}
