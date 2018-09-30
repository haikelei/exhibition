package luyuan.com.exhibition.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CompanyListBean;

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

    }
}
