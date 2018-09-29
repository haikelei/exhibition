package luyuan.com.exhibition.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CategoryBean;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class CategoryLeftAdapter extends BaseQuickAdapter<CategoryBean,BaseViewHolder> {

    public CategoryLeftAdapter(@Nullable List<CategoryBean> data) {
        super(R.layout.layout_adapter_category_left,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        helper.setTextColor(R.id.tv,item.isChecked?
                helper.getConvertView().getContext().getResources().getColor(R.color.c_165ce8)
                :helper.getConvertView().getContext().getResources().getColor(R.color.c_666666));
        helper.setGone(R.id.line,item.isChecked);
        helper.setText(R.id.tv,item.getName());
        helper.setBackgroundColor(R.id.root,item.isChecked?
                helper.getConvertView().getContext().getResources().getColor(R.color.c_22000000)
                :helper.getConvertView().getContext().getResources().getColor(R.color.c_ffffff));
    }
}
