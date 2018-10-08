package luyuan.com.exhibition.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CategoryBean;
import luyuan.com.exhibition.bean.CityBean;
import luyuan.com.exhibition.bean.CompanyListBean;
import luyuan.com.exhibition.bean.param.GetCompanyParam;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.ui.adapter.CompanyListAdapter;
import luyuan.com.exhibition.ui.adapter.DownRightAdapter;
import luyuan.com.exhibition.ui.widget.DownLeftRecyclerView;
import luyuan.com.exhibition.ui.widget.DownRightRecyclerView;
import luyuan.com.exhibition.ui.widget.DownSelectView;
import luyuan.com.exhibition.utils.PermissionHelper;
import luyuan.com.exhibition.utils.PermissionInterface;
import luyuan.com.exhibition.utils.ScreenUtil;

import static luyuan.com.exhibition.ui.activity.CompanyDetailActivity.BOOTH_ID;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class CompanyListActivity extends BaseActivity {

    @BindView(R.id.down_view_left)
    DownSelectView downViewLeft;
    @BindView(R.id.down_view_right)
    DownSelectView downViewRight;
    @BindView(R.id.contaier)
    RelativeLayout contaier;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    private DownLeftRecyclerView left;
    private DownRightRecyclerView right;
    public final static String CATEGORY_BEAN = "category";
    private CategoryBean categoryBean;
    private List<CityBean> cityList;
    private CompanyListAdapter mContentAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            double[] data = (double[]) msg.obj;
            List<Address> addList = null;
            Geocoder ge = new Geocoder(getApplicationContext());
            try {
                addList = ge.getFromLocation(data[0], data[1], 1);
                if (addList != null && addList.get(0) != null) {
                    String mCity = addList.get(0).getLocality();
                    if (mCity==null){
                        return;
                    }
                    if (mCity != null && cityList != null) {
                        for (int i = 0; i < cityList.size(); i++) {
                            String city = cityList.get(i).getRegion_name();
                            if (city!=null&&mCity!=null&&mCity.contains(city)) {
                                downViewLeft.setText(city);
                            }
                        }
                    }
                }
            } catch (IOException e) {

            }
        }
    };
    private LocationManager locationManager;
    private double latitude;
    private double longitude;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        ButterKnife.bind(this);
        categoryBean = (CategoryBean) getIntent().getSerializableExtra(CATEGORY_BEAN);
        initView();
        loadData();
        loadCategorys();
    }

    private List<CategoryBean> categorysList;
    private void loadCategorys() {
        HttpManager.post("Trade/getCategoryTree")
                .execute(new SimpleCallBack<List<CategoryBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<CategoryBean> categoryBeans) {
//                        寻找所有二级分类
                        ArrayList list = new ArrayList();
                        for (int i = 0; i < categoryBeans.size(); i++) {
                            List<CategoryBean> list1 = categoryBeans.get(i).getChildren();
                            if (list1!=null){
                                list.addAll(list1);
                            }

                        }
                        categorysList = list;
                    }
                });
    }

    private void loadCompany() {
        GetCompanyParam param = new GetCompanyParam();
        if (categoryBean != null) {
            param.trade_id = categoryBean.getTrade_id();
        }
        if (!TextUtils.isEmpty(downViewLeft.getCity())){
            String city = downViewLeft.getCity();
            for (int i = 0; i < cityList.size(); i++) {
                if (cityList.get(i)!=null){
                    String listCity = cityList.get(i).getRegion_name();
                    if (city!=null&& listCity!=null){
                        if (city.contains(listCity) || listCity.contains(city) ){
                            param.city_id = cityList.get(i).getCity_id();
                        }
                    }
                }
            }
        }
        HttpManager.post(HttpManager.GET_COMPANY_LIST)
                .customParams(param)
                .execute(new SimpleCallBack<List<CompanyListBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(final List<CompanyListBean> cityListBeans) {
                        mContentAdapter = new CompanyListAdapter(cityListBeans);
                        rvContent.setAdapter(mContentAdapter);
                        mContentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent = new Intent(view.getContext(),CompanyDetailActivity.class);
                                intent.putExtra(BOOTH_ID,cityListBeans.get(position).getBooth_id());
                                startActivity(intent);
                            }
                        });
                    }
                });
    }

    private void initView() {
        if (categoryBean != null) {
            downViewRight.setText(categoryBean.getName());
        }
        rvContent.setLayoutManager(new GridLayoutManager(this,4));

    }

    private void loadData() {
        HttpManager.post(HttpManager.GET_CITY_LIST)
                .execute(new SimpleCallBack<List<CityBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<CityBean> categoryBeans) {
                        downViewLeft.setText(categoryBeans.get(0).getRegion_name());
                        cityList = categoryBeans;
                        getLocation();
                        loadCompany();
                    }
                });
    }

    private PermissionHelper mPermissionHelper;

    private void getLocation() {
        mPermissionHelper = new PermissionHelper(this, new PermissionInterface() {
            @Override
            public int getPermissionsRequestCode() {
                return 10000;
            }

            @Override
            public String[] getPermissions() {
                return new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                };
            }

            @Override
            public void requestPermissionsSuccess() {
                getRealLocation();
            }

            @Override
            public void requestPermissionsFail() {

            }
        });
        mPermissionHelper.requestPermissions();

    }

    private void getRealLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        new Thread() {
            @Override
            public void run() {

                if (ActivityCompat.checkSelfPermission(CompanyListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CompanyListActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude(); // 经度
                    longitude = location.getLongitude();
                    // 纬度
                    double[] data = {latitude, longitude};
                    Message msg = handler.obtainMessage();
                    msg.obj = data;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    @OnClick({R.id.down_view_left, R.id.down_view_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.down_view_left:
                if (left == null) {
                    left = new DownLeftRecyclerView(this);
                    if (cityList != null) {
                        left.setData(cityList);
                    }
                    left.setBackgroundColor(getResources().getColor(R.color.c_f6f6f6));
                    left.setOnLeftItemClickListener(new DownLeftRecyclerView.OnLeftItemClickListener() {
                        @Override
                        public void onItemClick(CityBean bean) {
                            downViewLeft.setText(bean.getRegion_name());
                            left.setVisibility(View.GONE);
                            loadCompany();
                        }
                    });
                    Rect rect = new Rect();
                    downViewLeft.getGlobalVisibleRect(rect);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(rect.width(), 800);
                    layoutParams.leftMargin = rect.left;
                    layoutParams.topMargin = rect.bottom - ScreenUtil.getStateBarHeight(this);
                    contaier.addView(left, layoutParams);
                } else {
                    left.setVisibility(left.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
                break;
            case R.id.down_view_right:
                if (right == null) {
                    right = new DownRightRecyclerView(this);
                    right.setBackgroundColor(getResources().getColor(R.color.c_f6f6f6));
                    if (categorysList!=null){
                        right.setData(categorysList);
                    }
                    right.setOnRightItemClickListener(new DownRightRecyclerView.OnRightItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            CategoryBean bean = categorysList.get(position);
                            categoryBean = bean;
                            downViewRight.setText(bean.getName());
                            right.setVisibility(View.GONE);
                            loadCompany();
                        }
                    });
                    Rect rect2 = new Rect();
                    downViewRight.getGlobalVisibleRect(rect2);
                    RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(rect2.width(), 800);
                    layoutParams2.leftMargin = rect2.left;
                    layoutParams2.topMargin = rect2.bottom - ScreenUtil.getStateBarHeight(this);
                    contaier.addView(right, layoutParams2);
                } else {
                    right.setVisibility(right.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }

                break;
        }
    }
}
