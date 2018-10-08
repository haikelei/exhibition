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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhouyou.http.body.ProgressResponseCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luyuan.com.exhibition.AddProductBean;
import luyuan.com.exhibition.BuildConfig;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.ApplyBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.adapter.ApplyAdapter;
import luyuan.com.exhibition.ui.adapter.MutipleItem;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;
import luyuan.com.exhibition.utils.FileUtil;
import luyuan.com.exhibition.utils.SettingManager;
import me.leefeng.promptlibrary.PromptDialog;
import okhttp3.MediaType;

import static luyuan.com.exhibition.utils.FileUtil.getRealFilePathFromUri;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:我的主页
 */


public class AddProductActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private ArrayList list;
    private ApplyAdapter mAdapter;

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
    private PromptDialog promptDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_add_product);
        ButterKnife.bind(this);
        initView();
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

        promptDialog = new PromptDialog(this);

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
                if (ContextCompat.checkSelfPermission(AddProductActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(AddProductActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
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
                if (ContextCompat.checkSelfPermission(AddProductActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(AddProductActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
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
            Uri contentUri = FileProvider.getUriForFile(AddProductActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
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
                    list.add(0, bean);
                    mAdapter.notifyDataSetChanged();
                    uploadList.add(file);
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    ApplyBean bean = new ApplyBean(MutipleItem.IMG);
                    bean.setPath(cropImagePath);
                    list.add(0, bean);
                    mAdapter.notifyDataSetChanged();
                    File file = new File(cropImagePath);
                    uploadList.add(file);
                }
                break;
        }
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "增加产品", true);
        return topBar;
    }

    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
        String title = etName.getText().toString().trim();
        String content = et.getText().toString().trim();
        if (TextUtils.isEmpty(title)||TextUtils.isEmpty(content)||uploadList.size()==0){
            Toast.makeText(getBaseContext(),"请填写完整",Toast.LENGTH_SHORT).show();
            return;
        }
        promptDialog.showLoading("上传中");
        MediaType mediaType = MediaType.parse("image/jpeg");
        HttpManager.post(HttpManager.ADD_PRODUCT)
                .params("token", SettingManager.getInstance().getToken())
                .params("title",etName.getText().toString().trim())
                .params("content",et.getText().toString().trim())
                .addFileParams("image[]", uploadList, new ProgressResponseCallBack() {
                    @Override
                    public void onResponseProgress(long bytesWritten, long contentLength, boolean done) {
                    }
                })
                .execute(new SimpleCallBack<AddProductBean>() {
                    @Override
                    public void onError(ApiException e) {
                        promptDialog.dismiss();
                        Toast.makeText(getBaseContext(),"添加失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(AddProductBean addProductBean) {
                            promptDialog.dismiss();
                            finish();
                    }
                });
    }
}
