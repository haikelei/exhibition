package luyuan.com.exhibition.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CompanyListBean;
import luyuan.com.exhibition.utils.Const;
import luyuan.com.exhibition.utils.GlideCircleTransform;

/**
 * @author: lujialei
 * @date: 2018/9/30
 * @describe:
 */


public class CompanyListAdapter extends BaseQuickAdapter<CompanyListBean,BaseViewHolder> {
    public CompanyListAdapter(@Nullable List<CompanyListBean> data) {
        super(R.layout.layout_item_company_list,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyListBean item) {
        helper.setText(R.id.tv,item.getNickname());
        ImageView imageView = helper.getView(R.id.iv);
        Glide.with(helper.itemView.getContext())
                .load(Const.IMG_PRE+item.getHeadimgurl())
                .transform(new GlideCircleTransform(helper.itemView.getContext()))
                .into(imageView);
    }
}
