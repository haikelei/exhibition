package luyuan.com.exhibition.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.ProductDetailBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.interfaces.GlideImageLoader;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:产品详情
 */


public class ProductDetail2Activity extends BaseActivity {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.jz_video)
    JzvdStd mJzvdStd;
    public static final String PRODUCT_ID = "product_id";
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.webview_content)
    WebView webviewContent;
    private int productId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail2);
        ButterKnife.bind(this);
        productId = getIntent().getIntExtra(PRODUCT_ID, 0);
        initView();
        loadData();
    }

    private void loadData() {
        HttpManager.post(HttpManager.PRODUCT_DETAIL)
                .params("products_id", String.valueOf(productId))
                .execute(new SimpleCallBack<ProductDetailBean>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(ProductDetailBean productDetailBean) {
                        ArrayList list = new ArrayList();
                        for (int i = 0; i < productDetailBean.getThumbs().size(); i++) {
                            list.add(productDetailBean.getThumbs().get(i).getImage_url());
                        }
                        if (list.size() == 0) {
                            list.add("");
                        }
                        banner.setImages(list).setImageLoader(new GlideImageLoader()).setDelayTime(5000).start();

                        mJzvdStd.setUp(productDetailBean.vd_src, ""
                                , JzvdStd.SCREEN_WINDOW_NORMAL);

                        webview.loadUrl(productDetailBean.td_path);
                        webviewContent.loadData(productDetailBean.getContent(), "text/html; charset=UTF-8", null);
                    }
                });
    }

    private void initView() {
        Glide.with(this)
                .load("http://zh.online-sh.com/UpLoadFile/video/videoposter.png")
                .into(mJzvdStd.thumbImageView);
        initWebView();
    }

    private void initWebView() {
        WebSettings webSettings = webview.getSettings();
//支持缩放，默认为true。
        webSettings.setSupportZoom(false);
//调整图片至适合webview的大小
        webSettings.setUseWideViewPort(true);
// 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
//设置默认编码
        webSettings.setDefaultTextEncodingName("utf-8");
////设置自动加载图片
        webSettings.setLoadsImagesAutomatically(true);



        WebSettings webSettings1 = webviewContent.getSettings();
//支持缩放，默认为true。
        webSettings1.setSupportZoom(false);
//调整图片至适合webview的大小
        webSettings1.setUseWideViewPort(true);
// 缩放至屏幕的大小
        webSettings1.setLoadWithOverviewMode(true);
//设置默认编码
        webSettings1.setDefaultTextEncodingName("utf-8");
////设置自动加载图片
        webSettings1.setLoadsImagesAutomatically(true);
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "产品详情", true);
        return topBar;
    }

    @OnClick({R.id.tv_video, R.id.tv_td, R.id.tv_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_video:
                banner.setVisibility(View.GONE);
                mJzvdStd.setVisibility(View.VISIBLE);
                webview.setVisibility(View.GONE);

                break;
            case R.id.tv_td:
                banner.setVisibility(View.GONE);
                mJzvdStd.setVisibility(View.GONE);
                webview.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_image:
                banner.setVisibility(View.VISIBLE);
                mJzvdStd.setVisibility(View.GONE);
                webview.setVisibility(View.GONE);
                break;
        }
    }
}
