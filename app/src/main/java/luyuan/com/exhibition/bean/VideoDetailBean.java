package luyuan.com.exhibition.bean;

import java.io.Serializable;

/**
 * @author: lujialei
 * @date: 2018/10/16
 * @describe:
 */


public class VideoDetailBean implements Serializable {

    /**
     * video_id : 5
     * title : 公司宣传片
     * poster : 20181016/5bc5583bce5d7.png
     * video_src : 20181016/5bc5583bd22e0.mp4
     */

    private int video_id;
    private String title;
    private String poster;
    private String video_src;

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getVideo_src() {
        return video_src;
    }

    public void setVideo_src(String video_src) {
        this.video_src = video_src;
    }
}
