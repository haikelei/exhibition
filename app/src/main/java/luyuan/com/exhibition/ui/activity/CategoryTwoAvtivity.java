package luyuan.com.exhibition.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.ui.fragment.CategoryFragment;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;

/**
 * @author: lujialei
 * @date: 2018/10/18
 * @describe:
 */


public class CategoryTwoAvtivity extends BaseActivity {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    public static final String PARENT_ID = "parent_id";
    public static final String PARENT_NAME = "parent_name";
    private int parentId;
    private String parentName;
    private DefaultTopBar topBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        parentId = intent.getIntExtra(PARENT_ID,-1);
        parentName =intent.getStringExtra(PARENT_NAME);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_two);
        ButterKnife.bind(this);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setSelectedId(parentId);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment).commit();
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        topBar = new DefaultTopBar(this, parentName, true);
        return topBar;
    }
}
