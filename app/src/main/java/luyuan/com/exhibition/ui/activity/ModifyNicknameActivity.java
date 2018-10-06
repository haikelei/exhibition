package luyuan.com.exhibition.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luyuan.com.exhibition.event.EventModifyNickname;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.UserInfoBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.utils.SettingManager;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:我的主页
 */


public class ModifyNicknameActivity extends BaseActivity {

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_modify_nickname);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        et.setText(SettingManager.getInstance().getNickname());
    }


    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
        HttpManager.post(HttpManager.EDIT_USERINFO)
                .params("token",SettingManager.getInstance().getToken())
                .params("nickname",et.getText().toString())
                .execute(new SimpleCallBack<UserInfoBean>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(UserInfoBean userInfoBean) {
                        SettingManager.getInstance().setNickname(userInfoBean.getProfile().getNickname());
                        EventModifyNickname event = new EventModifyNickname();
                        event.nickname=userInfoBean.getProfile().getNickname();
                        EventBus.getDefault().post(event);
                        finish();
                    }
                });
    }
}
