package luyuan.com.exhibition.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CategoryBean;
import luyuan.com.exhibition.bean.CityBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.adapter.DownLeftAdapter;
import luyuan.com.exhibition.ui.adapter.DownRightAdapter;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class DownLeftRecyclerView extends FrameLayout {
    @BindView(R.id.rv)
    RecyclerView rv;
    DownLeftAdapter mAdapter;
    private List<CityBean> cityList;

    public DownLeftRecyclerView(@NonNull Context context) {
        super(context);
        initView(context);
        loadData();
    }

    public void setData(List<CityBean> categoryBeans){
        cityList = categoryBeans;
        mAdapter = new DownLeftAdapter(categoryBeans);
        rv.setAdapter(mAdapter);
    }

    private void loadData() {

    }

    private void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.layout_down_recycler, this, true);
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(context));
    }
}
