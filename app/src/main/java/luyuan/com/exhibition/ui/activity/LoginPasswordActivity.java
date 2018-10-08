package luyuan.com.exhibition.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.LoginBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;
import luyuan.com.exhibition.utils.SettingManager;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:我的主页
 */


public class LoginPasswordActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "汇展俱乐部登录", true);
        return topBar;
    }

    @OnClick({R.id.tv_regist, R.id.tv_login_mima, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_regist:
                startActivity(new Intent(this,RegistActivity.class));
                finish();
                break;
            case R.id.tv_login_mima:
                startActivity(new Intent(this,LoginYanZhengMaActivity.class));
                finish();
                break;
            case R.id.tv_login://登录按钮
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString();
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(this,"请填写手机号",Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)){
                    Toast.makeText(this,"请填写密码",Toast.LENGTH_SHORT).show();
                } else {
                    login(phone,password);
                }
                break;
        }
    }

    private void login(String phone,String password) {
        HttpManager.post(HttpManager.GET_LOGIN)
                .params("telno",phone)
                .params("password",password)
                .execute(new SimpleCallBack<LoginBean>() {
                    @Override
                    public void onError(ApiException e) {
                        Toast.makeText(getBaseContext(),"登录失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(LoginBean loginBean) {
                        SettingManager.getInstance().setAvatar(loginBean.getProfile().getHeadimgurl());
                        SettingManager.getInstance().setNickname(loginBean.getProfile().getNickname());
                        SettingManager.getInstance().setToken(loginBean.getToken());
                        SettingManager.getInstance().setAccount(loginBean.getTelno());
                        EventBus.getDefault().post(loginBean);
                        LoginBean.AppChatBean chatBean = loginBean.getApp_chat();

                        EMClient.getInstance().login(chatBean.getHx_username(), chatBean.getHx_password(), new EMCallBack() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(int i, String s) {

                            }

                            @Override
                            public void onProgress(int i, String s) {

                            }
                        });
                        finish();
                    }
                });
    }
}
