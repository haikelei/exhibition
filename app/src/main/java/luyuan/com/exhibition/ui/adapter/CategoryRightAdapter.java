package luyuan.com.exhibition.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CategoryBean;
import luyuan.com.exhibition.utils.Const;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class CategoryRightAdapter extends BaseQuickAdapter<CategoryBean,BaseViewHolder> {

    public CategoryRightAdapter(@Nullable List<CategoryBean> data) {
        super(R.layout.layout_category_grid_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        helper.setText(R.id.tv,item.getName());
        ImageView imageView = helper.getView(R.id.iv);
        Glide.with(helper.itemView.getContext())
                .load(Const.IMG_PRE+item.getIcon())
                .into(imageView);
    }
}
