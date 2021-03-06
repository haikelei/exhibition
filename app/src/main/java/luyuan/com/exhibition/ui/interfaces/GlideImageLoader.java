package luyuan.com.exhibition.ui.interfaces;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.youth.banner.loader.ImageLoader;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.utils.Const;

/**
 * @author: lujialei
 * @date: 2018/9/30
 * @describe:
 */


public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Glide.with(context)
                .load(Const.IMG_PRE+path)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(R.mipmap.no_image)
                .placeholder(R.mipmap.no_image)
                .into(imageView);
    }
}
