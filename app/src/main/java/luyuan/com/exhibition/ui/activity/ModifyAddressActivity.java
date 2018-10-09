package luyuan.com.exhibition.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.CitysBean;
import luyuan.com.exhibition.bean.UserInfoBean;
import luyuan.com.exhibition.net.HttpManager;
import luyuan.com.exhibition.parse.AddressPickTask;
import luyuan.com.exhibition.utils.SettingManager;

/**
 * @author: lujialei
 * @date: 2018/10/3
 * @describe:我的主页
 */


public class ModifyAddressActivity extends BaseActivity {

    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private ArrayList<Province> data = new ArrayList<>();
    private String selectedCounty="";
    private String selectedCity="";
    private String selectedProvince="";
    private AddressPicker picker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_modify_nickname);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String s = getIntent().getStringExtra("value");
        if (!TextUtils.isEmpty(s)) {
            et.setText(s);
        }
        loadData();
    }

    private void loadData() {
        HttpManager.post(HttpManager.GET_PROVINCES)
                .execute(new SimpleCallBack<List<CitysBean>>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(List<CitysBean> list) {
                        for (int i = 0; i < list.size(); i++) {
                            CitysBean citysBean = list.get(i);
                            Province province = new Province(String.valueOf(citysBean.getCity_id()),citysBean.getRegion_name());
                            data.add(province);
                            ArrayList<City> cityList = new ArrayList();
                            for (int j = 0; j < citysBean.getChildren().size(); j++) {
                                CitysBean city = citysBean.getChildren().get(j);
                                City city1 = new City(String.valueOf(city.getCity_id()),city.getRegion_name());
                                cityList.add(city1);
                                province.setCities(cityList);
                                ArrayList<County> countyList = new ArrayList();
                                if (city.getChildren()!=null){
                                    for (int k = 0; k < city.getChildren().size(); k++) {
                                        CitysBean bean3 = city.getChildren().get(k);
                                        County county = new County(String.valueOf(bean3.getCity_id()),bean3.getRegion_name());
                                        countyList.add(county);
                                        city1.setCounties(countyList);
                                    }
                                }


                            }
                        }
                        showPicker();
                    }
                });
    }

    public void showPicker() {
        picker = new AddressPicker(this, data);
        picker.setHideProvince(false);
        picker.setHideCounty(false);
        if (false) {
            picker.setColumnWeight(1 / 3.0f, 2 / 3.0f);//将屏幕分为3份，省级和地级的比例为1:2
        } else {
            picker.setColumnWeight(2 / 8.0f, 3 / 8.0f, 3 / 8.0f);//省级、地级和县级的比例为2:3:3
        }
        if (picker.getSelectedProvince()!=null){
            selectedProvince = picker.getSelectedProvince().getAreaName();
        }

        if (picker.getSelectedCity()!=null){
            selectedCity = picker.getSelectedCity().getAreaName();
        }

        if (picker.getSelectedCounty()!=null){
            selectedCounty = picker.getSelectedCounty().getAreaName();
        }

        picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);
        picker.setOnAddressPickListener(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {

            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                StringBuilder stringBuilder = new StringBuilder();
                if (province!=null){
                    stringBuilder.append(province.getAreaName()+"省");
                }
                if (city!=null){
                    stringBuilder.append(city.getAreaName()+"市");
                }
                if (county!=null){
                    stringBuilder.append(county.getAreaName()+"县");
                }
                tvTitle.setText(stringBuilder);
            }
        });
        picker.show();
    }


    @OnClick({R.id.tv_confirm,R.id.tv_title})
    public void onViewClicked(View view) {
        if (view.getId()==R.id.tv_confirm){
            HttpManager.post(HttpManager.EDIT_USERINFO)
                    .params("token", SettingManager.getInstance().getToken())
                    .params("address", tvTitle.getText().toString().trim()+et.getText().toString().trim())
                    .execute(new SimpleCallBack<UserInfoBean>() {
                        @Override
                        public void onError(ApiException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onSuccess(UserInfoBean userInfoBean) {
                            finish();
                        }
                    });
        }else if(view.getId()==R.id.tv_title){
            if (picker!=null&&data.size()>0){
                picker.show();
            }
        }

    }
}
