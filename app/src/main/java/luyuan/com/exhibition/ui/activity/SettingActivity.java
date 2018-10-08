package luyuan.com.exhibition.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.UserBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;
import luyuan.com.exhibition.utils.SettingManager;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:
 */


public class SettingActivity extends BaseActivity {
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_my_info);
        ButterKnife.bind(this);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingManager.getInstance().logout();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        HttpManager.post(HttpManager.GET_USER_INFO)
                .params("token", SettingManager.getInstance().getToken())
                .execute(new SimpleCallBack<UserBean>() {
                    @Override
                    public void onError(ApiException e) {
                        if (e.getCode()==401){
                            finish();
                        }
                    }

                    @Override
                    public void onSuccess(UserBean userBean) {
                        if (!TextUtils.isEmpty(userBean.getProfile().getPhone())) {
                            tvPhone.setText(userBean.getProfile().getPhone());
                        }
                    }
                });
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "设置", true);
        return topBar;
    }

    @OnClick({R.id.rl_phone, R.id.rl_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_phone:
                break;
            case R.id.rl_password:
                break;
        }
    }
}
