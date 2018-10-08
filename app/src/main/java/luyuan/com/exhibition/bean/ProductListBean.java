package luyuan.com.exhibition.bean;

/**
 * @author: lujialei
 * @date: 2018/10/8
 * @describe:
 */


public class ProductListBean {
    /**
     * products_id : 5
     * title : 微信小程序开发
     * thumb : 20180918/5ba0aeabd2d24.jpg_150x150.jpg
     * content : 十年开发，百折不挠
     */

    private int products_id;
    private String title;
    private String thumb;
    private String content;

    public int getProducts_id() {
        return products_id;
    }

    public void setProducts_id(int products_id) {
        this.products_id = products_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
