package luyuan.com.exhibition.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.R;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class HomeBannerView extends FrameLayout {
    @BindView(R.id.banner)
    Banner banner;

    public HomeBannerView(@NonNull Context context) {
        this(context, null);
    }

    public HomeBannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.layout_banner_home, this, true);
        ButterKnife.bind(this);
        ArrayList list = new ArrayList();
        list.add("http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg");
        banner.setImages(list).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context)
                        .load(path)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(imageView);
            }
        }).start();
    }
}
