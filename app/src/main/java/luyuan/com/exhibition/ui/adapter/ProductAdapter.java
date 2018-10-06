package luyuan.com.exhibition.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CompanyProductBean;
import luyuan.com.exhibition.utils.Const;

/**
 * @author: lujialei
 * @date: 2018/10/6
 * @describe:
 */


public class ProductAdapter extends BaseQuickAdapter<CompanyProductBean,BaseViewHolder> {
    public ProductAdapter(@Nullable List<CompanyProductBean> data) {
        super(R.layout.layout_item_company_product,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyProductBean item) {
        helper.setText(R.id.tv_product_name,item.getTitle());
        helper.setText(R.id.tv_product_intro,item.getContent());
        ImageView iv = helper.getView(R.id.iv_icon);
        Glide.with(helper.itemView.getContext())
                .load(Const.IMG_PRE+item.getThumb())
                .into(iv);
    }
}
