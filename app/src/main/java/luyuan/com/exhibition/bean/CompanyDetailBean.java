package luyuan.com.exhibition.bean;

import java.util.List;

/**
 * @author: lujialei
 * @date: 2018/10/6
 * @describe:
 */


public class CompanyDetailBean {
    /**
     * company_details : {"nickname":"张三四","headimgurl":"20180918/5ba0a0ea5ba93.jpg","describe":"上海企线","app_chat":{"hx_username":"13805697082","hx_password":"123456","status":1}}
     * banner_list : [{"banner_id":4,"image_url":"20180918/5ba0c4229ac41.jpg","exts":"jpg"}]
     */

    private CompanyDetailsBean company_details;
    private List<BannerListBean> banner_list;


    public CompanyDetailsAppChat getCompany_details_app_chat() {
        return company_details_app_chat;
    }

    public void setCompany_details_app_chat(CompanyDetailsAppChat company_details_app_chat) {
        this.company_details_app_chat = company_details_app_chat;
    }

    private CompanyDetailsAppChat company_details_app_chat;

    public CompanyDetailsBean getCompany_details() {
        return company_details;
    }

    public void setCompany_details(CompanyDetailsBean company_details) {
        this.company_details = company_details;
    }

    public List<BannerListBean> getBanner_list() {
        return banner_list;
    }

    public void setBanner_list(List<BannerListBean> banner_list) {
        this.banner_list = banner_list;
    }

    public static class AuthImageBean{
        public String image_url;
        public String exts;
    }

    public static class CompanyDetailsAppChat{
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

    public static class CompanyDetailsBean {
        /**
         * nickname : 张三四
         * headimgurl : 20180918/5ba0a0ea5ba93.jpg
         * describe : 上海企线
         * app_chat : {"hx_username":"13805697082","hx_password":"123456","status":1}
         */

        private String nickname;
        private String headimgurl;
        private String describe;
        private AppChatBean app_chat;
        public AuthImageBean auth_image;

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

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public AppChatBean getApp_chat() {
            return app_chat;
        }

        public void setApp_chat(AppChatBean app_chat) {
            this.app_chat = app_chat;
        }

        public static class AppChatBean {
            /**
             * hx_username : 13805697082
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

    public static class BannerListBean {
        /**
         * banner_id : 4
         * image_url : 20180918/5ba0c4229ac41.jpg
         * exts : jpg
         */

        private int banner_id;
        private String image_url;
        private String exts;

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
    }
}
