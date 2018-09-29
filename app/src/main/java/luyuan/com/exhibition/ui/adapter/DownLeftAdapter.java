package luyuan.com.exhibition.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CategoryBean;
import luyuan.com.exhibition.bean.CityBean;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class DownLeftAdapter extends BaseQuickAdapter<CityBean,BaseViewHolder> {
    public DownLeftAdapter(@Nullable List<CityBean> data) {
        super(R.layout.layout_adapter_text,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityBean item) {
        helper.setText(R.id.tv,item.getRegion_name());
    }
}
