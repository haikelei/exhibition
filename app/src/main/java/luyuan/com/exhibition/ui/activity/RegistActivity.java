package luyuan.com.exhibition.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.SmsBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:
 */


public class RegistActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_yanzhengma)
    TextView tvYanzhengma;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_yanzhengma)
    EditText etYanzhengma;
    @BindView(R.id.et_check_password)
    EditText etCheckPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    private int timeLength = 60;
    private Runnable yanzhengTask = new Runnable() {
        @Override
        public void run() {
            if (timeLength==0){
                tvYanzhengma.removeCallbacks(yanzhengTask);
                tvYanzhengma.setText("获取验证码");
            }else {
                timeLength--;
                tvYanzhengma.setText(timeLength+"秒");
                tvYanzhengma.postDelayed(this,1000);
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_regist);
        ButterKnife.bind(this);
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "会展平台注册", true);
        return topBar;
    }

    @OnClick({R.id.tv_yanzhengma, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yanzhengma:
                getYanZhengMa();
                break;
            case R.id.tv_login:
                loginCheck();
                break;
        }
    }

    private void loginCheck() {
        String phone = etPhone.getText().toString();
        String yanzhengma = etYanzhengma.getText().toString();
        String password = etPassword.getText().toString();
        String checkPassword = etCheckPassword.getText().toString();
        boolean isEmpty = TextUtils.isEmpty(phone)||TextUtils.isEmpty(yanzhengma)||TextUtils.isEmpty(password)||TextUtils.isEmpty(checkPassword);
        if (isEmpty){
            Toast.makeText(this,"请填写注册参数",Toast.LENGTH_SHORT).show();
        }else {
            HttpManager.post(HttpManager.GET_REGIST)
                    .params("telno",phone)
                    .params("codeno",yanzhengma)
                    .params("password",password)
                    .execute(new SimpleCallBack<SmsBean>() {
                        @Override
                        public void onError(ApiException e) {
                            Toast.makeText(RegistActivity.this,"注册失败:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(SmsBean smsBean) {
                            finish();
                        }
                    });
        }
    }

    private void getYanZhengMa() {
        String phone = etPhone.getText().toString();
        if (!TextUtils.isEmpty(phone)){
            HttpManager.post(HttpManager.GET_SMS_CODE)
                    .params("telno",phone)
                    .execute(new SimpleCallBack<SmsBean>() {
                        @Override
                        public void onError(ApiException e) {

                        }

                        @Override
                        public void onSuccess(SmsBean smsBean) {
                            Toast.makeText(RegistActivity.this,"验证码:"+smsBean.codeno,Toast.LENGTH_LONG).show();
                            etYanzhengma.setText(smsBean.codeno);
                            tvYanzhengma.post(yanzhengTask);
                        }
                    });
        }else {
            Toast.makeText(RegistActivity.this,"请填写验证码",Toast.LENGTH_SHORT).show();
        }
       
    }
}
