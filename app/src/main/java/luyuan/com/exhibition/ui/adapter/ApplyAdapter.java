package luyuan.com.exhibition.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.ApplyBean;
import luyuan.com.exhibition.utils.Const;

/**
 * @author: lujialei
 * @date: 2018/10/7
 * @describe:
 */


public class ApplyAdapter extends BaseMultiItemQuickAdapter<ApplyBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ApplyAdapter(List<ApplyBean> data) {
        super(data);
        addItemType(MutipleItem.IMG,R.layout.layout_item_image);
        addItemType(MutipleItem.PLUS, R.layout.layout_item_plus);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ApplyBean item) {
        switch (helper.getItemViewType()) {
            case MutipleItem.IMG:
                ImageView imageView = helper.getView(R.id.iv);
                if (!TextUtils.isEmpty(item.getPath())){//本地图片
                    Glide.with(helper.itemView.getContext())
                            .load(item.getPath())
                            .into(imageView);
                }else if (!TextUtils.isEmpty(item.image_url)){//网络图片
                    Glide.with(helper.itemView.getContext())
                            .load(Const.IMG_PRE+item.image_url)
                            .into(imageView);
                }
                ImageView ivDelete = helper.getView(R.id.iv_delete);
                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener!=null){
                            mListener.onDelete(item);
                        }
                    }
                });

                break;
            case MutipleItem.PLUS:

                break;
        }
    }

    public interface Listener{
        void onDelete(ApplyBean item);
    }
    private Listener mListener;
    public void setListener(Listener mListener){
        this.mListener = mListener;
    }
}
