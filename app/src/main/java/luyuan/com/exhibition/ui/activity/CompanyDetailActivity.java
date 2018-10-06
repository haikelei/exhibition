package luyuan.com.exhibition.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CompanyDetailBean;
import luyuan.com.exhibition.bean.CompanyProductBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.adapter.ProductAdapter;
import luyuan.com.exhibition.ui.interfaces.GlideImageLoader;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;
import luyuan.com.exhibition.utils.Const;

import static luyuan.com.exhibition.ui.activity.ChatActivity.CHAT_PASSWORD;
import static luyuan.com.exhibition.ui.activity.ChatActivity.CHAT_STATUS;
import static luyuan.com.exhibition.ui.activity.ChatActivity.CHAT_USER_NAME;


/**
 * @author: lujialei
 * @date: 2018/9/30
 * @describe:
 */


public class CompanyDetailActivity extends BaseActivity {
    @BindView(R.id.banner)
    Banner banner;

    public static final String BOOTH_ID = "booth_id";
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.rv)
    RecyclerView rv;
    private int boothId;
    private ProductAdapter mAdapter;
    private CompanyDetailBean.CompanyDetailsBean.AppChatBean chatBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        ButterKnife.bind(this);
        boothId = getIntent().getIntExtra(BOOTH_ID, 0);
        initView();
        loadData();
    }

    @Override
    public boolean isShowTopBar() {
        return true;
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "企业详情", true);
        topBar.showChatView(true);
        topBar.setOnTopBarClickListener(new DefaultTopBar.OnTopBarClickListener() {
            @Override
            public void onChatClick(View v) {
                if (chatBean!=null){
                    Intent intent = new Intent(getBaseContext(), ChatActivity.class);
                    intent.putExtra(CHAT_PASSWORD,chatBean.getHx_password());
                    intent.putExtra(CHAT_USER_NAME,chatBean.getHx_username());
                    intent.putExtra(CHAT_STATUS,chatBean.getStatus());
                    startActivity(intent);
                }
            }
        });
        return topBar;
    }

    private void loadData() {
        HttpManager.post(HttpManager.COMPANY_DETAIL)
                .params("booth_id", String.valueOf(boothId))
                .execute(new SimpleCallBack<CompanyDetailBean>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(CompanyDetailBean companyDetailBean) {
                        chatBean = companyDetailBean.getCompany_details().getApp_chat();
                        initBanner(companyDetailBean.getBanner_list());
                        tvCompanyName.setText(companyDetailBean.getCompany_details().getNickname());
                        tvDes.setText(companyDetailBean.getCompany_details().getDescribe());
                        Glide.with(CompanyDetailActivity.this)
                                .load(Const.IMG_PRE + companyDetailBean.getCompany_details().getHeadimgurl())
                                .into(ivHead);
                    }
                });
        HttpManager.post(HttpManager.COMPANY_PRODUCTS)
                .params("booth_id", String.valueOf(boothId))
                .execute(new SimpleCallBack<List<CompanyProductBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final List<CompanyProductBean> companyProductBeans) {
                        mAdapter = new ProductAdapter(companyProductBeans);
                        rv.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                int id = companyProductBeans.get(position).getProducts_id();
                                Intent intent = new Intent(getBaseContext(),ProductDetailActivity.class);
                                intent.putExtra(ProductDetailActivity.PRODUCT_ID,id);
                                startActivity(intent);
                            }
                        });
                    }
                });
    }

    private void initBanner(List<CompanyDetailBean.BannerListBean> tmpList) {
        ArrayList list = new ArrayList();
        for (int i = 0; i < tmpList.size(); i++) {
            list.add(tmpList.get(i).getImage_url());
        }
        banner.setImages(list);
        banner.setImageLoader(new GlideImageLoader())
                .setDelayTime(5000)
                .start();
    }


    private void initView() {
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}
