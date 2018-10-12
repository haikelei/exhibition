package luyuan.com.exhibition.bean;

import java.util.List;

/**
 * @author: lujialei
 * @date: 2018/10/12
 * @describe:
 */


public class MyPageBean {

    /**
     * describe : 上海企业在线成立于2003年 …
     * banner_list : [{"banner_id":4,"image_url":"20180918/5ba0c4229ac41.jpg","exts":"jpg","taxis":2}]
     */

    private String describe;
    private List<BannerListBean> banner_list;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<BannerListBean> getBanner_list() {
        return banner_list;
    }

    public void setBanner_list(List<BannerListBean> banner_list) {
        this.banner_list = banner_list;
    }

    public static class BannerListBean {
        /**
         * banner_id : 4
         * image_url : 20180918/5ba0c4229ac41.jpg
         * exts : jpg
         * taxis : 2
         */

        private int banner_id;
        private String image_url;
        private String exts;
        private int taxis;

        public int getBanner_id() {
            return banner_id;
        }

        public void setBanner_id(int banner_id) {
            this.banner_id = banner_id;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getExts() {
            return exts;
        }

        public void setExts(String exts) {
            this.exts = exts;
        }

        public int getTaxis() {
            return taxis;
        }

        public void setTaxis(int taxis) {
            this.taxis = taxis;
        }
    }
}
