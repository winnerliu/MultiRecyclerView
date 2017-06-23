package com.kad.mrv.stagger;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;


import com.kad.mrv.R;
import com.kad.mrv.stagger.bean.IndexEntity;
import com.kad.mrv.stagger.bean.TypeData;
import com.kad.mrv.stagger.support.KadMultiItemTypeSupport;
import com.kad.mrv.util.Content;
import com.mrv.lib.view.MultiRecyclerView;

import java.util.ArrayList;

/**
 * Created by Winner on 2016/5/9.
 */
public class CommonIndexActvity extends Activity {
    private MultiRecyclerView mRecyclerView;
    private IndexAdapter mAdapter;
    private ArrayList<IndexEntity> listData = new ArrayList<IndexEntity>();
    private int refreshTime = 0;
    private int times = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_index);
        mRecyclerView = (MultiRecyclerView) this.findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

//        View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header, (ViewGroup) findViewById(android.R.id.content), false);
//        mRecyclerView.addHeaderView(header);

        mRecyclerView.setLoadingListener(new MultiRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshTime++;
                times = 0;
                mRecyclerView.reset();
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        listData.clear();
                        generateFirstData();
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                if (times < 4) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mRecyclerView.loadMoreComplete();
                            generateData();
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.refreshComplete();
                        }
                    }, 1000);
                } else {
                    mRecyclerView.setNoMore(true);
                    mRecyclerView.setminFooterHeight(10);
//                    new Handler().postDelayed(new Runnable() {
//                        public void run() {
//
//                            mAdapter.notifyDataSetChanged();
//                            mRecyclerView.loadMoreComplete();
//                        }
//                    }, 1000);
                }
                times++;
            }
        });


        mAdapter = new IndexAdapter(this, listData, new KadMultiItemTypeSupport());
        generateFirstData();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void generateData() {
        for (int k = 0; k < 20; k++) {
            IndexEntity gridEntity = new IndexEntity();
            gridEntity.setImageUrl(Content.imageUrlList[k]);
            gridEntity.setItemType(IndexEntity.TYPE_GRID);

            ArrayList<TypeData> list = new ArrayList<>();

            TypeData typeData = new TypeData();
            typeData.setImageUrl(Content.imageUrlList[k]);
            typeData.setName("数据---" + k);
            list.add(typeData);

            gridEntity.setDatas(list);

            listData.add(gridEntity);
        }
    }

    private void generateFirstData() {
        for (int i = 0; i < 15; i++) {
            IndexEntity entity = new IndexEntity();
            entity.setImageUrl(Content.imageUrlList[i]);

            if (i == 0) {
                entity.setItemType(IndexEntity.TYPE_VIEWPAGER_AD);
                listData.add(entity);

            } else if (i == 1) {
                entity.setItemType(IndexEntity.TYPE_QUICK_BAR);
                ArrayList<TypeData> list = new ArrayList<>();
                for (int k = 0; k < 8; k++) {
                    TypeData typeData = new TypeData();
                    typeData.setImageUrl(Content.imageUrlList[k]);
                    typeData.setName(Content.quick_bars[k]);
                    list.add(typeData);
                }
                entity.setDatas(list);
                listData.add(entity);

            } else if (i == 2) {
                entity.setItemType(IndexEntity.TYPE_TEXT_NOICE);
                listData.add(entity);

            } else if (i == 3) {
                entity.setItemType(IndexEntity.TYPE_DACU_HEADER);
                listData.add(entity);

            } else if (i == 4) {
                entity.setItemType(IndexEntity.TYPE_DACU);
                ArrayList<TypeData> list = new ArrayList<>();
                for (int k = 0; k < 7; k++) {
                    TypeData typeData = new TypeData();
                    typeData.setImageUrl(Content.imageUrlList[k + 8]);
                    // typeData.setName(Content.quick_bars[k]);
                    list.add(typeData);
                }
                entity.setDatas(list);
                listData.add(entity);

            } else if (i == 5) {
                entity.setItemType(IndexEntity.TYPE_HOT_ZHUANTI_TUIJIAN);

                ArrayList<TypeData> list = new ArrayList<>();
                for (int k = 0; k < 3; k++) {
                    TypeData typeData = new TypeData();
                    typeData.setImageUrl(Content.imageUrlList[k + 15]);
                    // typeData.setName(Content.quick_bars[k]);
                    list.add(typeData);
                }
                entity.setDatas(list);
                listData.add(entity);

            } else if (i == 6) {
                entity.setItemType(IndexEntity.TYPE_AD_1);
                listData.add(entity);

            } else if (i == 7) {
                entity.setItemType(IndexEntity.TYPE_KESHI_TIP);
                listData.add(entity);

            } else if (i == 8) {
                entity.setItemType(IndexEntity.TYPE_HOT_KESHI);
                ArrayList<TypeData> list = new ArrayList<>();
                for (int k = 0; k < 8; k++) {
                    TypeData typeData = new TypeData();
                    typeData.setImageUrl(Content.imageUrlList[k + 18]);
                    // typeData.setName(Content.quick_bars[k]);
                    list.add(typeData);
                }
                entity.setDatas(list);
                listData.add(entity);

            } else if (i == 9) {
                entity.setItemType(IndexEntity.TYPE_AD_2);
                listData.add(entity);

            } else if (i == 10) {
                entity.setItemType(IndexEntity.TYPE_HEALTH_TIP);
                listData.add(entity);

            } else if (i == 11) {
                entity.setItemType(IndexEntity.TYPE_HEALTH_JINGXUAN);
                ArrayList<TypeData> list = new ArrayList<>();
                for (int k = 0; k < 10; k++) {
                    TypeData typeData = new TypeData();
                    typeData.setImageUrl(Content.imageUrlList[k + 28]);
                    // typeData.setName(Content.quick_bars[k]);
                    list.add(typeData);
                }
                entity.setDatas(list);
                listData.add(entity);

            } else if (i == 12) {
                entity.setItemType(IndexEntity.TYPE_AD_3);
                listData.add(entity);

            } else if (i == 13) {
                entity.setItemType(IndexEntity.TYPE_GRID_HEAD_TIP);
                listData.add(entity);

            } else {
                for (int k = 0; k < 20; k++) {
                    IndexEntity gridEntity = new IndexEntity();
                    gridEntity.setImageUrl(Content.imageUrlList[k]);
                    gridEntity.setItemType(IndexEntity.TYPE_GRID);

                    ArrayList<TypeData> list = new ArrayList<>();

                    TypeData typeData = new TypeData();
                    typeData.setImageUrl(Content.imageUrlList[k]);
                    typeData.setName("数据---" + k);
                    list.add(typeData);

                    gridEntity.setDatas(list);

                    listData.add(gridEntity);
                }

            }


        }
    }
}
