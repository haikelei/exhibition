package luyuan.com.exhibition.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.ui.EaseConversationListFragment;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.ui.activity.ChatActivity;
import luyuan.com.exhibition.ui.adapter.ChatListAdaper;

import static luyuan.com.exhibition.ui.activity.ChatActivity.CHAT_PASSWORD;
import static luyuan.com.exhibition.ui.activity.ChatActivity.CHAT_STATUS;
import static luyuan.com.exhibition.ui.activity.ChatActivity.CHAT_USER_NAME;

/**
 * @author: lujialei
 * @date: 2018/9/27
 * @describe:
 */


public class ChatListFragment extends Fragment {

    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                String username = conversation.getLastMessage().getUserName();
                Intent intent = new Intent(getContext(), ChatActivity.class);
                intent.putExtra(CHAT_USER_NAME,username);
                startActivity(intent);
            }
        });
        getChildFragmentManager().beginTransaction().add(R.id.container, conversationListFragment).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
