package com.yuqi.admin.py.interfaces;

/**
 * Created by zhanghongwei on 2017/12/22.
 */

public interface SubjectListener {
    void addGoods(int goodsID,int goodsnum);
    void changeGoodsNum(int goodsID, int goodsnum);
    void removeGoods(int goodsID);
}
