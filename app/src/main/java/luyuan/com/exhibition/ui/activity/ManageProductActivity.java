package luyuan.com.exhibition.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.ui.widget.PlusTopBar;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:
 */


public class ManageProductActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_manage_product);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rv.setLayoutManager(new LinearLayoutManager(this));

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
