package luyuan.com.exhibition.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhouyou.http.body.ProgressResponseCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;
import luyuan.com.exhibition.BuildConfig;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CityBean;
import luyuan.com.exhibition.bean.UserBean;
import luyuan.com.exhibition.bean.UserInfoBean;
import luyuan.com.exhibition.event.EventModifyAvatar;
import luyuan.com.exhibition.event.EventModifyNickname;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;
import luyuan.com.exhibition.utils.Const;
import luyuan.com.exhibition.utils.FileUtil;
import luyuan.com.exhibition.utils.SettingManager;

import static luyuan.com.exhibition.utils.FileUtil.getRealFilePathFromUri;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:
 */


public class MyInfomationActivity extends BaseActivity {
    @BindView(R.id.rl_logo)
    RelativeLayout rlLogo;
    @BindView(R.id.rl_nickname)
    RelativeLayout rlNickname;
    @BindView(R.id.rl_phone)
    RelativeLayout rlPhone;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.rl_email)
    RelativeLayout rlEmail;
    @BindView(R.id.rl_zhizhao)
    RelativeLayout rlZhizhao;
    @BindView(R.id.rl_faren)
    RelativeLayout rlFaren;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_yingye)
    TextView tvYingye;
    @BindView(R.id.tv_faren)
    TextView tvFaren;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求相册
    private static final int REQUEST_ZHIZHAO = 105;
    //请求相册
    private static final int REQUEST_FAREN = 106;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    @BindView(R.id.tv_city)
    TextView tvCity;
    //调用照相机返回图片文件
    private File tempFile;
    // 1: qq, 2: weixin
    private int type = 2;
    private List<CityBean> cityList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_settings);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        loadCitys();
    }

    private void loadCitys() {
        HttpManager.post(HttpManager.GET_CITY_LIST)
                .execute(new SimpleCallBack<List<CityBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<CityBean> categoryBeans) {
                        cityList = categoryBeans;
                    }
                });
    }


    private void loadData() {
        HttpManager.post(HttpManager.GET_USER_INFO)
                .params("token", SettingManager.getInstance().getToken())
                .execute(new SimpleCallBack<UserBean>() {
                    @Override
                    public void onError(ApiException e) {
                        if (e.getCode() == 401) {
                            finish();
                        }
                    }

                    @Override
                    public void onSuccess(UserBean userBean) {
                        if (!TextUtils.isEmpty(userBean.getProfile().getHeadimgurl())) {
                            Glide.with(getBaseContext())
                                    .load(Const.IMG_PRE + userBean.getProfile().getHeadimgurl())
                                    .into(ivAvatar);
                        }
                        if (!TextUtils.isEmpty(userBean.getProfile().getNickname())) {
                            tvNickname.setText(userBean.getProfile().getNickname());
                        }
                        if (!TextUtils.isEmpty(userBean.getProfile().getPhone())) {
                            tvPhone.setText(userBean.getProfile().getPhone());
                        }
                        if (!TextUtils.isEmpty(userBean.getProfile().getAddress())) {
                            tvAddress.setText(userBean.getProfile().getAddress());
                        }
                        if (!TextUtils.isEmpty(userBean.getProfile().getEmail())) {
                            tvEmail.setText(userBean.getProfile().getEmail());
                        }
                        //    0=未提交；1=待审核；2=已通过审核；9=审核不通过；
                        int zhizhaoStatus = userBean.getProfile().getLicense_pic_status();
                        if (zhizhaoStatus == 0) {
                            tvYingye.setText("未提交");
                        } else if (zhizhaoStatus == 1) {
                            tvYingye.setText("待审核");
                        } else if (zhizhaoStatus == 2) {
                            tvYingye.setText("已通过审核");
                        } else if (zhizhaoStatus == 9) {
                            tvYingye.setText("审核不通过");
                        }
                        int farenStatus = userBean.getProfile().getLegal_pic_status();
                        if (farenStatus == 0) {
                            tvFaren.setText("未提交");
                        } else if (farenStatus == 1) {
                            tvFaren.setText("待审核");
                        } else if (farenStatus == 2) {
                            tvFaren.setText("已通过审核");
                        } else if (farenStatus == 9) {
                            tvFaren.setText("审核不通过");
                        }

                    }
                });
    }

    private void initView() {

    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "我的资料", true);
        return topBar;
    }

    @OnClick({R.id.rl_logo, R.id.rl_nickname, R.id.rl_email, R.id.rl_address, R.id.rl_phone, R.id.rl_zhizhao, R.id.rl_faren, R.id.rl_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_logo:
                uploadHeadImage();
                break;
            case R.id.rl_nickname:
                startActivityForResult(new Intent(this, ModifyNicknameActivity.class), 2);
                break;
            case R.id.rl_phone:
                Intent intent = new Intent(this, ModifyPhoneActivity.class);
                intent.putExtra("value", tvPhone.getText().toString());
                startActivityForResult(intent, 3);
                break;
            case R.id.rl_address:
                Intent intent1 = new Intent(this, ModifyAddressActivity.class);
                intent1.putExtra("value", tvAddress.getText().toString());
                startActivityForResult(intent1, 4);
                break;
            case R.id.rl_email:
                Intent intent2 = new Intent(this, ModifyEmailActivity.class);
                intent2.putExtra("value", tvEmail.getText().toString());
                startActivityForResult(intent2, 5);
                break;
            case R.id.rl_zhizhao:
                //跳转到调用系统图库
                Intent intent4 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent4, "请选择图片"), REQUEST_ZHIZHAO);
                break;
            case R.id.rl_faren:
                //跳转到调用系统图库
                Intent intent5 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent5, "请选择图片"), REQUEST_FAREN);
                break;
            case R.id.rl_city:
                onOptionPicker();
                break;
        }
    }


    public void onOptionPicker() {
        if (cityList != null) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < cityList.size(); i++) {
                if (cityList.get(i)!=null&&cityList.get(i).getRegion_name()!=null){
                    list.add(cityList.get(i).getRegion_name());
                }
            }
            OptionPicker picker = new OptionPicker(this, list);
            picker.setCanceledOnTouchOutside(false);
            picker.setDividerRatio(WheelView.DividerConfig.FILL);
            picker.setShadowColor(Color.RED, 40);
            picker.setSelectedIndex(1);
            picker.setCycleDisable(true);
            picker.setTextSize(11);
            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    tvCity.setText(item);
                    uploadCity(item);
                }
            });
            picker.show();
        } else {
            Toast.makeText(this, "获取城市列表势失败", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadCity(String cityname) {
        int cityId=0;
        for (int i = 0; i <cityList.size() ; i++) {
            if (cityList.get(i)!=null&&cityname.equals(cityList.get(i).getRegion_name())){
                cityId = cityList.get(i).getCity_id();
            }
        }
        final int finalCityId = cityId;
        HttpManager.post(HttpManager.EDIT_USERINFO)
                .params("token", SettingManager.getInstance().getToken())
                .params("city_id",String.valueOf(cityId))
                .execute(new SimpleCallBack<UserInfoBean>() {
                    @Override
                    public void onError(ApiException e) {
                        e.printStackTrace();
                        Toast.makeText(getBaseContext(), "修改城市失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(UserInfoBean userInfoBean) {
                        Toast.makeText(getBaseContext(), "修改城市成功", Toast.LENGTH_SHORT).show();
                        SettingManager.getInstance().setCityId(String.valueOf(finalCityId));
                    }
                });
    }


    @Subscribe
    public void onNicknameEdited(EventModifyNickname eventModifyNickname) {
        tvNickname.setText(eventModifyNickname.nickname);
    }


    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(MyInfomationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(MyInfomationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCamera();
                }
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(MyInfomationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(MyInfomationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCamera();
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            }
        }
    }


    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(MyInfomationActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        loadData();
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
//                    ivAvatar.setImageBitmap(bitMap);
                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......
                    doUpload(cropImagePath);

                }
                break;
            case REQUEST_ZHIZHAO:  //调用系统相册返回 执照
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    zhizhaoFile = new File(cropImagePath);
                    HttpManager.post(HttpManager.EDIT_USERINFO)
                            .params("token", SettingManager.getInstance().getToken())
                            .params("license_pic", zhizhaoFile, new ProgressResponseCallBack() {
                                @Override
                                public void onResponseProgress(long bytesWritten, long contentLength, boolean done) {

                                }
                            })
                            .execute(new SimpleCallBack<UserInfoBean>() {
                                @Override
                                public void onError(ApiException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onSuccess(UserInfoBean userInfoBean) {
                                    Toast.makeText(getBaseContext(), "提交审核", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
            case REQUEST_FAREN:  //调用系统相册返回 法人
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    farenFile = new File(cropImagePath);
                    HttpManager.post(HttpManager.EDIT_USERINFO)
                            .params("token", SettingManager.getInstance().getToken())
                            .params("legal_pic", farenFile, new ProgressResponseCallBack() {
                                @Override
                                public void onResponseProgress(long bytesWritten, long contentLength, boolean done) {

                                }
                            })
                            .execute(new SimpleCallBack<UserInfoBean>() {
                                @Override
                                public void onError(ApiException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onSuccess(UserInfoBean userInfoBean) {
                                    Toast.makeText(getBaseContext(), "提交审核", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
        }
    }

    private File zhizhaoFile;
    private File farenFile;

    private void doUpload(String file) {
        File imageFile = new File(file);//将要保存图片的路径
        HttpManager.post(HttpManager.EDIT_USERINFO)
                .params("token", SettingManager.getInstance().getToken())
                .params("headimgurl", imageFile, new ProgressResponseCallBack() {
                    @Override
                    public void onResponseProgress(long bytesWritten, long contentLength, boolean done) {

                    }
                })
                .execute(new SimpleCallBack<UserInfoBean>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(UserInfoBean userInfoBean) {
                        SettingManager.getInstance().setAvatar(userInfoBean.getProfile().getHeadimgurl());
                        Glide.with(getBaseContext())
                                .load(Const.IMG_PRE + userInfoBean.getProfile().getHeadimgurl())
                                .into(ivAvatar);
                        EventModifyAvatar eventModifyAvatar = new EventModifyAvatar();
                        eventModifyAvatar.avatar = userInfoBean.getProfile().getHeadimgurl();
                        EventBus.getDefault().post(eventModifyAvatar);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }
}
