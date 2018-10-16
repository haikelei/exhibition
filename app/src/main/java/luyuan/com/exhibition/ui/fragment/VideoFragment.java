package luyuan.com.exhibition.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jzvd.JzvdStd;
import luyuan.com.exhibition.R;
import luyuan.com.exhibition.bean.VideoDetailBean;
import luyuan.com.exhibition.utils.Const;

/**
 * @author: lujialei
 * @date: 2018/10/16
 * @describe:
 */


public class VideoFragment extends Fragment {


    @BindView(R.id.jz_video)
    JzvdStd jzVideo;
    Unbinder unbinder;
    VideoDetailBean data;

    public static VideoFragment getInstance(VideoDetailBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", bean);
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDatas();
        initView();

    }

    public void initDatas() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            data = (VideoDetailBean) bundle.getSerializable("data");
        }
    }


    private void initView() {
        if (data == null) {
            return;
        }
        Glide.with(this)
                .load(Const.IMG_PRE + data.getPoster())
                .into(jzVideo.thumbImageView);
        jzVideo.setUp(Const.IMG_PRE+data.getVideo_src(), ""
                , JzvdStd.SCREEN_WINDOW_NORMAL);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            jzVideo.releaseAllVideos();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        jzVideo.releaseAllVideos();
    }
}
