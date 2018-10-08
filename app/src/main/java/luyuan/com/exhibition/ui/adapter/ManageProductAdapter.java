package luyuan.com.exhibition.ui.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.ProductListBean;
import luyuan.com.exhibition.ui.activity.ProductDetailActivity;
import luyuan.com.exhibition.utils.Const;

import static luyuan.com.exhibition.ui.activity.ProductDetailActivity.PRODUCT_ID;

/**
 * @author: lujialei
 * @date: 2018/10/8
 * @describe:
 */


public class ManageProductAdapter extends RecyclerView.Adapter<ManageProductAdapter.MyHolder> {



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_manage_product,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvContent.setText(list.get(position).getContent());
        Glide.with(holder.itemView.getContext())
                .load(Const.IMG_PRE+list.get(position).getThumb())
                .into(holder.iv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                intent.putExtra(PRODUCT_ID,list.get(position).getProducts_id());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    private ArrayList<ProductListBean> list;
    public void setData(ArrayList<ProductListBean> list) {
        this.list = list;
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        public ImageView iv;
        public TextView tvTitle;
        public TextView tvContent;

        public MyHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_icon);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
