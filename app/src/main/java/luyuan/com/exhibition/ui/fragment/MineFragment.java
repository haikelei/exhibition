package luyuan.com.exhibition.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import luyuan.com.exhibition.event.EventModifyAvatar;
import luyuan.com.exhibition.event.EventModifyNickname;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.LoginBean;
import luyuan.com.exhibition.ui.activity.ApplyExhibitionActivity;
import luyuan.com.exhibition.ui.activity.LoginPasswordActivity;
import luyuan.com.exhibition.ui.activity.ManageProductActivity;
import luyuan.com.exhibition.ui.activity.MyInfoActivity;
import luyuan.com.exhibition.ui.activity.MyPageActivity;
import luyuan.com.exhibition.ui.activity.SettingActivity;
import luyuan.com.exhibition.utils.Const;
import luyuan.com.exhibition.utils.SettingManager;

/**
 * @author: lujialei
 * @date: 2018/9/27
 * @describe:
 */


public class MineFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.rl_apply)
    RelativeLayout rlApply;
    @BindView(R.id.my_info)
    RelativeLayout myInfo;
    @BindView(R.id.rl_mypage)
    RelativeLayout rlMypage;
    @BindView(R.id.rl_manage_product)
    RelativeLayout rlManageProduct;
    @BindView(R.id.rl_settings)
    RelativeLayout rlSettings;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_account)
    TextView tvAccount;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!SettingManager.getInstance().isLogin()) {
            tvName.setText("未登录");
        } else {
            if (TextUtils.isEmpty(SettingManager.getInstance().getNickname())){
                tvName.setText("已登录");
            }else {
                tvName.setText(SettingManager.getInstance().getNickname());
            }
        }
        if (TextUtils.isEmpty(SettingManager.getInstance().getAccount())){
            tvAccount.setText("");
        }else {
            tvAccount.setText(SettingManager.getInstance().getAccount());
        }
        if (!SettingManager.getInstance().isLogin()) {
            ivAvatar.setImageResource(R.mipmap.home_exhibition);
        } else {
            Glide.with(getActivity())
                    .load(Const.IMG_PRE + SettingManager.getInstance().getAvatar())
                    .into(ivAvatar);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_avatar, R.id.rl_apply, R.id.my_info, R.id.rl_mypage, R.id.rl_manage_product, R.id.rl_settings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar://头像
                if (SettingManager.getInstance().isLogin()) {

                } else {
                    startActivity(new Intent(getContext(), LoginPasswordActivity.class));
                }
                break;
            case R.id.rl_apply://申请展位
                startActivity(new Intent(getContext(), ApplyExhibitionActivity.class));
                break;
            case R.id.my_info://我的资料
                startActivity(new Intent(getContext(), MyInfoActivity.class));
                break;
            case R.id.rl_mypage://我的主页
                startActivity(new Intent(getContext(), MyPageActivity.class));
                break;
            case R.id.rl_manage_product://管理产品
                startActivity(new Intent(getContext(), ManageProductActivity.class));
                break;
            case R.id.rl_settings://设置
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogin(LoginBean loginBean) {
        if (TextUtils.isEmpty(loginBean.getProfile().getNickname())) {
            tvName.setText("已登录");
        } else {
            tvName.setText(loginBean.getProfile().getNickname());
        }

        if (TextUtils.isEmpty(loginBean.getTelno())){
            tvAccount.setText("");
        }else {
            tvAccount.setText(loginBean.getTelno());
        }

    }

    @Subscribe
    public void onNicknameEdited(EventModifyNickname eventModifyNickname){
        tvName.setText(eventModifyNickname.nickname);
    }

    @Subscribe
    public void onAvatarEdited(EventModifyAvatar event){
        Glide.with(getContext())
                .load(Const.IMG_PRE+event.avatar)
                .into(ivAvatar);
    }
}
