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


    /**
     * 获取城市列表
     */
    public static final String GET_CITY_LIST = "Overall/getOpenRegions";
    /**
     * 获取企业列表
     */
    public static final String GET_COMPANY_LIST = "Company/getCompanyList";
    /**
     * 获取验证码
     */
    public static final String GET_SMS_CODE = "User/getSmsCode";
    /**
     * 注册
     */
    public static final String GET_REGIST = "User/getRegister";
    /**
     * 登录
     */
    public static final String GET_LOGIN = "User/getLogin";
    /**
     *编辑用户基本资料
     */
    public static final String EDIT_USERINFO = "User/editUserInfo";
    /**
     *获取企业详情
     */
    public static final String COMPANY_DETAIL = "Company/getCompanyDetails";
    /**
     *获取企业产品
     */
    public static final String COMPANY_PRODUCTS = "Company/getCompanyProducts";
    /**
     *获取产品详情
     */
    public static final String PRODUCT_DETAIL = "Company/getProductDetails";

}
