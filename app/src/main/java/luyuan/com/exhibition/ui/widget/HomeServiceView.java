package luyuan.com.exhibition.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import luyuan.com.exhibition.R;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class HomeServiceView extends RelativeLayout {
    public HomeServiceView(Context context) {
        this(context,null);
    }

    public HomeServiceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HomeServiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.layout_home_service,this,true);
    }
}
