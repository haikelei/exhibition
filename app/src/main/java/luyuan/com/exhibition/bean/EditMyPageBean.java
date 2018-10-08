package luyuan.com.exhibition.bean;

import java.util.List;

/**
 * @author: lujialei
 * @date: 2018/10/8
 * @describe:
 */


public class EditMyPageBean {
    /**
     * describe : 上海企线
     * banner_list : [{"banner_id":3,"image_url":"20180918/5ba0cf7316beb.jpg","exts":"jpg","taxis":1}]
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
         * banner_id : 3
         * image_url : 20180918/5ba0cf7316beb.jpg
         * exts : jpg
         * taxis : 1
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
