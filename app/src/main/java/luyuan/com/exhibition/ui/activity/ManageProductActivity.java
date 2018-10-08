package luyuan.com.exhibition.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.ProductListBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.adapter.ManageProductAdapter;
import luyuan.com.exhibition.ui.widget.PlusTopBar;
import luyuan.com.exhibition.utils.SettingManager;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:
 */


public class ManageProductActivity extends BaseActivity {
    @BindView(R.id.rv)
    SwipeMenuRecyclerView swipeRecyclerView;
    ManageProductAdapter mAdapter;
    private ArrayList<ProductListBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_manage_product);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        HttpManager.post(HttpManager.GET_PRODUCT_LIST)
                .params("token", SettingManager.getInstance().getToken())
                .params("pagesize",String.valueOf(100))
                .execute(new SimpleCallBack<List<ProductListBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<ProductListBean> tList) {
                        list = (ArrayList<ProductListBean>) tList;
                        mAdapter.setData(list);
                        mAdapter.notifyDataSetChanged();

                    }
                });
    }

    private void initView() {
        swipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {

                int width = 140;
                // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
                // 2. 指定具体的高，比如80;
                // 3. WRAP_CONTENT，自身高度，不推荐;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                SwipeMenuItem deleteItem = new SwipeMenuItem(getBaseContext())
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.mipmap.ic_action_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                // 各种文字和图标属性设置。
                rightMenu.addMenuItem(deleteItem); // 在Item右侧添加一个菜单。


            }
        };

        // 设置监听器。
        swipeRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        swipeRecyclerView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                int position = menuBridge.getAdapterPosition();
                deleteProduct(menuBridge,position);
            }
        });
        mAdapter = new ManageProductAdapter();
        swipeRecyclerView.setAdapter(mAdapter);

    }

    private void deleteProduct(final SwipeMenuBridge menuBridge, final int position) {
        HttpManager.post(HttpManager.DELETE_PRODUCT)
                .params("token",SettingManager.getInstance().getToken())
                .params("products_id",String.valueOf(list.get(position).getProducts_id()))
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        Toast.makeText(getBaseContext(),"删除失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String s) {
                        menuBridge.closeMenu();
                        list.remove(position);
                        if (mAdapter!=null){
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        PlusTopBar topBar = new PlusTopBar(this, "产品管理", true);
        topBar.setOnTopBarClickListener(new PlusTopBar.OnTopBarClickListener() {
            @Override
            public void onChatClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });
        return topBar;
    }
}
