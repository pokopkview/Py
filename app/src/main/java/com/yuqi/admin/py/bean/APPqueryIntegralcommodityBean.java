package com.yuqi.admin.py.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 *
 * 积分商城
 *
 */
public class APPqueryIntegralcommodityBean {


    /**
     * object : [{"id":1,"commodityName":"基础网站建设","integralPrice":3000,"introduce":"专业为企业建设开发基础网站--例如官网、门户等","pictureURL":"http://120.77.242.12/image/yantie.jpg"},{"id":2,"commodityName":"微信公众号开发","integralPrice":5000,"introduce":"专业为企业开发微信公众号，只有你想不到到没有做不到的！","pictureURL":"http://120.77.242.12/image/cixuanfurunhuayou.jpg"}]
     * state : 200
     */

    private String state;
    private List<ObjectBean> object;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ObjectBean> getObject() {
        return object;
    }

    public void setObject(List<ObjectBean> object) {
        this.object = object;
    }

    public static class ObjectBean {
        /**
         * id : 1
         * commodityName : 基础网站建设
         * integralPrice : 3000
         * introduce : 专业为企业建设开发基础网站--例如官网、门户等
         * pictureURL : http://120.77.242.12/image/yantie.jpg
         */

        private int id;
        private String commodityName;
        private int integralPrice;
        private String introduce;
        private String pictureURL;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public int getIntegralPrice() {
            return integralPrice;
        }

        public void setIntegralPrice(int integralPrice) {
            this.integralPrice = integralPrice;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getPictureURL() {
            return pictureURL;
        }

        public void setPictureURL(String pictureURL) {
            this.pictureURL = pictureURL;
        }
    }
}
