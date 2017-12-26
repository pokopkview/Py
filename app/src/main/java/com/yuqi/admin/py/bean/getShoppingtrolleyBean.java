package com.yuqi.admin.py.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */
public class getShoppingtrolleyBean {

    /**
     * object : {"手机":[{"id":6,"commodity_id":4,"typeName":"手机","commodityName":"好机友手机病健康贴眼贴专克眼睛干涩、酸痛、红血丝","commodityNumber":1,"commodityPrice":10,"picture":"http://120.77.242.12/image/yantie.jpg"},{"id":12,"commodity_id":4,"typeName":"手机","commodityName":"好机友手机病健康贴眼贴专克眼睛干涩、酸痛、红血丝","commodityNumber":1,"commodityPrice":10,"picture":"http://120.77.242.12/image/yantie.jpg"},{"id":13,"commodity_id":8,"typeName":"手机","commodityName":"磁悬浮润滑油：磁悬浮润滑油SN/GF-50W 0W-40汽车机油","commodityNumber":1,"commodityPrice":0.04,"picture":"http://120.77.242.12/image/cixuanfurunhuayou.jpg"},{"id":14,"commodity_id":4,"typeName":"手机","commodityName":"好机友手机病健康贴眼贴专克眼睛干涩、酸痛、红血丝","commodityNumber":1,"commodityPrice":10,"picture":"http://120.77.242.12/image/yantie.jpg"}]}
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
        private List<手机Bean> 手机;

        public List<手机Bean> get手机() {
            return 手机;
        }

        public void set手机(List<手机Bean> 手机) {
            this.手机 = 手机;
        }

        public static class 手机Bean {
            /**
             * id : 6
             * commodity_id : 4
             * typeName : 手机
             * commodityName : 好机友手机病健康贴眼贴专克眼睛干涩、酸痛、红血丝
             * commodityNumber : 1
             * commodityPrice : 10.0
             * picture : http://120.77.242.12/image/yantie.jpg
             */

            private int id;
            private int commodity_id;
            private String typeName;
            private String commodityName;
            private int commodityNumber;
            private double commodityPrice;
            private String picture;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getCommodity_id() {
                return commodity_id;
            }

            public void setCommodity_id(int commodity_id) {
                this.commodity_id = commodity_id;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getCommodityName() {
                return commodityName;
            }

            public void setCommodityName(String commodityName) {
                this.commodityName = commodityName;
            }

            public int getCommodityNumber() {
                return commodityNumber;
            }

            public void setCommodityNumber(int commodityNumber) {
                this.commodityNumber = commodityNumber;
            }

            public double getCommodityPrice() {
                return commodityPrice;
            }

            public void setCommodityPrice(double commodityPrice) {
                this.commodityPrice = commodityPrice;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }
    }
}
