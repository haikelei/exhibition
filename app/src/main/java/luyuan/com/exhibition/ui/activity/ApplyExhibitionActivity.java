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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhouyou.http.body.ProgressResponseCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

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
import luyuan.com.exhibition.bean.ApplyBean;
import luyuan.com.exhibition.bean.CategoryBean;
import luyuan.com.exhibition.bean.UserInfoBean;
import luyuan.com.exhibition.event.EventModifyAvatar;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.adapter.ApplyAdapter;
import luyuan.com.exhibition.ui.adapter.MutipleItem;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;
import luyuan.com.exhibition.utils.Const;
import luyuan.com.exhibition.utils.FileUtil;
import luyuan.com.exhibition.utils.SettingManager;

import static luyuan.com.exhibition.utils.FileUtil.getRealFilePathFromUri;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:展位申请
 */


public class ApplyExhibitionActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv0)
    TextView tv0;
    @BindView(R.id.tv1)
    TextView tv1;
    private ArrayList list;

    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //调用照相机返回图片文件
    private File tempFile;
    // 1: qq, 2: weixin
    private int type = 2;
    private ApplyAdapter mAdapter;
    private ArrayList<CategoryBean> firstList;
    private ArrayList<CategoryBean> secondList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_apply_exhibition);
        ButterKnife.bind(this);
        initView();
        loadData();
    }

    private void loadData() {
        HttpManager.post("Trade/getCategoryTree")
                .execute(new SimpleCallBack<List<CategoryBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<CategoryBean> categoryBeans) {
                        firstList = (ArrayList<CategoryBean>) categoryBeans;
                    }
                });
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(linearLayoutManager);
        list = new ArrayList();
        list.add(new ApplyBean(MutipleItem.PLUS));
        mAdapter = new ApplyAdapter(list);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == list.size() - 1) {
                    uploadHeadImage();
                }
            }
        });
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
                if (ContextCompat.checkSelfPermission(ApplyExhibitionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(ApplyExhibitionActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
                if (ContextCompat.checkSelfPermission(ApplyExhibitionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(ApplyExhibitionActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
            Uri contentUri = FileProvider.getUriForFile(ApplyExhibitionActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    ArrayList<File> uploadList = new ArrayList();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    Uri uri = Uri.fromFile(tempFile);
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    File file = new File(cropImagePath);
                    ApplyBean bean = new ApplyBean(MutipleItem.IMG);
                    bean.setPath(cropImagePath);
                    for (int i = 0; i < list.size(); i++) {
                        if (i!=list.size()-1){
                            list.remove(i);
                        }
                    }
                    list.add(0, bean);
                    mAdapter.notifyDataSetChanged();
                    uploadList.add(0,file);
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    ApplyBean bean = new ApplyBean(MutipleItem.IMG);
                    bean.setPath(cropImagePath);
                    for (int i = 0; i < list.size(); i++) {
                        if (i!=list.size()-1){
                            list.remove(i);
                        }
                    }
                    list.add(0, bean);
                    mAdapter.notifyDataSetChanged();
                    File file = new File(cropImagePath);
                    uploadList.add(0,file);
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
                }
                break;
        }
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "展位申请", true);
        return topBar;
    }

    @OnClick({R.id.rl0, R.id.rl1, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl0:
                if (firstList != null) {
                    onFirstPicker();
                }
                break;
            case R.id.rl1:
                if (mCurrentFirstTradeId != -1) {
                    loadSecondCategory();
                }
                break;
            case R.id.tv_confirm:
                if (mCurrentFirstTradeId!=-1 && mSecondTradeId!=-1&&uploadList!=null&&uploadList.size()>=1){
                    apply();
                }else {
                    Toast.makeText(getBaseContext(),"请填写完整参数",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void apply() {
        HttpManager.post(HttpManager.APPLY_BOOTH)
                .params("token", SettingManager.getInstance().getToken())
                .params("trade_id",String.valueOf(mSecondTradeId))
                .params("image",uploadList.get(0),new ProgressResponseCallBack() {
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
                        Toast.makeText(ApplyExhibitionActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void loadSecondCategory() {
        HttpManager.post("Trade/getCategoryTree")
                .params("parent_id", String.valueOf(mCurrentFirstTradeId))
                .execute(new SimpleCallBack<List<CategoryBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<CategoryBean> categoryBeans) {
                        secondList = (ArrayList<CategoryBean>) categoryBeans;
                        List list = new ArrayList();
                        for (int i = 0; i < categoryBeans.size(); i++) {
                            list.add(categoryBeans.get(i).getName());
                        }
                        onSecondPicker(list);
                    }
                });
    }

    private void onSecondPicker(List<String> list) {
        OptionPicker picker = new OptionPicker(this, list);
        picker.setCanceledOnTouchOutside(false);
        picker.setHeight(800);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED, 40);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(11);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                mSecondTradeId = firstList.get(index).getTrade_id();
                tv1.setText(secondList.get(index).getName());
            }
        });
        picker.show();
    }

    private int mCurrentFirstTradeId = -1;
    private int mSecondTradeId = -1;

    public void onFirstPicker() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < firstList.size(); i++) {
            list.add(firstList.get(i).getName());
        }
        OptionPicker picker = new OptionPicker(this, list);
        picker.setCanceledOnTouchOutside(false);
        picker.setHeight(800);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED, 40);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(11);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                mCurrentFirstTradeId = firstList.get(index).getTrade_id();
                tv0.setText(firstList.get(index).getName());
            }
        });
        picker.show();
    }
}
