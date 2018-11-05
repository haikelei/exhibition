package luyuan.com.exhibition.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.loader.ImageLoader;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CompanyProductBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.adapter.ProductListAdapter;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;
import luyuan.com.exhibition.utils.Const;

import static luyuan.com.exhibition.ui.activity.CompanyDetailActivity.BOOTH_ID;
import static luyuan.com.exhibition.ui.activity.ProductDetailActivity.PRODUCT_ID;

/**
 * @author: lujialei
 * @date: 2018/11/5
 * @describe:
 */


public class ProductListActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    private ArrayList<CompanyProductBean> dataList;
    private ProductListAdapter adapter;
    private int boothId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_product_list);
        boothId = getIntent().getIntExtra(BOOTH_ID, 0);
        ButterKnife.bind(this);
        initView();
        loadData();
    }

    private void loadData() {
        HttpManager.post(HttpManager.COMPANY_PRODUCTS)
                .params("booth_id", String.valueOf(boothId))
                .execute(new SimpleCallBack<List<CompanyProductBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final List<CompanyProductBean> companyProductBeans) {
                        dataList.addAll(companyProductBeans);
                        adapter.notifyDataSetChanged();

                    }
                });
    }

    private void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        dataList = new ArrayList<>();
        adapter = new ProductListAdapter(dataList);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(view.getContext(), ProductDetail2Activity.class);
                intent.putExtra(PRODUCT_ID,dataList.get(position).getProducts_id());
               startActivity(intent);
            }
        });
    }




    @Override
    protected View onCreateTopBar(ViewGroup view) {
        return new DefaultTopBar(this, "产品列表", true);
    }
}
