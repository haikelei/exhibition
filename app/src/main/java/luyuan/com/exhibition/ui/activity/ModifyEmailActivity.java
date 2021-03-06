package luyuan.com.exhibition.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.UserInfoBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.utils.SettingManager;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:我的主页
 */


public class ModifyEmailActivity extends BaseActivity {

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_modify_common);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String s = getIntent().getStringExtra("value");
        if (!TextUtils.isEmpty(s)){
            et.setText(s);
        }
    }


    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
        HttpManager.post(HttpManager.EDIT_USERINFO)
                .params("token",SettingManager.getInstance().getToken())
                .params("email",et.getText().toString().trim())
                .execute(new SimpleCallBack<UserInfoBean>() {
                    @Override
                    public void onError(ApiException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onSuccess(UserInfoBean userInfoBean) {
                        finish();
                    }
                });
    }
}
