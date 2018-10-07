package luyuan.com.exhibition.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import luyuan.com.exhibition.ui.activity.CompanyListActivity;
import luyuan.com.exhibition.ui.adapter.CategoryLeftAdapter;
import luyuan.com.exhibition.ui.adapter.CategoryRightAdapter;

import static luyuan.com.exhibition.ui.activity.CompanyListActivity.CATEGORY_BEAN;

/**
 * @author: lujialei
 * @date: 2018/9/27
 * @describe:
 */


public class CategoryFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.rv_left)
    RecyclerView rvLeft;
    @BindView(R.id.rv_right)
    RecyclerView rvRight;
    CategoryLeftAdapter mLeftAdapter;
    private List<CategoryBean> leftList = new ArrayList<>();
    CategoryRightAdapter mRightAdapter;
    private List<CategoryBean> rightList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListener();
        loadData();

    }

    private void loadData() {
        HttpManager.post("Trade/getCategoryTree")
                .execute(new SimpleCallBack<List<CategoryBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<CategoryBean> categoryBeans) {
                        leftList.addAll(categoryBeans);
                        mLeftAdapter.notifyDataSetChanged();
                        generateRightList();
                        mRightAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void generateRightList() {
        for (int i = 0; i < leftList.size(); i++) {
            if (leftList.get(i).getChildren()!=null){
                rightList.addAll(leftList.get(i).getChildren());
            }
        }
    }

    private void initListener() {
        mLeftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < leftList.size(); i++) {
                    if (position==i){
                        leftList.get(i).isChecked=true;
                    }else {
                        leftList.get(i).isChecked=false;
                    }
                }
                mLeftAdapter.notifyDataSetChanged();

                int leftId = leftList.get(position).getTrade_id();
                for (int i = 0; i < rightList.size(); i++) {
                    int rightId = rightList.get(i).getParent_id();
                    if (leftId==rightId){
                        rvRight.smoothScrollToPosition(i);
                        return;
                    }
                }


            }
        });
        mRightAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), CompanyListActivity.class);
                intent.putExtra(CATEGORY_BEAN,rightList.get(position));
                startActivity(intent);
            }
        });
    }

    private void initView() {
        rvLeft.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRight.setLayoutManager(new GridLayoutManager(getContext(),3));
        mLeftAdapter = new CategoryLeftAdapter(leftList);
        mRightAdapter = new CategoryRightAdapter(rightList);
        rvLeft.setAdapter(mLeftAdapter);
        rvRight.setAdapter(mRightAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
