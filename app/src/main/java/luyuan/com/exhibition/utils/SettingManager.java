package luyuan.com.exhibition.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

import luyuan.com.exhibition.MyApplication;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:
 */


public class SettingManager {
    private static SettingManager instance;
    private SharedPreferencesHelper helper;
    private SettingManager(){
        helper = new SharedPreferencesHelper(MyApplication.getAppContext(),"exhibition_sp");
    }

    public static SettingManager getInstance(){
        if (instance==null){
            instance = new SettingManager();
        }
        return instance;
    }

    public void setNickname(String nickname){
        helper.put("nick_name",nickname);
    }

    public String getNickname(){
        return (String) helper.getSharedPreference("nick_name","");
    }

    public boolean isLogin(){
        return !TextUtils.isEmpty((String) helper.getSharedPreference("nick_name",""));
    }

    public void setAvatar(String nickname){
        helper.put("avatar",nickname);
    }

    public String getAvatar(){
        return (String) helper.getSharedPreference("avatar","");
    }
}
