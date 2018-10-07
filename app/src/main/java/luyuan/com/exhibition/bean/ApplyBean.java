package luyuan.com.exhibition.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.w3c.dom.ProcessingInstruction;

import luyuan.com.exhibition.ui.adapter.MutipleItem;

/**
 * @author: lujialei
 * @date: 2018/10/7
 * @describe:
 */


public class ApplyBean implements MultiItemEntity {
    public ApplyBean(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    @Override
    public int getItemType() {
        return type;
    }
}
