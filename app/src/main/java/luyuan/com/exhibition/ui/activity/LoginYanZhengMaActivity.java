package luyuan.com.exhibition.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:我的主页
 */


public class LoginYanZhengMaActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_login_yanzhengma);
        ButterKnife.bind(this);
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "汇展俱乐部登录", true);
        return topBar;
    }

    @OnClick({R.id.tv_regist, R.id.tv_login_yanzhengma, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_regist:

                break;
            case R.id.tv_login_yanzhengma:
                break;
            case R.id.tv_login:
                break;
        }
    }
}