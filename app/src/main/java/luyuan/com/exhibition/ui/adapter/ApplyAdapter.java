package luyuan.com.exhibition.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.ApplyBean;

/**
 * @author: lujialei
 * @date: 2018/10/7
 * @describe:
 */


public class ApplyAdapter extends BaseMultiItemQuickAdapter<ApplyBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ApplyAdapter(List<ApplyBean> data) {
        super(data);
        addItemType(MutipleItem.IMG,R.layout.layout_item_image);
        addItemType(MutipleItem.PLUS, R.layout.layout_item_plus);
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplyBean item) {
        switch (helper.getItemViewType()) {
            case MutipleItem.IMG:
                ImageView imageView = helper.getView(R.id.iv);
                Glide.with(helper.itemView.getContext())
                        .load(item.getPath())
                        .into(imageView);
                break;
            case MutipleItem.PLUS:

                break;
        }
    }
}
