package com.yuqi.admin.py.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/8.
 * 待付款实体类
 */
public class MyPendingPaymentBean {

    /**
     * commodityPictures : ["http://120.77.242.12/image/1515379828754asdsadsa.jpg","http://120.77.242.12/image/1515379882723磁悬浮润滑油.jpg"]
     * msg : 成功！
     * counts : [1,3]
     * commodity_ids : [155,156]
     * orders : [{"order_id":479,"user_id":84,"address_id":227,"payWay":"支付宝","orderPrice":998,"orderStatus":0,"payAccount":"13412345678","createTime":"2018-01-08 15:50:13","order_number":"201801081501798a2ae7340"},{"order_id":480,"user_id":84,"address_id":227,"payWay":"支付宝","orderPrice":300,"orderStatus":0,"payAccount":"13412345678","createTime":"2018-01-08 16:09:13","order_number":"201801081601469ac3df1ab"}]
     * state : 200
     * commodityPrice : [998,100]
     * commodityName : ["现货正宗太湖清水大闸蟹 鲜活","磁悬浮润滑油 SN／GF-5 0W-30 0W-40 汽油机油"]
     */

    private String msg;
    private String state;
    private List<String> commodityPictures;
    private List<Integer> counts;
    private List<Integer> commodity_ids;
    private List<OrdersBean> orders;
    private List<Double> commodityPrice;
    private List<String> commodityName;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getCommodityPictures() {
        return commodityPictures;
    }

    public void setCommodityPictures(List<String> commodityPictures) {
        this.commodityPictures = commodityPictures;
    }

    public List<Integer> getCounts() {
        return counts;
    }

    public void setCounts(List<Integer> counts) {
        this.counts = counts;
    }

    public List<Integer> getCommodity_ids() {
        return commodity_ids;
    }

    public void setCommodity_ids(List<Integer> commodity_ids) {
        this.commodity_ids = commodity_ids;
    }

    public List<OrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public List<Double> getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(List<Double> commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public List<String> getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(List<String> commodityName) {
        this.commodityName = commodityName;
    }

    public static class OrdersBean {
        /**
         * order_id : 479
         * user_id : 84
         * address_id : 227
         * payWay : 支付宝
         * orderPrice : 998.0
         * orderStatus : 0
         * payAccount : 13412345678
         * createTime : 2018-01-08 15:50:13
         * order_number : 201801081501798a2ae7340
         */

        private int order_id;
        private int user_id;
        private int address_id;
        private String payWay;
        private double orderPrice;
        private int orderStatus;
        private String payAccount;
        private String createTime;
        private String order_number;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public String getPayWay() {
            return payWay;
        }

        public void setPayWay(String payWay) {
            this.payWay = payWay;
        }

        public double getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(double orderPrice) {
            this.orderPrice = orderPrice;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getPayAccount() {
            return payAccount;
        }

        public void setPayAccount(String payAccount) {
            this.payAccount = payAccount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }
    }
}
