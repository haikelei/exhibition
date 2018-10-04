package luyuan.com.exhibition.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CategoryBean;
import luyuan.com.exhibition.utils.Const;
import luyuan.com.exhibition.utils.GlideCircleTransform;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class HomeAdapter extends BaseQuickAdapter<CategoryBean,BaseViewHolder> {
    public HomeAdapter(@Nullable List<CategoryBean> data) {
        super(R.layout.layout_category_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        helper.setText(R.id.tv,item.getName());
        ImageView iv = helper.getView(R.id.iv);
        Glide.with(helper.itemView.getContext())
                .load(Const.IMG_PRE+item.getIcon())
                .transform(new GlideCircleTransform(helper.itemView.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(iv);
    }
}
