package luyuan.com.exhibition;

import java.util.List;

/**
 * @author: lujialei
 * @date: 2018/10/8
 * @describe:
 */


public class AddProductBean {
    /**
     * title : app制作
     * content : 原生/混合
     * thumbs : [{"image_url":"20180922/5ba5e7c63ebee.JPG","exts":"JPG"}]
     */

    private String title;
    private String content;
    private List<ThumbsBean> thumbs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ThumbsBean> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<ThumbsBean> thumbs) {
        this.thumbs = thumbs;
    }

    public static class ThumbsBean {
        /**
         * image_url : 20180922/5ba5e7c63ebee.JPG
         * exts : JPG
         */

        private String image_url;
        private String exts;

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
    }
}
