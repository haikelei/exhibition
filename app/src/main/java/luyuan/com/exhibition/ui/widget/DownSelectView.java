package luyuan.com.exhibition.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import luyuan.com.exhibition.R;


/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class DownSelectView extends RelativeLayout {
    @BindView(R.id.tv)
    TextView tv;

    public DownSelectView(Context context) {
        this(context, null);
    }

    public DownSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_down_select_view, this, true);
        ButterKnife.bind(this);
    }

    public void setText(String s){
        tv.setText(s);
    }
    public String getCity(){
        return tv.getText().toString();
    }
}
