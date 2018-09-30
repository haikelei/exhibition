package luyuan.com.exhibition.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import luyuan.com.exhibition.R;

/**
 * @author: lujialei
 * @date: 2018/9/30
 * @describe:
 */


public class ChatListAdaper extends BaseQuickAdapter<String,BaseViewHolder> {
    public ChatListAdaper(@Nullable List<String> data) {
        super(R.layout.layout_chat_list_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
