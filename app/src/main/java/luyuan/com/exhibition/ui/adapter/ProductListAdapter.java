package luyuan.com.exhibition.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CompanyProductBean;
import luyuan.com.exhibition.bean.ProductListBean;
import luyuan.com.exhibition.utils.Const;

/**
 * @author: lujialei
 * @date: 2018/11/5
 * @describe:
 */


public class ProductListAdapter extends BaseQuickAdapter<CompanyProductBean,BaseViewHolder> {
    public ProductListAdapter(@Nullable List<CompanyProductBean> data) {
        super(R.layout.layout_item_product_list,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyProductBean item) {
        TextView tv = helper.getView(R.id.tv);
        ImageView iv = helper.getView(R.id.iv);
        tv.setText(item.getTitle());
        Glide.with(iv.getContext()).load(Const.IMG_PRE+item.getThumb()).into(iv);
    }
}
