package luyuan.com.exhibition;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.util.EMLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.ui.activity.BaseActivity;
import luyuan.com.exhibition.ui.activity.CompanyListActivity;
import luyuan.com.exhibition.ui.activity.VoiceCallActivity;
import luyuan.com.exhibition.ui.widget.BottomNavigationView;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;
import luyuan.com.exhibition.ui.widget.HomeServiceView;
import luyuan.com.exhibition.utils.PermissionHelper;
import luyuan.com.exhibition.utils.PermissionInterface;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_view)
    BottomNavigationView bottomView;
    private DefaultTopBar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottomView.initFragment(getSupportFragmentManager());
        initListener();
        setBrocast();
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


    private void setBrocast() {
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        registerReceiver(new CallReceiver(), callFilter);

        EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, CallError error) {
                switch (callState) {
                    case CONNECTING: // 正在连接对方
                        Log.e("lujialei","CONNECTING");
                        break;
                    case CONNECTED: // 双方已经建立连接
                        Log.e("lujialei","CONNECTED");
                        break;

                    case ACCEPTED: // 电话接通成功
                        Log.e("lujialei","ACCEPTED");
                        break;
                    case DISCONNECTED: // 电话断了
                        Log.e("lujialei","DISCONNECTED");
                        break;
                    case NETWORK_UNSTABLE: //网络不稳定
                        Log.e("lujialei","NETWORK_UNSTABLE");
                        if(error == CallError.ERROR_NO_DATA){
                            //无通话数据
                        }else{
                        }
                        break;
                    case NETWORK_NORMAL: //网络恢复正常
                        Log.e("lujialei","NETWORK_NORMAL");
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void initListener() {
        bottomView.setListener(new BottomNavigationView.Listener() {
            @Override
            public void onClick(int index) {
                if (index==0){
                    topBar.setTitle("汇展俱乐部");
                }else if (index==1){
                    topBar.setTitle("分类");
                }else if (index==2){
                    topBar.setTitle("聊天");
                }else if (index==3){
                    topBar.setTitle("我的");
                }
            }
        });
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        topBar = new DefaultTopBar(this,"汇展俱乐部");
        return topBar;
    }

    @Override
    public boolean isShowTopBar() {
        return true;
    }

    private class CallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //username
            String from = intent.getStringExtra("from");
            //call type
            String type = intent.getStringExtra("type");
            if("video".equals(type)){ //video call
//                context.startActivity(new Intent(context, VideoCallActivity.class).
//                        putExtra("username", from).putExtra("isComingCall", true).
//                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }else{ //voice call
                context.startActivity(new Intent(context, VoiceCallActivity.class).
                        putExtra("username", from).putExtra("isComingCall", true).
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            EMLog.d("CallReceiver", "app received a incoming call");
        }
    }
}
