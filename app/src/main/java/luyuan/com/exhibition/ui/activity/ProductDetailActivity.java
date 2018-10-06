package luyuan.com.exhibition.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.ProductDetailBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.interfaces.GlideImageLoader;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:我的主页
 */


public class ProductDetailActivity extends BaseActivity {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_name)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    public static final String PRODUCT_ID = "product_id";
    private int productId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        productId = getIntent().getIntExtra(PRODUCT_ID,0);
        initView();
        loadData();
    }

    private void loadData() {
        HttpManager.post(HttpManager.PRODUCT_DETAIL)
                .params("products_id",String.valueOf(productId))
                .execute(new SimpleCallBack<ProductDetailBean>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(ProductDetailBean productDetailBean) {
                        tvTitle.setText(productDetailBean.getTitle());
                        tvContent.setText(productDetailBean.getContent());
                        ArrayList list = new ArrayList();
                        for (int i = 0; i < productDetailBean.getThumbs().size(); i++) {
                            list.add(productDetailBean.getThumbs().get(i).getImage_url());
                        }
                        banner.setImages(list).setImageLoader(new GlideImageLoader()).setDelayTime(5000).start();
                    }
                });
    }

    private void initView() {

    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "产品详情", true);
        return topBar;
    }
}
