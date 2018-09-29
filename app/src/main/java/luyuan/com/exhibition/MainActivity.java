package luyuan.com.exhibition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.ui.activity.BaseActivity;
import luyuan.com.exhibition.ui.activity.CompanyListActivity;
import luyuan.com.exhibition.ui.widget.BottomNavigationView;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;
import luyuan.com.exhibition.ui.widget.HomeServiceView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_view)
    BottomNavigationView bottomView;
    private DefaultTopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottomView.initFragment(getSupportFragmentManager());
        initListener();
    }

    private void initListener() {
        bottomView.setListener(new BottomNavigationView.Listener() {
            @Override
            public void onClick(int index) {
                if (index==0){
                    topBar.setTitle("汇展俱乐部");
                }else if (index==1){
                    topBar.setTitle("分类");
                }else if (index==2){
                    topBar.setTitle("聊天");
                }else if (index==3){
                    topBar.setTitle("我的");
                }
            }
        });
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        topBar = new DefaultTopBar(this,"汇展俱乐部");
        return topBar;
    }

    @Override
    public boolean isShowTopBar() {
        return true;
    }
}
