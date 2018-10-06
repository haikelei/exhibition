package luyuan.com.exhibition.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:
 */


public class MyInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_my_info);
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this,"我的资料",true);
        return topBar;
    }
}