package luyuan.com.exhibition.ui.activity;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_two);
        ButterKnife.bind(this);
        CategoryFragment fragment = new CategoryFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container,fragment).commit();
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "分类", true);
        return topBar;
    }
}
