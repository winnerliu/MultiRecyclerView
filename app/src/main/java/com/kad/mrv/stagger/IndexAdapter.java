package com.kad.mrv.stagger;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kad.mrv.R;
import com.kad.mrv.stagger.bean.IndexEntity;
import com.kad.mrv.stagger.support.CommonHolder;
import com.kad.mrv.stagger.support.MultiItemCommonAdapter;
import com.kad.mrv.stagger.support.MultiItemTypeSupport;
import com.kad.mrv.stagger.widget.CommonGridLayout;
import com.kad.mrv.util.ScreenUtil;

import java.util.List;

/**
 * Created by Winner on 2016/5/12.
 */
public class IndexAdapter extends MultiItemCommonAdapter<IndexEntity> {
    private Context mContext;

    public IndexAdapter(Context context, List<IndexEntity> datas, MultiItemTypeSupport<IndexEntity> multiItemTypeSupport) {
        super(context, datas, multiItemTypeSupport);
        this.mContext = context;
    }

    @Override
    public void convert(CommonHolder holder, IndexEntity indexEntity) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        int type = indexEntity.getItemType();
        switch (holder.getLayoutId()) {
            case R.layout.layout_index_ad:
                holder.setImageUrl(R.id.ad_view, indexEntity.getImageUrl());
                layoutParams.setFullSpan(true);
                break;

            case R.layout.layout_index_grid:
                holder.setImageUrl(R.id.gv_view, indexEntity.getImageUrl());
                layoutParams.setFullSpan(false);
                break;

            case R.layout.layout_index_text_noice:
                layoutParams.setFullSpan(true);
                break;

            case R.layout.layout_index_common:
                layoutParams.setFullSpan(true);
                selectLayoutType(holder, indexEntity, type);

                break;

            case R.layout.layout_index_viewpager:
                layoutParams.setFullSpan(true);
                break;

            case R.layout.layout_index_dacu_header:
                layoutParams.setFullSpan(true);
                break;

            case R.layout.layout_index_grid_head_tip:
                layoutParams.setFullSpan(true);
                break;

            case R.layout.layout_index_text_tip:
                layoutParams.setFullSpan(true);
                final TextView mTip = holder.getView(R.id.tv_tip);
                if(type == IndexEntity.TYPE_HEALTH_TIP){
                    mTip.setText("健康精选");

                }else if(type == IndexEntity.TYPE_KESHI_TIP){
                    mTip.setText("热门科室");
                }
                break;

        }
    }

    private void selectLayoutType(CommonHolder holder, IndexEntity indexEntity, int type) {
        if (type == IndexEntity.TYPE_DACU) {
            System.out.println("------------setupDaCu-----size()---" + indexEntity.getDatas().size());

            int height = (int) ((ScreenUtil.getW(mContext) - 2 * 5) * 0.4 + 5 + 2 * ((ScreenUtil.getW(mContext) - 4 * 5) / 3) + 2 * 5);
            setupLayout(holder, indexEntity, IndexEntity.TYPE_DACU, CommonGridLayout.TYPE_DACU, height);

        } else if (type == IndexEntity.TYPE_QUICK_BAR) {
            System.out.println("------------setupQuickBar-----size()---" + indexEntity.getDatas().size());
            int height = (ScreenUtil.getW(mContext) - 5 * 5) / 4 * 2 + 2 * 15 + 2 * 5;
            setupLayout(holder, indexEntity, IndexEntity.TYPE_QUICK_BAR, CommonGridLayout.TYPE_COMMON, height);

        } else if (type == IndexEntity.TYPE_HOT_KESHI) {
            System.out.println("------------setupHotKeShi-----size()---" + indexEntity.getDatas().size());

            int height = (int) ((ScreenUtil.getW(mContext) - 3 * 5) / 2 + 5 + (ScreenUtil.getW(mContext) - 3 * 5)/2*0.6 + 5 + (ScreenUtil.getW(mContext) - 5 * 5) / 4 * 1.5);
            setupLayout(holder, indexEntity, IndexEntity.TYPE_HOT_KESHI, CommonGridLayout.TYPE_HOT_KESHI, height);


        } else if (type == IndexEntity.TYPE_HOT_ZHUANTI_TUIJIAN) {
            System.out.println("------------setupHotTuiJian-----size()---" + indexEntity.getDatas().size());

            int height = (int) (ScreenUtil.getW(mContext) * 0.4 * 1.5);
            setupLayout(holder, indexEntity, IndexEntity.TYPE_HOT_ZHUANTI_TUIJIAN, CommonGridLayout.TYPE_HOT_ZHUANTI_TUIJIAN, height);

        } else if (type == IndexEntity.TYPE_HEALTH_JINGXUAN) {
            System.out.println("------------setupHealthJingXuan-----size()---" + indexEntity.getDatas().size());

            int height = (ScreenUtil.getW(mContext) - 3 * 5) / 4 + 5 + (ScreenUtil.getW(mContext) - 5 * 5) / 4 * 2 + 5;
            setupLayout(holder, indexEntity, IndexEntity.TYPE_HEALTH_JINGXUAN, CommonGridLayout.TYPE_HEALTH_TUIJIAN, height);
        }

    }


    private void setupLayout(CommonHolder holder, final IndexEntity indexEntity, final int type, int itemType, int height) {
        final CommonGridLayout grid = holder.getView(R.id.common_grid_view);
        FrameLayout mRoot = holder.getView(R.id.fl_common_root);
        ViewGroup.LayoutParams params = mRoot.getLayoutParams();
        params.height = height;
        mRoot.setLayoutParams(params);


        if (type == IndexEntity.TYPE_DACU) {
            mRoot.setBackgroundColor(Color.parseColor("#123456"));
        } else if (type == IndexEntity.TYPE_QUICK_BAR) {
            mRoot.setBackgroundColor(Color.parseColor("#CC0033"));
        } else if (type == IndexEntity.TYPE_HOT_ZHUANTI_TUIJIAN) {
            mRoot.setBackgroundColor(Color.parseColor("#339933"));
        } else if (type == IndexEntity.TYPE_HEALTH_JINGXUAN) {
            mRoot.setBackgroundColor(Color.parseColor("#9683E7"));
        } else if (type == IndexEntity.TYPE_HOT_KESHI) {
            mRoot.setBackgroundColor(Color.parseColor("#FBFC8F"));
        }

        grid.setType(itemType);

        if (grid.getGridAdapter() == null) {
            grid.setGridAdapter(new CommonGridLayout.GridAdatper() {

                @Override
                public View getView(int index) {
                    View view = LinearLayout.inflate(grid.getContext(), R.layout.layout_index_common_item, null);
                    SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.common_sdv_view);
                    TextView mName = (TextView) view.findViewById(R.id.tv_common_name);
                    simpleDraweeView.setImageURI(Uri.parse(indexEntity.getDatas().get(index).getImageUrl()));

                    if (type == IndexEntity.TYPE_QUICK_BAR) {
                        mName.setVisibility(View.VISIBLE);
                        mName.setText(indexEntity.getDatas().get(index).getName());
                    } else {
                        mName.setVisibility(View.GONE);
                    }


                    return view;
                }

                @Override
                public int getCount() {
                    // TODO Auto-generated method stub
                    return indexEntity.getDatas().size();
                }
            });
            grid.setOnItemClickListener(new CommonGridLayout.OnItemClickListener() {

                @Override
                public void onItemClick(View v, int index) {
                    // TODO Auto-generated method stub
                    Toast.makeText(grid.getContext(), "item=" + index + "__" + indexEntity.getItemType(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
