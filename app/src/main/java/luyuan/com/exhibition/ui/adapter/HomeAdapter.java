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


public class HomeAdapter extends BaseQuickAdapter<CategoryBean,BaseViewHolder> {
    public HomeAdapter(@Nullable List<CategoryBean> data) {
        super(R.layout.layout_category_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        helper.setText(R.id.tv,item.getName());
    }
}
