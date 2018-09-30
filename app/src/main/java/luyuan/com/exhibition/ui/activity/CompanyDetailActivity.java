package luyuan.com.exhibition.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.ui.interfaces.GlideImageLoader;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;

/**
 * @author: lujialei
 * @date: 2018/9/30
 * @describe:
 */


public class CompanyDetailActivity extends BaseActivity {
    @BindView(R.id.banner)
    Banner banner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        ButterKnife.bind(this);
        initView();
        loadData();
    }

    @Override
    public boolean isShowTopBar() {
        return true;
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this,"企业详情",true);
        topBar.showChatView(true);
        topBar.setOnTopBarClickListener(new DefaultTopBar.OnTopBarClickListener() {
            @Override
            public void onChatClick(View v) {

            }
        });
        return topBar;
    }

    private void loadData() {

    }



    private void initView() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 3; i++) {
            list.add("http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg");
        }
        banner.setImages(list);
        banner.setImageLoader(new GlideImageLoader()).start();
    }
}
