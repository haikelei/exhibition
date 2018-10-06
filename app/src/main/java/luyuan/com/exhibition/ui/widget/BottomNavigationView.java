package luyuan.com.exhibition.ui.widget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.ui.fragment.CategoryFragment;
import luyuan.com.exhibition.ui.fragment.ChatListFragment;
import luyuan.com.exhibition.ui.fragment.HomeFragment;
import luyuan.com.exhibition.ui.fragment.MineFragment;

/**
 * @author: lujialei
 * @date: 2018/9/27
 * @describe:
 */


public class BottomNavigationView extends RelativeLayout {
    @BindView(R.id.iv0)
    ImageView iv0;
    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.tv0)
    TextView tv0;
    @BindView(R.id.rl0)
    RelativeLayout rl0;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.rl1)
    RelativeLayout rl1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.rl3)
    RelativeLayout rl3;

    public BottomNavigationView(Context context) {
        this(context, null);
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.layout_bottom_navigation_view, this, true);
        ButterKnife.bind(this);
        iv0.setSelected(true);
        iv1.setSelected(false);
        iv2.setSelected(false);
        iv3.setSelected(false);
        tv0.setTextColor(getResources().getColor(R.color.c_165ce8));
    }

    private ArrayList<Fragment> fragmentList;
    private FragmentManager supportFragmentManager;

    public void initFragment(FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
        fragmentList = new ArrayList();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new CategoryFragment());
        fragmentList.add(new ChatListFragment());
        fragmentList.add(new MineFragment());
        supportFragmentManager.beginTransaction()
                .add(R.id.container, fragmentList.get(0), "home")
                .add(R.id.container, fragmentList.get(1), "category")
                .add(R.id.container, fragmentList.get(2), "chat")
                .add(R.id.container, fragmentList.get(3), "mine")
                .hide(fragmentList.get(1))
                .hide(fragmentList.get(2))
                .hide(fragmentList.get(3))
                .commitAllowingStateLoss();
    }

    public interface Listener {
        void onClick(int index);
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @OnClick({R.id.rl0, R.id.rl1, R.id.rl2, R.id.rl3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl0:
                iv0.setSelected(true);
                iv1.setSelected(false);
                iv2.setSelected(false);
                iv3.setSelected(false);
                tv0.setTextColor(getResources().getColor(R.color.c_165ce8));
                tv1.setTextColor(getResources().getColor(R.color.c_999999));
                tv2.setTextColor(getResources().getColor(R.color.c_999999));
                tv3.setTextColor(getResources().getColor(R.color.c_999999));
                changeTab(0);
                if (listener != null) {
                    listener.onClick(0);
                }
                break;
            case R.id.rl1:
                iv0.setSelected(false);
                iv1.setSelected(true);
                iv2.setSelected(false);
                iv3.setSelected(false);
                tv0.setTextColor(getResources().getColor(R.color.c_999999));
                tv1.setTextColor(getResources().getColor(R.color.c_165ce8));
                tv2.setTextColor(getResources().getColor(R.color.c_999999));
                tv3.setTextColor(getResources().getColor(R.color.c_999999));
                changeTab(1);
                if (listener != null) {
                    listener.onClick(1);
                }
                break;
            case R.id.rl2:
                iv0.setSelected(false);
                iv1.setSelected(false);
                iv2.setSelected(true);
                iv3.setSelected(false);
                tv0.setTextColor(getResources().getColor(R.color.c_999999));
                tv1.setTextColor(getResources().getColor(R.color.c_999999));
                tv2.setTextColor(getResources().getColor(R.color.c_165ce8));
                tv3.setTextColor(getResources().getColor(R.color.c_999999));
                changeTab(2);
                if (listener != null) {
                    listener.onClick(2);
                }
                break;
            case R.id.rl3:
                iv0.setSelected(false);
                iv1.setSelected(false);
                iv2.setSelected(false);
                iv3.setSelected(true);
                tv0.setTextColor(getResources().getColor(R.color.c_999999));
                tv1.setTextColor(getResources().getColor(R.color.c_999999));
                tv2.setTextColor(getResources().getColor(R.color.c_999999));
                tv3.setTextColor(getResources().getColor(R.color.c_165ce8));
                changeTab(3);
                if (listener != null) {
                    listener.onClick(3);
                }
                break;
        }
    }

    private void changeTab(int index) {
        if (index == 0) {
            supportFragmentManager.beginTransaction()
                    .show(fragmentList.get(0))
                    .hide(fragmentList.get(1))
                    .hide(fragmentList.get(2))
                    .hide(fragmentList.get(3))
                    .commitAllowingStateLoss();
        } else if (index == 1) {
            supportFragmentManager.beginTransaction()
                    .hide(fragmentList.get(0))
                    .show(fragmentList.get(1))
                    .hide(fragmentList.get(2))
                    .hide(fragmentList.get(3))
                    .commitAllowingStateLoss();
        } else if (index == 2) {
            supportFragmentManager.beginTransaction()
                    .hide(fragmentList.get(0))
                    .hide(fragmentList.get(1))
                    .show(fragmentList.get(2))
                    .hide(fragmentList.get(3))
                    .commitAllowingStateLoss();
        } else if (index == 3) {
            supportFragmentManager.beginTransaction()
                    .hide(fragmentList.get(0))
                    .hide(fragmentList.get(1))
                    .hide(fragmentList.get(2))
                    .show(fragmentList.get(3))
                    .commitAllowingStateLoss();
        }
    }
}
