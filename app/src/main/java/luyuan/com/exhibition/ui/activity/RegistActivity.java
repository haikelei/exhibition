package luyuan.com.exhibition.ui.activity;

import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CityBean;
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
    @BindView(R.id.tv_city)
    TextView tvCity;
    private List<CityBean> cityList;

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
        loadCitys();
    }

    private void loadCitys() {
        HttpManager.post(HttpManager.GET_CITY_LIST)
                .execute(new SimpleCallBack<List<CityBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<CityBean> categoryBeans) {
                        cityList = categoryBeans;
                    }
                });
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "会展平台注册", true);
        return topBar;
    }

    @OnClick({R.id.tv_yanzhengma, R.id.tv_login,R.id.rl_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yanzhengma:
                getYanZhengMa();
                break;
            case R.id.tv_login:
                loginCheck();
                break;
            case R.id.rl_city:
                onOptionPicker();
                break;
        }
    }

    public void onOptionPicker() {
        if (cityList != null) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < cityList.size(); i++) {
                if (cityList.get(i)!=null&&cityList.get(i).getRegion_name()!=null){
                    list.add(cityList.get(i).getRegion_name());
                }
            }
            OptionPicker picker = new OptionPicker(this, list);
            picker.setCanceledOnTouchOutside(false);
            picker.setDividerRatio(WheelView.DividerConfig.FILL);
            picker.setShadowColor(Color.RED, 40);
            picker.setSelectedIndex(1);
            picker.setCycleDisable(true);
            picker.setTextSize(11);
            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    tvCity.setText(item);
                }
            });
            picker.show();
        } else {
            Toast.makeText(this, "获取城市列表失败", Toast.LENGTH_SHORT).show();
        }

    }

    private void loginCheck() {
        String phone = etPhone.getText().toString();
        String yanzhengma = etYanzhengma.getText().toString();
        String password = etPassword.getText().toString();
        String checkPassword = etCheckPassword.getText().toString();
        String city = tvCity.getText().toString().trim();
        boolean isEmpty = TextUtils.isEmpty(city)||"请选择城市".equals(city)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(yanzhengma)||TextUtils.isEmpty(password)||TextUtils.isEmpty(checkPassword);
        if (isEmpty){
            Toast.makeText(this,"请填写注册参数",Toast.LENGTH_SHORT).show();
        }else {
            int cityId=0;
            for (int i = 0; i <cityList.size() ; i++) {
                if (cityList.get(i)!=null&&city.equals(cityList.get(i).getRegion_name())){
                    cityId = cityList.get(i).getCity_id();
                }
            }
            HttpManager.post(HttpManager.GET_REGIST)
                    .params("telno",phone)
                    .params("codeno",yanzhengma)
                    .params("password",password)
                    .params("city_id",String.valueOf(cityId))
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
