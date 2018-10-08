package luyuan.com.exhibition.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CategoryBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.activity.CategoryActivity;
import luyuan.com.exhibition.ui.activity.CompanyListActivity;
import luyuan.com.exhibition.ui.adapter.HomeAdapter;
import luyuan.com.exhibition.ui.widget.HomeBannerView;
import luyuan.com.exhibition.ui.widget.HomeServiceView;

import static luyuan.com.exhibition.ui.activity.CategoryActivity.PARENT_ID;
import static luyuan.com.exhibition.ui.activity.CompanyListActivity.CATEGORY_BEAN;

/**
 * @author: lujialei
 * @date: 2018/9/27
 * @describe:
 */


public class HomeFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.rv)
    RecyclerView rv;
    HomeAdapter mAdapter;
    private List<CategoryBean> list = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        loadData();
    }

    private void loadData(){
                HttpManager.post("Trade/getCategoryTree")
                //这里不想解析，简单只是为了做演示 直接返回String
                .execute(new SimpleCallBack<List<CategoryBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<CategoryBean> categoryBeans) {
                        list.addAll(categoryBeans);
                        mAdapter.notifyDataSetChanged();
                    }
                });




    }

    private void initView() {
        rv.setLayoutManager(new GridLayoutManager(getContext(),4));
        mAdapter= new HomeAdapter(list);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra(PARENT_ID,list.get(position).getTrade_id());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
