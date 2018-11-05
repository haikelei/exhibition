package luyuan.com.exhibition.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.easeui.EaseConstant;
import com.yescpu.keyboardchangelib.KeyboardChangeListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CompanyDetailBean;
import luyuan.com.exhibition.bean.CompanyProductBean;
import luyuan.com.exhibition.bean.VideoDetailBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.adapter.VideoPagerAdapter;
import luyuan.com.exhibition.ui.fragment.CustomChatWrapperFragment;
import luyuan.com.exhibition.ui.fragment.VideoFragment;
import luyuan.com.exhibition.ui.interfaces.GlideImageLoader;
import luyuan.com.exhibition.ui.widget.DefaultTopBar;
import luyuan.com.exhibition.utils.Const;
import luyuan.com.exhibition.utils.ScreenUtil;

import static luyuan.com.exhibition.ui.activity.ChatActivity.CHAT_PASSWORD;
import static luyuan.com.exhibition.ui.activity.ChatActivity.CHAT_STATUS;
import static luyuan.com.exhibition.ui.activity.ChatActivity.CHAT_USER_NAME;


/**
 * @author: lujialei
 * @date: 2018/9/30
 * @describe:
 */


public class CompanyDetailActivity extends BaseActivity {


    public static final String BOOTH_ID = "booth_id";

    @BindView(R.id.rl_bottom_container)
    LinearLayout rlBottomContainer;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.iv_renzheng)
    ImageView ivRenzheng;
    @BindView(R.id.banner_canzhan)
    Banner bannerCanzhan;
    @BindView(R.id.banner_products)
    Banner bannerProducts;
    @BindView(R.id.vp_video)
    ViewPager vp;
    VideoPagerAdapter mVideoPagerAdapter;
    @BindView(R.id.rl2)
    LinearLayout rl2;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.ll_com_video)
    LinearLayout llComVideo;

    private int boothId;
    private CompanyDetailBean.CompanyDetailsBean.AppChatBean chatBean;
    private int screenHeight;
    private int smallHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);
        ButterKnife.bind(this);
        boothId = getIntent().getIntExtra(BOOTH_ID, 0);
        initView();
        initListener();
        loadData();
    }

    private void initListener() {
        bannerProducts.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getBaseContext(), ProductListActivity.class);
                intent.putExtra(BOOTH_ID, boothId);
                startActivity(intent);
            }
        });
        llComVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),VideoListActivity.class);
                intent.putExtra(BOOTH_ID, boothId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean isShowTopBar() {
        return true;
    }

    @Override
    protected View onCreateTopBar(ViewGroup view) {
        DefaultTopBar topBar = new DefaultTopBar(this, "企业详情", true);
        topBar.setOnTopBarClickListener(new DefaultTopBar.OnTopBarClickListener() {
            @Override
            public void onChatClick(View v) {
                if (chatBean != null) {
                    Intent intent = new Intent(getBaseContext(), ChatActivity.class);
                    intent.putExtra(CHAT_PASSWORD, chatBean.getHx_password());
                    intent.putExtra(CHAT_USER_NAME, chatBean.getHx_username());
                    intent.putExtra(CHAT_STATUS, chatBean.getStatus());
                    startActivity(intent);
                }
            }
        });
        return topBar;
    }

    private void loadData() {

        HttpManager.post(HttpManager.COMPANY_DETAIL)
                .params("booth_id", String.valueOf(boothId))
                .execute(new SimpleCallBack<CompanyDetailBean>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(CompanyDetailBean companyDetailBean) {
                        chatBean = companyDetailBean.getCompany_details().getApp_chat();
                        handleCompanyDetail(companyDetailBean);
                        initChatFragment(companyDetailBean);
                    }
                });
        HttpManager.post(HttpManager.COMPANY_PRODUCTS)
                .params("booth_id", String.valueOf(boothId))
                .execute(new SimpleCallBack<List<CompanyProductBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final List<CompanyProductBean> companyProductBeans) {
                        ArrayList list = new ArrayList();
                        for (int i = 0; i < companyProductBeans.size(); i++) {
                            list.add(companyProductBeans.get(i).getThumb());
                        }
                        bannerProducts.setImages(list).setImageLoader(new ImageLoader() {
                            @Override
                            public void displayImage(Context context, Object path, ImageView imageView) {
                                Glide.with(context)
                                        .load(Const.IMG_PRE + path)
                                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                                        .error(R.mipmap.no_image)
                                        .centerCrop()
                                        .placeholder(R.mipmap.no_image)
                                        .into(imageView);
                            }
                        }).setDelayTime(5000).start();
                    }
                });
        HttpManager.post(HttpManager.GET_COM_VIDEO)
                .params("booth_id", String.valueOf(boothId))
                .execute(new SimpleCallBack<List<VideoDetailBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<VideoDetailBean> list) {
                        ArrayList tmpList = new ArrayList();
                        for (int i = 0; i < list.size(); i++) {
                            VideoFragment videoFragment = VideoFragment.getInstance(list.get(i));
                            tmpList.add(videoFragment);
                        }
                        mVideoPagerAdapter = new VideoPagerAdapter(getSupportFragmentManager(), tmpList);
                        vp.setOffscreenPageLimit(0);
                        vp.setAdapter(mVideoPagerAdapter);
                    }
                });
    }

    private void handleCompanyDetail(CompanyDetailBean companyDetailBean) {
        tvNickname.setText(companyDetailBean.getCompany_details().getApp_chat().getHx_username());
        Glide.with(this)
                .load(Const.IMG_PRE + companyDetailBean.getCompany_details().getHeadimgurl())
                .into(ivLogo);
        tvDes.setText(companyDetailBean.getCompany_details().getDescribe());
        Glide.with(this)
                .load(Const.IMG_PRE + companyDetailBean.getCompany_details().auth_image.image_url)
                .centerCrop()
                .into(ivRenzheng);
        ArrayList list = getBannerList(companyDetailBean);
        bannerCanzhan.setImages(list).setImageLoader(new GlideImageLoader()).setDelayTime(5000).start();
    }

    private ArrayList getBannerList(CompanyDetailBean companyDetailBean) {
        ArrayList list = new ArrayList();
        for (int i = 0; i < companyDetailBean.getBanner_list().size(); i++) {
            list.add(companyDetailBean.getBanner_list().get(i).getImage_url());
        }
        return list;
    }

    private void initChatFragment(CompanyDetailBean companyDetailBean) {
        CustomChatWrapperFragment chatFragment = new CustomChatWrapperFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, companyDetailBean.getCompany_details().getApp_chat().getHx_username());
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.rl_container, chatFragment).commit();
    }

    public void changeHeight(int Height) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlBottomContainer.getLayoutParams();
        params.height = Height;
        rlBottomContainer.setLayoutParams(params);
    }

    private void initView() {

        rl2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                rl2.getGlobalVisibleRect(rect);
                Log.e("lujialei", "(" + rect.left + "," + rect.bottom + ")");
                screenHeight = ScreenUtil.getScreenHeight(getBaseContext());
                smallHeight = screenHeight - rect.bottom;
                changeHeight(smallHeight);
                rl2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        new KeyboardChangeListener(this).setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                if (isShow) {
                    tvNickname.setVisibility(View.VISIBLE);
                    int height = screenHeight - keyboardHeight - 100;
                    changeHeight(height);
                } else {
                    tvNickname.setVisibility(View.GONE);
                    Rect rect = new Rect();
                    rl2.getGlobalVisibleRect(rect);
                    int height = screenHeight - rect.bottom;
                    changeHeight(height);
                }
            }
        });

    }

}
