package luyuan.com.exhibition.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CategoryBean;
import luyuan.com.exhibition.ui.activity.CompanyListActivity;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class MyGridAdapter extends BaseAdapter {
    private List<CategoryBean> list;
    private LayoutInflater layoutInflater;

    public MyGridAdapter(Context context,List<CategoryBean> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view  = layoutInflater.inflate(R.layout.layout_category_grid_item,null);
        ImageView imageView = view.findViewById(R.id.iv);
        TextView textView = view.findViewById(R.id.tv);
        textView.setText(list.get(position).getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CompanyListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(CompanyListActivity.CATEGORY_BEAN,list.get(position));
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }
}
