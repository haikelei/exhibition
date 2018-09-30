package luyuan.com.exhibition.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luyuan.com.exhibition.R;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class DefaultTopBar extends RelativeLayout {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    Activity activity;
    @BindView(R.id.tv_chat)
    TextView tvChat;

    public interface OnTopBarClickListener{
        void onChatClick(View v);
    }
    private OnTopBarClickListener mOnTopBarClickListener;
    public void setOnTopBarClickListener(OnTopBarClickListener mOnTopBarClickListener){
        this.mOnTopBarClickListener = mOnTopBarClickListener;
    }

    public DefaultTopBar(Context context) {
        this(context, null);
    }

    public DefaultTopBar(Context context, String s) {
        this(context, s, false);
    }

    public DefaultTopBar(Context context, String s, boolean isShowBack) {
        super(context);
        initView(context);
        if (s != null) {
            setTitle(s);
        }
        ivLeft.setVisibility(isShowBack ? VISIBLE : GONE);
    }

    private void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.layout_topbar_default, this, true);
        ButterKnife.bind(this);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    public void setTitle(String s) {
        tvTitle.setText(s);
    }

    public void showChatView(boolean b){
        tvChat.setVisibility(b ? VISIBLE : GONE);
    }

    @OnClick(R.id.iv_left)
    public void onViewClicked() {
        if (activity != null) {
            activity.onBackPressed();
        }
    }
}
