package com.kad.mrv.linear;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kad.mrv.R;
import com.kad.mrv.util.Content;
import com.mrv.lib.a.MultiRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LinearManagerActivity extends AppCompatActivity implements MultiRecyclerView.LoadingListener {

    private MultiRecyclerView mRecyclerView;

    Handler requestHandler = new Handler();
    private ArrayList<String> datas = new ArrayList<>();
    private LinearAdapter mAdapter;
    private View mEmptyView;
    private RelativeLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("LinearManager");
        setContentView(R.layout.activity_linear_manager);
        initView();
        setupData();
    }

    private void initView() {
        mRecyclerView = (MultiRecyclerView) findViewById(R.id.mrv_linear);
        mRootView = (RelativeLayout) findViewById(R.id.activity_linear_manager);
        //mEmptyView = findViewById(R.id.text_empty);
    }

    private void setupData() {
       // getDatas();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//        View mEmptyView = LayoutInflater.from(this).inflate(R.layout.layout_emptyview, mRootView);
//        mRecyclerView.setEmptyView(mEmptyView);
        mRecyclerView.setRefreshingGifNameFormAssets("kadindex_refreshing.gif");
        mAdapter = new LinearAdapter(this, datas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLoadingMoreEnabled(true);
        //设置为垂直布局，这也是默认的
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.refresh();
    }

    private List<String> getDatas() {
        int size = (int) (Math.random() * 50);
        size = size == 0 ? 15 : size;
        int oldSize = datas.size();
        int contentUrlLength = Content.imageUrlList.length;
        for (int i = oldSize; i < oldSize + size; i++) {
            datas.add(Content.imageUrlList[i % contentUrlLength]);
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
                Toast.makeText(LinearManagerActivity.this, "" + datas.size(), Toast.LENGTH_SHORT).show();
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
                if(datas.size()>60){
                    mRecyclerView.setNoMore(true);
                }else {
                    getDatas();
                    Toast.makeText(LinearManagerActivity.this, "" + datas.size(), Toast.LENGTH_SHORT).show();
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.loadMoreComplete();
                }
            }
        }, 2000);
    }
}
