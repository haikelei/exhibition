package luyuan.com.exhibition.bean;

/**
 * @author: lujialei
 * @date: 2018/10/4
 * @describe:
 */


public class UserInfoBean {

    /**
     * telno : 13805697083
     * status : 1
     * profile : {"nickname":"张四","headimgurl":"20180921/5ba48738e1df7.jpg","phone":"021-63353165","email":"851109666@qq.com","address":"上海市黄浦区北京东路科技京城","city_id":21,"license_pic":"20180921/5ba487392b4dc.jpg","legal_pic":"20180921/5ba487394eb4e.jpg","complete_percent":5,"license_pic_status":1,"legal_pic_status":1}
     */

    private String telno;
    private int status;
    private ProfileBean profile;

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ProfileBean getProfile() {
        return profile;
    }

    public void setProfile(ProfileBean profile) {
        this.profile = profile;
    }

    public static class ProfileBean {
        /**
         * nickname : 张四
         * headimgurl : 20180921/5ba48738e1df7.jpg
         * phone : 021-63353165
         * email : 851109666@qq.com
         * address : 上海市黄浦区北京东路科技京城
         * city_id : 21
         * license_pic : 20180921/5ba487392b4dc.jpg
         * legal_pic : 20180921/5ba487394eb4e.jpg
         * complete_percent : 5
         * license_pic_status : 1
         * legal_pic_status : 1
         */

        private String nickname;
        private String headimgurl;
        private String phone;
        private String email;
        private String address;
        private int city_id;
        private String license_pic;
        private String legal_pic;
        private int complete_percent;
        private int license_pic_status;
        private int legal_pic_status;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public String getLicense_pic() {
            return license_pic;
        }

        public void setLicense_pic(String license_pic) {
            this.license_pic = license_pic;
        }

        public String getLegal_pic() {
            return legal_pic;
        }

        public void setLegal_pic(String legal_pic) {
            this.legal_pic = legal_pic;
        }

        public int getComplete_percent() {
            return complete_percent;
        }

        public void setComplete_percent(int complete_percent) {
            this.complete_percent = complete_percent;
        }

        public int getLicense_pic_status() {
            return license_pic_status;
        }

        public void setLicense_pic_status(int license_pic_status) {
            this.license_pic_status = license_pic_status;
        }

        public int getLegal_pic_status() {
            return legal_pic_status;
        }

        public void setLegal_pic_status(int legal_pic_status) {
            this.legal_pic_status = legal_pic_status;
        }
    }
}
