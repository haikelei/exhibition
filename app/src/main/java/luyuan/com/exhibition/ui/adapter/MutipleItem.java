package luyuan.com.exhibition.ui.adapter;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author: lujialei
 * @date: 2018/10/7
 * @describe:
 */


public class MutipleItem implements MultiItemEntity {

    public static final int IMG = 1;
    public static final int PLUS = 2;
    private int type;

    public MutipleItem(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
