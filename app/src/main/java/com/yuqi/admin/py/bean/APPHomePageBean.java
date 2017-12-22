package com.yuqi.admin.py.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */
public class APPHomePageBean {

    /**
     * object : {"commodities":[{"id":4,"commodityType_id":1,"commodityName":"好机友手机病健康贴眼贴专克眼睛干涩、酸痛、红血丝","commodityPrice":50,"express":0,"sales":0,"picture":"http://120.77.242.12/image/眼贴.jpg"},{"id":5,"commodityType_id":1,"commodityName":"中科特膳：［1盒装］中科特膳美膳美姿纤美营养素 135g／盒","commodityPrice":198,"express":0,"sales":0,"picture":"http://120.77.242.12/image/中科特膳-1盒.jpg"},{"id":6,"commodityType_id":1,"commodityName":"中科特膳：［5盒装］中科特膳美膳美姿纤美营养素 135g／盒","commodityPrice":780,"express":0,"sales":0,"picture":"http://120.77.242.12/image/中科特膳-5盒.jpg"},{"id":7,"commodityType_id":1,"commodityName":"太湖清水大闸蟹：现货正宗太湖清水大闸蟹 鲜活","commodityPrice":998,"express":0,"sales":0,"picture":"http://120.77.242.12/image/太湖清水大闸蟹.jpg"},{"id":8,"commodityType_id":1,"commodityName":"磁悬浮润滑油：磁悬浮润滑油SN/GF-50W 0W-40汽车机油","commodityPrice":100,"express":0,"sales":0,"picture":"http://120.77.242.12/image/磁悬浮润滑油.jpg"}],"pictures":[{"id":1,"picture_url":"http://120.77.242.12/image/pyjh1.jpg","first":1},{"id":2,"picture_url":"http://120.77.242.12/image/pyjh2.jpg","first":2},{"id":3,"picture_url":"http://120.77.242.12/image/pyjh3.jpg","first":3}],"demands":[{"id":1,"name":"彭先生","gender":1,"phoneNumber":"13587642062","demandContext":"我现在需要购买一大批呼伦贝尔香烟","auditStatus":0,"accomplishStatus":0,"createTime":"2017-12-19"}]}
     * state : 200
     */

    private ObjectBean object;
    private String state;

    public ObjectBean getObject() {
        return object;
    }

    public void setObject(ObjectBean object) {
        this.object = object;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class ObjectBean {
        private List<CommoditiesBean> commodities;
        private List<PicturesBean> pictures;
        private List<DemandsBean> demands;

        public List<CommoditiesBean> getCommodities() {
            return commodities;
        }

        public void setCommodities(List<CommoditiesBean> commodities) {
            this.commodities = commodities;
        }

        public List<PicturesBean> getPictures() {
            return pictures;
        }

        public void setPictures(List<PicturesBean> pictures) {
            this.pictures = pictures;
        }

        public List<DemandsBean> getDemands() {
            return demands;
        }

        public void setDemands(List<DemandsBean> demands) {
            this.demands = demands;
        }

        public static class CommoditiesBean {
            /**
             * id : 4
             * commodityType_id : 1
             * commodityName : 好机友手机病健康贴眼贴专克眼睛干涩、酸痛、红血丝
             * commodityPrice : 50.0
             * express : 0.0
             * sales : 0
             * picture : http://120.77.242.12/image/眼贴.jpg
             */

            private int id;
            private int commodityType_id;
            private String commodityName;
            private double commodityPrice;
            private double express;
            private int sales;
            private String picture;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCommodityType_id() {
                return commodityType_id;
            }

            public void setCommodityType_id(int commodityType_id) {
                this.commodityType_id = commodityType_id;
            }

            public String getCommodityName() {
                return commodityName;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public double getCommodityPrice() {
                return commodityPrice;
            }

            public void setCommodityPrice(double commodityPrice) {
                this.commodityPrice = commodityPrice;
            }

            public double getExpress() {
                return express;
            }

            public void setExpress(double express) {
                this.express = express;
            }

            public int getSales() {
                return sales;
            }

            public void setSales(int sales) {
                this.sales = sales;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }

        public static class PicturesBean {
            /**
             * id : 1
             * picture_url : http://120.77.242.12/image/pyjh1.jpg
             * first : 1
             */

            private int id;
            private String picture_url;
            private int first;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPicture_url() {
                return picture_url;
            }

            public void setPicture_url(String picture_url) {
                this.picture_url = picture_url;
            }

            public int getFirst() {
                return first;
            }

            public void setFirst(int first) {
                this.first = first;
            }
        }

        public static class DemandsBean {
            /**
             * id : 1
             * name : 彭先生
             * gender : 1
             * phoneNumber : 13587642062
             * demandContext : 我现在需要购买一大批呼伦贝尔香烟
             * auditStatus : 0
             * accomplishStatus : 0
             * createTime : 2017-12-19
             */

            private int id;
            private String name;
            private int gender;
            private String phoneNumber;
            private String demandContext;
            private int auditStatus;
            private int accomplishStatus;
            private String createTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getDemandContext() {
                return demandContext;
            }

            public void setDemandContext(String demandContext) {
                this.demandContext = demandContext;
            }

            public int getAuditStatus() {
                return auditStatus;
            }

            public void setAuditStatus(int auditStatus) {
                this.auditStatus = auditStatus;
            }

            public int getAccomplishStatus() {
                return accomplishStatus;
            }

            public void setAccomplishStatus(int accomplishStatus) {
                this.accomplishStatus = accomplishStatus;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }
        }
    }
}
