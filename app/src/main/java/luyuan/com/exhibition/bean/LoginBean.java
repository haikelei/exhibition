package luyuan.com.exhibition.bean;

/**
 * @author: lujialei
 * @date: 2018/10/4
 * @describe:
 */


public class LoginBean {

    /**
     * telno : 13805697083
     * status : 0
     * profile : {"nickname":null,"headimgurl":null,"complete_percent":0}
     * app_chat : {"hx_username":"13805697083","hx_password":"123456","status":1}
     * token : BF192F5B-18A9-AD11-2BC4-769135D62EE7
     */

    private String telno;
    private int status;
    private ProfileBean profile;
    private AppChatBean app_chat;
    private String token;

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

    public AppChatBean getApp_chat() {
        return app_chat;
    }

    public void setApp_chat(AppChatBean app_chat) {
        this.app_chat = app_chat;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class ProfileBean {
        /**
         * nickname : null
         * headimgurl : null
         * complete_percent : 0
         */

        private String nickname;
        private String headimgurl;
        private int complete_percent;

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

        public int getComplete_percent() {
            return complete_percent;
        }

        public void setComplete_percent(int complete_percent) {
            this.complete_percent = complete_percent;
        }
    }

    public static class AppChatBean {
        /**
         * hx_username : 13805697083
         * hx_password : 123456
         * status : 1
         */

        private String hx_username;
        private String hx_password;
        private int status;

        public String getHx_username() {
            return hx_username;
        }

        public void setHx_username(String hx_username) {
            this.hx_username = hx_username;
        }

        public String getHx_password() {
            return hx_password;
        }

        public void setHx_password(String hx_password) {
            this.hx_password = hx_password;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
