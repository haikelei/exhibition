package luyuan.com.exhibition.bean;

import java.util.List;

/**
 * @author: lujialei
 * @date: 2018/10/9
 * @describe:
 */


public class CitysBean {

    /**
     * city_id : 2
     * region_name : 北京
     * children : [{"city_id":52,"region_name":"北京"}]
     */

    private int city_id;
    private String region_name;
    private List<CitysBean> children;

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public List<CitysBean> getChildren() {
        return children;
    }

    public void setChildren(List<CitysBean> children) {
        this.children = children;
    }
}
