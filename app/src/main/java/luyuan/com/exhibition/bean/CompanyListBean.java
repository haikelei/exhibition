package luyuan.com.exhibition.bean;

/**
 * @author: lujialei
 * @date: 2018/9/30
 * @describe:
 */


public class CompanyListBean {

    /**
     * booth_id : 4
     * nickname : 张三四
     * headimgurl : 20180918/5ba0a0ea5ba93.jpg
     */

    private int booth_id;
    private String nickname;
    private String headimgurl;

    public int getBooth_id() {
        return booth_id;
    }

    public void setBooth_id(int booth_id) {
        this.booth_id = booth_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
}
