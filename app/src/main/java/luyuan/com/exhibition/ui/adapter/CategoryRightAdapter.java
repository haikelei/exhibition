package luyuan.com.exhibition.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.GridView;

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


public class CategoryRightAdapter extends BaseQuickAdapter<CategoryBean,BaseViewHolder> {

    public CategoryRightAdapter(@Nullable List<CategoryBean> data) {
        super(R.layout.layout_adapter_category_right,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        GridView gridView = helper.getView(R.id.grid);
        gridView.setAdapter(new MyGridAdapter(helper.getConvertView().getContext(),item.getChildren()));
        helper.setText(R.id.tv,item.getName());
    }
}
