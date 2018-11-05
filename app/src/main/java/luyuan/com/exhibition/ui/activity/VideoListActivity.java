package luyuan.com.exhibition.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CompanyProductBean;
import luyuan.com.exhibition.bean.VideoDetailBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.adapter.ProductListAdapter;
import luyuan.com.exhibition.ui.adapter.VideoPagerAdapter;
import luyuan.com.exhibition.ui.fragment.VideoFragment;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;

import static luyuan.com.exhibition.ui.activity.CompanyDetailActivity.BOOTH_ID;
import static luyuan.com.exhibition.ui.activity.ProductDetailActivity.PRODUCT_ID;

/**
 * @author: lujialei
 * @date: 2018/11/5
 * @describe:
 */


public class VideoListActivity extends BaseActivity {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    private ArrayList<VideoDetailBean> dataList;
    private int boothId;
    private AdapterRecyclerViewVideo adapter;

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
        HttpManager.post(HttpManager.GET_COM_VIDEO)
                .params("booth_id", String.valueOf(boothId))
                .execute(new SimpleCallBack<List<VideoDetailBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<VideoDetailBean> list) {
                        ArrayList tmpList = new ArrayList();
                        for (int i = 0; i < list.size(); i++) {
                            VideoFragment videoFragment = VideoFragment.getInstance(list.get(i));
                            tmpList.add(videoFragment);
                        }
                        dataList.addAll(list);
                        adapter.notifyDataSetChanged();

                    }
                });
    }

    private void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        dataList = new ArrayList<>();
        adapter = new AdapterRecyclerViewVideo(this,dataList);
        recycler.setAdapter(adapter);
    }




    @Override
    protected View onCreateTopBar(ViewGroup view) {
        return new DefaultTopBar(this, "视频列表", true);
    }
}
