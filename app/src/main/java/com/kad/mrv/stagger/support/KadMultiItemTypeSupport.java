package com.kad.mrv.stagger.support;

import android.util.Log;

import com.kad.mrv.R;
import com.kad.mrv.stagger.bean.IndexEntity;

/**
 * Created by Winner on 2016/5/12.
 */
public class KadMultiItemTypeSupport implements MultiItemTypeSupport<IndexEntity> {
    @Override
    public int getLayoutId(int itemType) {
        Log.d("---itemType---", itemType + "");
        if (itemType == IndexEntity.TYPE_AD_1
                || itemType == IndexEntity.TYPE_AD_2
                || itemType == IndexEntity.TYPE_AD_3)
            return R.layout.layout_index_ad;

        else if (itemType == IndexEntity.TYPE_DACU
                || itemType == IndexEntity.TYPE_HEALTH_JINGXUAN
                || itemType == IndexEntity.TYPE_HOT_KESHI
                || itemType == IndexEntity.TYPE_HOT_ZHUANTI_TUIJIAN
                || itemType == IndexEntity.TYPE_QUICK_BAR)
            return R.layout.layout_index_common;

        else if (itemType == IndexEntity.TYPE_TEXT_NOICE) return R.layout.layout_index_text_noice;
        else if (itemType == IndexEntity.TYPE_VIEWPAGER_AD) return R.layout.layout_index_viewpager;
        else if (itemType == IndexEntity.TYPE_HEALTH_TIP) return R.layout.layout_index_text_tip;
        else if (itemType == IndexEntity.TYPE_KESHI_TIP) return R.layout.layout_index_text_tip;
        else if (itemType == IndexEntity.TYPE_DACU_HEADER) return R.layout.layout_index_dacu_header;
        else if (itemType == IndexEntity.TYPE_GRID_HEAD_TIP) return R.layout.layout_index_grid_head_tip;

        else return R.layout.layout_index_grid;
    }

    @Override
    public int getItemViewType(int position, IndexEntity indexEntity) {
        return indexEntity.getItemType();
    }
}
