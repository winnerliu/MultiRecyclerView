package com.mrv.lib.view;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mrv.lib.R;
import com.nineoldandroids.animation.ValueAnimator;

public class ArrowRefreshHeader extends LinearLayout implements BaseRefreshHeader {

    private RelativeLayout mContainer;
    private SimpleDraweeView mProgressBar;
    private int mState = STATE_NORMAL;
    private String mRefreshingGif = "kadindex_refreshing.gif";

    public int mMeasuredHeight;
    private OnHideCompleteListener mListener;
    private int lastValue = 0;
    private AbstractDraweeController draweeController;

    public ArrowRefreshHeader(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public ArrowRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        // 初始情况，设置下拉刷新view高度为0
        mContainer = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.multirecyclerview_header, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);
        this.setBackgroundColor(Color.parseColor("#ffffff"));

        //init the progress view
        mProgressBar = (SimpleDraweeView) findViewById(R.id.mrv_header_progressbar);
        setProgressBarController();
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
    }

    private void setProgressBarController(){
        if (mProgressBar != null) {
            draweeController = Fresco.newDraweeControllerBuilder()
                    .setUri(Uri.parse("asset:///" + mRefreshingGif))
                    .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                    .build();
            mProgressBar.setController(draweeController);
        }
    }

    private void startProgressAnim() {
//        if (draweeController != null) {
//            Animatable animatable = draweeController.getAnimatable();
//            if (animatable != null) {
//                if (!animatable.isRunning()) {
//                    //运行中，停止
//                    animatable.start();
//                }
//            }
//        }
    }

    private void stopProgressAnim() {
//        if (draweeController != null) {
//            Animatable animatable = draweeController.getAnimatable();
//            if (animatable != null) {
//                if (animatable.isRunning()) {
//                    animatable.stop();
//                }
//            }
//        }
    }

    public void setRefreshingGifNameFormAssets(String mfullName) {
        this.mRefreshingGif = mfullName;
        setProgressBarController();
    }

    public void setState(int state) {
        if (state == mState) return;
        if (state == STATE_REFRESHING) {    // 显示进度
            smoothScrollTo(mMeasuredHeight);
            mProgressBar.setVisibility(View.VISIBLE);
            startProgressAnim();
        } else {    // 显示箭头图片
            mProgressBar.setVisibility(View.VISIBLE);
            stopProgressAnim();
        }
        mState = state;
    }

    public int getState() {
        return mState;
    }

    @Override
    public void refreshComplete() {
        setState(STATE_DONE);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                reset();
            }
        }, 100);
    }

    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        return lp.height;
    }

    @Override
    public void onMove(float delta) {
        if (getVisibleHeight() > 0 || delta > 0) {

            if (mState != STATE_REFRESHING) {
                setVisibleHeight((int) delta + getVisibleHeight());
                if (mState <= STATE_RELEASE_TO_REFRESH) { // 未处于刷新状态，更新箭头
                    if (getVisibleHeight() > mMeasuredHeight) {
                        setState(STATE_RELEASE_TO_REFRESH);
                    } else {
                        setState(STATE_NORMAL);
                    }
                }
            }
        }
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) { // not visible.
            isOnRefresh = false;
            //System.out.println("----------height == 0--------");
        }

        if (getVisibleHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height <= mMeasuredHeight) {
            //return;
        }
        int destHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mState == STATE_REFRESHING) {
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);

        return isOnRefresh;
    }

    public void reset() {
        smoothScrollTo(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                setState(STATE_NORMAL);
            }
        }, 300);
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(1).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());

                if (lastValue != (int) animation.getAnimatedValue()) {
                    lastValue = (int) animation.getAnimatedValue();
                }

                if (mListener != null && lastValue == 0) {
                    mListener.hideCompete();
                }
            }
        });
        animator.start();
    }

    public interface OnHideCompleteListener {
        void hideCompete();
    }

    public void setOnHideCompleteListener(OnHideCompleteListener mListener) {
        this.mListener = mListener;
    }
}