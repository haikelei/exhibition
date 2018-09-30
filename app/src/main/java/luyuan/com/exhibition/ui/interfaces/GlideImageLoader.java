package luyuan.com.exhibition.ui.interfaces;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.youth.banner.loader.ImageLoader;

/**
 * @author: lujialei
 * @date: 2018/9/30
 * @describe:
 */


public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);
    }
}
