package luyuan.com.exhibition.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import luyuan.com.exhibition.bean.CategoryBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.adapter.CategoryRightAdapter;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;

import static luyuan.com.exhibition.ui.activity.CompanyListActivity.CATEGORY_BEAN;

/**
 * @author: lujialei
 * @date: 2018/10/8
 * @describe:
 */


public class CategoryActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    private List<CategoryBean> list = new ArrayList<>();
    public static final String PARENT_ID = "parent_id";
    private int parentId;
    private CategoryRightAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_category);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rv.setLayoutManager(new GridLayoutManager(getBaseContext(),4));
        parentId = getIntent().getIntExtra(PARENT_ID,-1);
        loadCategory(parentId);
    }

    private void loadCategory(int parentId) {
        HttpManager.post(HttpManager.CHILD_PRODUCT)
                .params("parent_id",String.valueOf(parentId))
                .execute(new SimpleCallBack<List<CategoryBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<CategoryBean> categoryBeans) {
                        list.addAll(categoryBeans);
                        mAdapter = new CategoryRightAdapter(list);
                        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent(getBaseContext(), CompanyListActivity.class);
                                intent.putExtra(CATEGORY_BEAN, list.get(position));
                                startActivity(intent);
                            }
                        });
                        rv.setAdapter(mAdapter);
                    }
                });
    }


    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "分类", true);
        return topBar;
    }
}
