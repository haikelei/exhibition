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
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.adapter.DownRightAdapter;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class DownRightRecyclerView extends FrameLayout {
    @BindView(R.id.rv)
    RecyclerView rv;
    DownRightAdapter mAdapter;


    public DownRightRecyclerView(@NonNull Context context) {
        super(context);
        initView(context);
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
//                        寻找所有三级分类
                        ArrayList list = new ArrayList();
                        for (int i = 0; i < categoryBeans.size(); i++) {
                            List<CategoryBean> list1 = categoryBeans.get(i).getChildren();
                            if (list1!=null){
                                for (int j = 0; j < list1.size(); j++) {
                                    if (list1.get(j).getChildren()!=null){
                                        list.addAll(list1.get(j).getChildren());
                                    }

                                }
                            }

                        }
                        mAdapter = new DownRightAdapter(list);
                        rv.setAdapter(mAdapter);
                    }
                });
    }

    private void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.layout_down_recycler, this, true);
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(context));
    }
}
