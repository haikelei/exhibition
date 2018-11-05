package luyuan.com.exhibition.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.VideoDetailBean;
import luyuan.com.exhibition.utils.Const;

import static luyuan.com.exhibition.ui.activity.ProductDetailActivity.PRODUCT_ID;

public class AdapterRecyclerViewVideo extends RecyclerView.Adapter<AdapterRecyclerViewVideo.MyViewHolder> {

    public static final String TAG = "AdapterRecyclerViewVideo";
    private Context context;
    private ArrayList<VideoDetailBean> list;

    public AdapterRecyclerViewVideo(Context context,ArrayList<VideoDetailBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_videoview, parent,
                false));
        return holder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder [" + holder.jzvdStd.hashCode() + "] position=" + position);

        holder.jzvdStd.setUp(
                Const.IMG_PRE+list.get(position).getVideo_src(),
                "", Jzvd.SCREEN_WINDOW_LIST);
        Glide.with(holder.jzvdStd.getContext()).load(Const.IMG_PRE+list.get(position).getPoster()).into(holder.jzvdStd.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JzvdStd jzvdStd;

        public MyViewHolder(View itemView) {
            super(itemView);
            jzvdStd = itemView.findViewById(R.id.videoplayer);
        }
    }

}
