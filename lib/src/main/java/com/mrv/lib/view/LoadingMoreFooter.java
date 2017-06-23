package com.mrv.lib.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mrv.lib.R;

public class LoadingMoreFooter extends LinearLayout {

    private View mFootView;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    private TextView mText;
    private String loadingHint;
    private String noMoreHint;
    private String loadingDoneHint;
    private ProgressBar mProgressBar;

    public LoadingMoreFooter(Context context) {
		super(context);
		initView();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public LoadingMoreFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

    public void setLoadingHint(String hint) {
        loadingHint = hint;
    }

    public void setNoMoreHint(String hint) {
        noMoreHint = hint;
    }

    public void setLoadingDoneHint(String hint) {
        loadingDoneHint = hint;
    }

    public void initView(){
        setGravity(Gravity.CENTER);
        setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mFootView = LayoutInflater.from(getContext()).inflate(R.layout.multirecyclerview_footer, null, false);
        addView(mFootView);
        mText = (TextView) mFootView.findViewById(R.id.mrv_foot_more);
        mText.setText(R.string.multirecyclerview_loading);
        mProgressBar = (ProgressBar)mFootView.findViewById(R.id.mrv_foot_progress);

        loadingHint = (String)getContext().getText(R.string.multirecyclerview_loading);
        noMoreHint = (String)getContext().getText(R.string.multirecyclerview_nomore_loading);
        loadingDoneHint = (String)getContext().getText(R.string.multirecyclerview_loading_done);
    }

    public void  setState(int state) {
        switch(state) {
            case STATE_LOADING:
                mText.setText(loadingHint);
                mProgressBar.setVisibility(View.VISIBLE);
                this.setVisibility(View.VISIBLE);
                    break;
            case STATE_COMPLETE:
                mText.setText(loadingDoneHint);
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setText(noMoreHint);
                mProgressBar.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }
}
