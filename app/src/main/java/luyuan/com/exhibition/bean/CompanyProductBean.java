package luyuan.com.exhibition.bean;

/**
 * @author: lujialei
 * @date: 2018/10/6
 * @describe:
 */


public class CompanyProductBean {
    /**
     * products_id : 7
     * title : app制作
     * thumb : 20180922/5ba5e7c63ebee.JPG_150x150.JPG
     * content : 原生/混合
     */

    private int products_id;
    private String title;
    private String thumb;
    private String content;
    public String summary;

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
