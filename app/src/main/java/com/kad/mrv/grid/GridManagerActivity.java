package com.kad.mrv.grid;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.kad.mrv.R;
import com.mrv.lib.view.MultiRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GridManagerActivity extends AppCompatActivity implements MultiRecyclerView.LoadingListener {

    private MultiRecyclerView mRecyclerView;

    Handler requestHandler = new Handler();
    private ArrayList<String> datas = new ArrayList<>();
    private GridAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("GridManager");
        setContentView(R.layout.activity_grid_manager);
        initView();
        setupData();
    }

    private void initView() {
        mRecyclerView = (MultiRecyclerView) findViewById(R.id.mrv_grid);
    }

    private void setupData() {
        getDatas();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new GridAdapter(this, datas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingMoreEnabled(true);
        mRecyclerView.setRefreshingGifNameFormAssets("message_content_refresh.gif");
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLoadingListener(this);
    }

    private List<String> getDatas() {
        int size = (int) (Math.random() * 80);
        int oldSize = datas.size();
        for (int i = oldSize; i < oldSize + size; i++) {
            datas.add("item" + i);
        }
        return datas;
    }

    @Override
    public void onRefresh() {
        requestHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                datas.clear();
                getDatas();
                Toast.makeText(GridManagerActivity.this, ""+datas.size(), Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                mRecyclerView.refreshComplete();
            }
        }, 2000);

    }

    @Override
    public void onLoadMore() {
        requestHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getDatas();
                Toast.makeText(GridManagerActivity.this, ""+datas.size(), Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                mRecyclerView.loadMoreComplete();
            }
        }, 2000);
    }
}
