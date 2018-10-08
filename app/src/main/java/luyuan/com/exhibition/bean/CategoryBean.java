package luyuan.com.exhibition.bean;

import android.os.Parcelable;

import com.zhouyou.http.model.ApiResult;

import java.io.Serializable;
import java.util.List;

/**
 * @author: lujialei
 * @date: 2018/9/29
 * @describe:
 */


public class CategoryBean  extends ApiResult implements Serializable{

    /**
     * trade_id : 4
     * parent_id : 0
     * name : 机械设备
     * icon : null
     * children : [{"trade_id":19,"parent_id":4,"name":"食品、饮料加工设备","icon":"20180917/5b9f4ecaaef78.jpg","children":[{"trade_id":20,"parent_id":19,"name":"饼干机械","icon":""}]},{"trade_id":10,"parent_id":4,"name":"石油设备","icon":""},{"trade_id":9,"parent_id":4,"name":"塑料机械","icon":""}]
     */
    public boolean isChecked;
    private int trade_id;
    private int parent_id;
    private String name;
    private String icon;
    private List<CategoryBean> children;

    public int getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(int trade_id) {
        this.trade_id = trade_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<CategoryBean> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryBean> children) {
        this.children = children;
    }

}
