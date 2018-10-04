package luyuan.com.exhibition.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.ui.activity.AddProductActivity;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class PlusTopBar extends RelativeLayout {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    Activity activity;
    @BindView(R.id.tv_plus)
    TextView tvPlus;



    public interface OnTopBarClickListener {
        void onChatClick(View v);
    }

    private OnTopBarClickListener mOnTopBarClickListener;

    public void setOnTopBarClickListener(OnTopBarClickListener mOnTopBarClickListener) {
        this.mOnTopBarClickListener = mOnTopBarClickListener;
    }

    public PlusTopBar(Context context) {
        this(context, null);
    }

    public PlusTopBar(Context context, String s) {
        this(context, s, false);
    }

    public PlusTopBar(Context context, String s, boolean isShowBack) {
        super(context);
        initView(context);
        if (s != null) {
            setTitle(s);
        }
        ivLeft.setVisibility(isShowBack ? VISIBLE : GONE);
    }

    private void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.layout_topbar_plus, this, true);
        ButterKnife.bind(this);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    public void setTitle(String s) {
        tvTitle.setText(s);
    }


    @OnClick({R.id.iv_left,R.id.tv_plus})
    public void onViewClicked(View view) {
        if (view.getId()==R.id.iv_left){
            if (activity != null) {
                activity.onBackPressed();
            }
        }else if (view.getId()==R.id.tv_plus){
            if (activity != null) {
                activity.startActivity(new Intent(activity, AddProductActivity.class));
            }
        }

    }
}
