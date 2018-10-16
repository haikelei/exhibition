package luyuan.com.exhibition.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.ui.fragment.ChatWrapperFragment;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;
import luyuan.com.exhibition.utils.PermissionHelper;
import luyuan.com.exhibition.utils.PermissionInterface;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:聊天
 */

public class ChatActivity extends BaseActivity {

    public static final String CHAT_PASSWORD = "password";
    public static final String CHAT_USER_NAME = "username";
    public static final String CHAT_STATUS = "status";
    @BindView(R.id.container)
    FrameLayout container;

    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_chat);
        username = getIntent().getStringExtra(CHAT_USER_NAME);
        ButterKnife.bind(this);
        initView();
        initPermission();
    }

    private PermissionHelper mPermissionHelper;

    private void initPermission() {
        mPermissionHelper = new PermissionHelper(this, new PermissionInterface() {
            @Override
            public int getPermissionsRequestCode() {
                return 10000;
            }

            @Override
            public String[] getPermissions() {
                return new String[]{
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };
            }

            @Override
            public void requestPermissionsSuccess() {
            }

            @Override
            public void requestPermissionsFail() {

            }
        });
        mPermissionHelper.requestPermissions();

    }

    private void initView() {
        ChatWrapperFragment chatFragment = new ChatWrapperFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, username);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }
}
