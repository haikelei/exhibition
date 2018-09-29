package luyuan.com.exhibition;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    }

    private ArrayList<Fragment> fragmentList;
    private FragmentManager supportFragmentManager;
    public void initFragment(FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
        fragmentList = new ArrayList();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new CategoryFragment());
        fragmentList.add(new ChatFragment());
        fragmentList.add(new MineFragment());
        supportFragmentManager.beginTransaction()
                .add(R.id.container,fragmentList.get(0),"home")
                .add(R.id.container,fragmentList.get(1),"category")
                .add(R.id.container,fragmentList.get(2),"chat")
                .add(R.id.container,fragmentList.get(3),"mine")
                .hide(fragmentList.get(1))
                .hide(fragmentList.get(2))
                .hide(fragmentList.get(3))
                .commitAllowingStateLoss();
    }

    public interface Listener{
        void onClick(int index);
    }
    private Listener listener;
    public void setListener(Listener listener){
        this.listener = listener;
    }

    @OnClick({R.id.iv0, R.id.iv1, R.id.iv2, R.id.iv3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv0:
                iv0.setSelected(true);
                iv1.setSelected(false);
                iv2.setSelected(false);
                iv3.setSelected(false);
                changeTab(0);
                if (listener!= null){
                    listener.onClick(0);
                }
                break;
            case R.id.iv1:
                iv0.setSelected(false);
                iv1.setSelected(true);
                iv2.setSelected(false);
                iv3.setSelected(false);
                changeTab(1);
                if (listener!= null){
                    listener.onClick(1);
                }
                break;
            case R.id.iv2:
                iv0.setSelected(false);
                iv1.setSelected(false);
                iv2.setSelected(true);
                iv3.setSelected(false);
                changeTab(2);
                if (listener!= null){
                    listener.onClick(2);
                }
                break;
            case R.id.iv3:
                iv0.setSelected(false);
                iv1.setSelected(false);
                iv2.setSelected(false);
                iv3.setSelected(true);
                changeTab(3);
                if (listener!= null){
                    listener.onClick(3);
                }
                break;
        }
    }

    private void changeTab(int index) {
        if (index==0){
            supportFragmentManager.beginTransaction()
                    .show(fragmentList.get(0))
                    .hide(fragmentList.get(1))
                    .hide(fragmentList.get(2))
                    .hide(fragmentList.get(3))
                    .commitAllowingStateLoss();
        } else if (index==1){
            supportFragmentManager.beginTransaction()
                    .hide(fragmentList.get(0))
                    .show(fragmentList.get(1))
                    .hide(fragmentList.get(2))
                    .hide(fragmentList.get(3))
                    .commitAllowingStateLoss();
        } else if (index==2){
            supportFragmentManager.beginTransaction()
                    .hide(fragmentList.get(0))
                    .hide(fragmentList.get(1))
                    .show(fragmentList.get(2))
                    .hide(fragmentList.get(3))
                    .commitAllowingStateLoss();
        } else if (index==3){
            supportFragmentManager.beginTransaction()
                    .hide(fragmentList.get(0))
                    .hide(fragmentList.get(1))
                    .hide(fragmentList.get(2))
                    .show(fragmentList.get(3))
                    .commitAllowingStateLoss();
        }
    }
}
