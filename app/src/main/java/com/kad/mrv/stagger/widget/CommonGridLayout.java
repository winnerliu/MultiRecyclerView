package com.kad.mrv.stagger.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import com.kad.mrv.R;

import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.makeMeasureSpec;


public class CommonGridLayout extends ViewGroup {
    private final String TAG = "TopicGridLayout";

    int margin = 2;// 每个格子的水平和垂直间隔
    int colums = 2;
    private int mMaxChildWidth = 0;
    private int mMaxChildHeight = 0;
    int count = 0;

    public static int TYPE_HOT_ZHUANTI_TUIJIAN = 4;
    public static int TYPE_HOT_KESHI = 3;
    public static int TYPE_HEALTH_TUIJIAN = 2;
    public static int TYPE_DACU = 1;
    public static int TYPE_COMMON = 0;
    private int type = TYPE_COMMON;

    GridAdatper adapter;

    public CommonGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CommonGridLayout);
            colums = a.getInteger(R.styleable.CommonGridLayout_numColumns, 2);
            margin = (int) a.getInteger(R.styleable.CommonGridLayout_itemMargin, 2);
        }
    }

    public CommonGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonGridLayout(Context context) {
        this(context, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        mMaxChildWidth = 0;
        mMaxChildHeight = 0;

        int modeW = MeasureSpec.AT_MOST, modeH = MeasureSpec.AT_MOST;
        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED)
            modeW = MeasureSpec.UNSPECIFIED;
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.UNSPECIFIED)
            modeH = MeasureSpec.UNSPECIFIED;

        final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), modeW);
        final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), modeH);

        count = getChildCount();
        if (count == 0) {
            super.onMeasure(childWidthMeasureSpec, childHeightMeasureSpec);
            return;
        }
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }

            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);

            mMaxChildWidth = Math.max(mMaxChildWidth, child.getMeasuredWidth());
            mMaxChildHeight = Math.max(mMaxChildHeight, child.getMeasuredHeight());
        }
        setMeasuredDimension(resolveSize(mMaxChildWidth, widthMeasureSpec), resolveSize(mMaxChildHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        int height = b - t;// 布局区域高度
        int width = r - l;// 布局区域宽度

        if (type == TYPE_COMMON) {
            onCommonLayout(width, height);

        } else if (type == TYPE_DACU) {
            onDaCuLayout(width, height);

        } else if (type == TYPE_HEALTH_TUIJIAN) {
            onHealthTuiJianLayout(width, height);

        } else if (type == TYPE_HOT_KESHI) {
            onHotKeShiLayout(width, height);

        } else if (type == TYPE_HOT_ZHUANTI_TUIJIAN) {
            onHotZhuantiTuiJianLayout(width, height);
        }

    }


    public int getColums() {
        return colums;
    }

    public void setColums(int colums) {
        this.colums = colums;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public int getType() {
        return type;
    }

    public  void setType(int type) {
        this.type = type;
    }

    public GridAdatper getGridAdapter() {
        return adapter != null ? adapter : null;
    }

    public void removeAllViews() {
        removeAllViews();
    }

    public interface GridAdatper {
        View getView(int index);

        int getCount();
    }

    /**
     * 设置适配器
     */
    public void setGridAdapter(GridAdatper adapter) {
        this.adapter = adapter;
        // 动态添加视图
        int size = adapter.getCount();
        for (int i = 0; i < size; i++) {
            addView(adapter.getView(i));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int index);
    }

    public void setOnItemClickListener(final OnItemClickListener click) {
        if (this.adapter == null)
            return;
        for (int i = 0; i < adapter.getCount(); i++) {
            final int index = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    click.onItemClick(v, index);
                }
            });
        }
    }

    private void onCommonLayout(int parentW, int parentH) {
        int rows = count % colums == 0 ? count / colums : count / colums + 1;// 行数
        if (count == 0)
            return;
        int gridW = (parentW - margin * (colums - 1)) / colums;// 格子宽度
        int gridH = (parentH - margin * rows) / rows;// 格子高度

        int left = 0;
        int top = margin;

        for (int i = 0; i < rows; i++) {// 遍历行
            for (int j = 0; j < colums; j++) {// 遍历每一行的元素
                View child = this.getChildAt(i * colums + j);
                if (child == null)
                    return;
                left = j * gridW + j * margin;
                // 如果当前布局宽度和测量宽度不一样，就直接用当前布局的宽度重新测量
                if (gridW != child.getMeasuredWidth() || gridH != child.getMeasuredHeight()) {
                    child.measure(makeMeasureSpec(gridW, EXACTLY), makeMeasureSpec(gridH, EXACTLY));
                }
                child.layout(left, top, left + gridW, top + gridH);
                // System.out
                // .println("--top--" + top + ",bottom=" + (top + gridH));

            }
            top += gridH + margin;
        }
    }

    private void onDaCuLayout(int parentW, int parentH) {
        int mColums = 3;
        int tmp = count - 1;
        int rows = tmp > 0 ? (tmp % mColums == 0 ? tmp / mColums : tmp / mColums + 1) : 0;// 行数
        if (count == 0)
            return;
        int left = 0;
        int top = margin;
        // 第一行宽高
        int itemFirstW = parentW - margin * 2;// 格子宽度
        int itemFirstH = (int) (itemFirstW * 0.4);
        // 其他行宽高
        int itemOtherW = (parentW - margin * (mColums - 1)) / mColums;// 格子宽度
        int itemOtherH = (parentH - margin * (rows + 1) - itemFirstH) / rows;// 格子高度

        if (count > 0) {

            View child = this.getChildAt(0);
            if (child == null)
                return;
            left = margin;

            // 如果当前布局宽度和测量宽度不一样，就直接用当前布局的宽度重新测量
            if (itemFirstW != child.getMeasuredWidth() || itemFirstH != child.getMeasuredHeight()) {
                child.measure(makeMeasureSpec(itemFirstW, EXACTLY), makeMeasureSpec(itemFirstH, EXACTLY));
            }
            child.layout(left, top, left + itemFirstW, top + itemFirstH);

            top += itemFirstH + margin;
        }

        for (int i = 0; i < rows; i++) {// 遍历行
            for (int j = 0; j < mColums; j++) {// 遍历每一行的元素
                View child = this.getChildAt(i * mColums + j + 1);
                if (child == null)
                    return;
                left = j * itemOtherW + j * margin;
                // 如果当前布局宽度和测量宽度不一样，就直接用当前布局的宽度重新测量
                if (itemOtherW != child.getMeasuredWidth() || itemOtherH != child.getMeasuredHeight()) {
                    child.measure(makeMeasureSpec(itemOtherW, EXACTLY), makeMeasureSpec(itemOtherH, EXACTLY));
                }
                child.layout(left, top, left + itemOtherW, top + itemOtherH);
                System.out.println("--top--" + top + ",bottom=" + (top + itemOtherH));

            }
            top += itemOtherH + margin;
        }

    }

    private void onHealthTuiJianLayout(int parentW, int parentH) {
        int mColums = 4;// 列数
        int tmp = count - 4;
        int rows = tmp > 0 ? (tmp % mColums == 0 ? tmp / mColums : tmp / mColums + 1) : 0;// 行数
        if (count == 0)
            return;
        int left = 0;
        int top = margin;
        // 第一行宽高
        int itemFirstW = (parentW - margin) / 2;// 格子宽度
        int itemFirstH = (int) (itemFirstW * 0.5);
        // 其他行宽高
        int itemOtherW = (parentW - margin * (mColums - 1)) / mColums;// 格子宽度
        int itemOtherH = itemOtherW;

        if (count > 1) {
            for (int k = 0; k < 2; k++) {

                View child = this.getChildAt(k);
                if (child == null)
                    return;
                left = k * (margin + itemFirstW);

                // 如果当前布局宽度和测量宽度不一样，就直接用当前布局的宽度重新测量
                if (itemFirstW != child.getMeasuredWidth() || itemFirstH != child.getMeasuredHeight()) {
                    child.measure(makeMeasureSpec(itemFirstW, EXACTLY), makeMeasureSpec(itemFirstH, EXACTLY));
                }
                child.layout(left, top, left + itemFirstW, top + itemFirstH);

            }
            top += itemFirstH + margin;

        }

        for (int i = 0; i < rows; i++) {// 遍历行
            for (int j = 0; j < mColums; j++) {// 遍历每一行的元素
                View child = this.getChildAt(i * mColums + j + 2);
                if (child == null)
                    return;
                left = j * itemOtherW + j * margin;
                // 如果当前布局宽度和测量宽度不一样，就直接用当前布局的宽度重新测量
                if (itemOtherW != child.getMeasuredWidth() || itemOtherH != child.getMeasuredHeight()) {
                    child.measure(makeMeasureSpec(itemOtherW, EXACTLY), makeMeasureSpec(itemOtherH, EXACTLY));
                }
                child.layout(left, top, left + itemOtherW, top + itemOtherH);
                System.out.println("--top--" + top + ",bottom=" + (top + itemOtherH));

            }
            top += itemOtherH + margin;
        }
    }

    private void onHotZhuantiTuiJianLayout(int parentW, int parentH) {
        if (count == 0)
            return;
        int left = 0;
        int top = margin;


// 第一个item宽高
        int itemFirstW = (int) (parentW * 0.4);
        int itemFirstH = (int) (itemFirstW * 1.5);
        // 第二个item宽高
        int itemSecW = (int) (parentW * 0.6 - margin);
        int itemSecH = (itemFirstH - margin) / 2;

        // 第三个item宽高
        int itemThirdW = (int) (parentW * 0.6 - margin);
        int itemThirdH = (itemFirstH - margin) / 2;

        if (count >= 3) {
            for (int i = 0; i < 3; i++) {
                View child = this.getChildAt(i);
                if (child == null)
                    return;

                if (i == 0) {
                    left = 0;

                    // 如果当前布局宽度和测量宽度不一样，就直接用当前布局的宽度重新测量
                    if (itemFirstW != child.getMeasuredWidth() || itemFirstH != child.getMeasuredHeight()) {
                        child.measure(makeMeasureSpec(itemFirstW, EXACTLY), makeMeasureSpec(itemFirstH, EXACTLY));
                    }
                    child.layout(left, top, left + itemFirstW, top + itemFirstH);

                } else if (i == 1) {
                    left = itemFirstW + margin;

                    // 如果当前布局宽度和测量宽度不一样，就直接用当前布局的宽度重新测量
                    if (itemSecW != child.getMeasuredWidth() || itemSecH != child.getMeasuredHeight()) {
                        child.measure(makeMeasureSpec(itemSecW, EXACTLY), makeMeasureSpec(itemSecH, EXACTLY));
                    }
                    child.layout(left, top, left + itemSecW, top + itemSecH);

                    top += itemSecH + margin;
                } else if (i == 2) {
                    left = itemFirstW + margin;

                    // 如果当前布局宽度和测量宽度不一样，就直接用当前布局的宽度重新测量
                    if (itemThirdW != child.getMeasuredWidth() || itemThirdH != child.getMeasuredHeight()) {
                        child.measure(makeMeasureSpec(itemThirdW, EXACTLY), makeMeasureSpec(itemThirdH, EXACTLY));
                    }
                    child.layout(left, top, left + itemThirdW, top + itemSecH);

                }
            }
        }

    }

    private void onHotKeShiLayout(int parentW, int parentH) {
        if (count == 0)
            return;
        int left = 0;
        int top = margin;

        // 第一行宽高
        int firstRowW = (parentW - margin) / 2;// 格子宽度
        int firstRowH = firstRowW;

        if (count > 1) {
            for (int k = 0; k < 2; k++) {

                View child = this.getChildAt(k);
                if (child == null)
                    return;
                left = k * (margin + firstRowW);

                // 如果当前布局宽度和测量宽度不一样，就直接用当前布局的宽度重新测量
                if (firstRowW != child.getMeasuredWidth() || firstRowH != child.getMeasuredHeight()) {
                    child.measure(makeMeasureSpec(firstRowW, EXACTLY), makeMeasureSpec(firstRowH, EXACTLY));
                }
                child.layout(left, top, left + firstRowW, top + firstRowH);

            }
            top += firstRowH + margin;

        }

        // 第二行宽高
        int secRowW = (parentW - margin) / 2;// 格子宽度
        int secRowH = (int) (secRowW * 0.6);// 格子高度

        if (count > 4) {
            for (int n = 2; n < 4; n++) {

                View child = this.getChildAt(n);
                if (child == null)
                    return;
                left = (n - 2) * (margin + secRowW);

                // 如果当前布局宽度和测量宽度不一样，就直接用当前布局的宽度重新测量
                if (secRowW != child.getMeasuredWidth() || secRowH != child.getMeasuredHeight()) {
                    child.measure(makeMeasureSpec(secRowW, EXACTLY), makeMeasureSpec(secRowH, EXACTLY));
                }
                child.layout(left, top, left + secRowW, top + secRowH);

            }
            top += secRowH + margin;

        }
        if (count > 5) {
            int mColums = 4;// 列数
            int tmp = count - 4;
            int rows = tmp > 0 ? (tmp % mColums == 0 ? tmp / mColums : tmp / mColums + 1) : 0;// 行数

            // 其他行宽高
            int itemOtherW = (parentW - margin * (mColums - 1)) / mColums;// 格子宽度
            int itemOtherH = (int) (itemOtherW * 1.5);

            for (int i = 0; i < rows; i++) {// 遍历行
                for (int j = 0; j < mColums; j++) {// 遍历每一行的元素
                    View child = this.getChildAt(i * mColums + j + 4);
                    if (child == null)
                        return;
                    left = j * itemOtherW + j * margin;
                    // 如果当前布局宽度和测量宽度不一样，就直接用当前布局的宽度重新测量
                    if (itemOtherW != child.getMeasuredWidth() || itemOtherH != child.getMeasuredHeight()) {
                        child.measure(makeMeasureSpec(itemOtherW, EXACTLY), makeMeasureSpec(itemOtherH, EXACTLY));
                    }
                    child.layout(left, top, left + itemOtherW, top + itemOtherH);
                    System.out.println("--top--" + top + ",bottom=" + (top + itemOtherH));

                }
                top += itemOtherH + margin;
            }
        }
    }
}
