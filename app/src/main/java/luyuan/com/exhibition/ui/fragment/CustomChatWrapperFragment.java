package luyuan.com.exhibition.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.CustomEaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseChatExtendMenu;

import luyuan.com.exhibition.R;
import luyuan.com.exhibition.ui.activity.VoiceCallActivity;

/**
 * @author: lujialei
 * @date: 2018/10/6
 * @describe:
 */


public class CustomChatWrapperFragment extends CustomEaseChatFragment {


    /**
     * make a voice call
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // voiceCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }


    @Override
    protected void registerExtendMenuItem() {
        //use the menu in base class
        super.registerExtendMenuItem();
        inputMenu.registerExtendMenuItem("通话", R.drawable.em_chat_voice_call_selector, 4, new EaseChatExtendMenu.EaseChatExtendMenuItemClickListener() {
            @Override
            public void onClick(int itemId, View view) {
                startVoiceCall();
            }
        });
    }
}
