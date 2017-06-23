package com.kad.mrv.stagger.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Winner on 2016/5/12.
 */
public class IndexEntity implements Serializable {
    public static final int TYPE_VIEWPAGER_AD = 0;//轮播广告；
    public static final int TYPE_QUICK_BAR = 1;//快速导航
    public static final int TYPE_TEXT_NOICE = 2;//文字公告
    public static final int TYPE_DACU = 3;//大促
    public static final int TYPE_HOT_ZHUANTI_TUIJIAN = 4;//热门专题推荐
    public static final int TYPE_AD_1 = 5;//广告位1
    public static final int TYPE_HOT_KESHI = 6;//热门科室
    public static final int TYPE_AD_2 = 7;//广告位2
    public static final int TYPE_HEALTH_JINGXUAN = 8;//健康精选
    public static final int TYPE_AD_3 = 9;//广告位3
    public static final int TYPE_GRID = 10;//1、猜你喜欢
    public static final int TYPE_KESHI_TIP = 11;//
    public static final int TYPE_HEALTH_TIP = 12;
    public static final int TYPE_DACU_HEADER = 13;
    public static final int TYPE_GRID_HEAD_TIP = 14;


    private int itemType;
    private String ImageUrl;
    private String title;
    private ArrayList<TypeData> datas;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<TypeData> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<TypeData> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "IndexEntity{" +
                "itemType='" + itemType + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
