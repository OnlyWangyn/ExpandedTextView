package com.example.wangyn.expandedtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by nancy on 16-12-26.
 */

public class ExpandTextView extends RelativeLayout implements View.OnClickListener{
    private TextView mTv;
    private TextView mOperation;
    private int mMaxCollapseLine = 4;
    private boolean mIsExpanded = false;
    private boolean mIsDisableExpand;
    private int mDisplayTextColor;
    private int mDisplayTextSize;
    private int mOpertateTextColor;
    private int mOperateTextSize;
    private String mCollapseText;
    private String mExpandText;
    private int mDirection;
    public ExpandTextView(Context context) {
        this(context,null);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandTextView, defStyleAttr, 0);
        mDisplayTextColor = typedArray.getColor(R.styleable.ExpandTextView_display_text_color, 0);
        mDisplayTextSize = typedArray.getDimensionPixelOffset(R.styleable.ExpandTextView_display_text_size, 0);
        mOpertateTextColor = typedArray.getColor(R.styleable.ExpandTextView_operate_text_color, 0);
        mOperateTextSize = typedArray.getDimensionPixelSize(R.styleable.ExpandTextView_operate_text_size, 0);
        mIsExpanded = typedArray.getBoolean(R.styleable.ExpandTextView_is_expanded, false);
        mMaxCollapseLine = typedArray.getInteger(R.styleable.ExpandTextView_max_collapse_line, 4);
        mCollapseText = typedArray.getString(R.styleable.ExpandTextView_collapse_text);
        mExpandText = typedArray.getString(R.styleable.ExpandTextView_expand_text);
        mDirection = typedArray.getInt(R.styleable.ExpandTextView_operate_text_direction, 0);
        if (mDirection < 0 || mDirection > 2) {
            mDirection = 0;
        }
        typedArray.recycle();
        initViews(context);
    }

    @Override
    public void onClick(View v) {
      if(mOperation==null||mOperation.getVisibility()==View.GONE)return;
        mIsExpanded = !mIsExpanded;
        requestLayout();
    }

    public void setText(@Nullable CharSequence text) {
        mTv.setText(text);
        setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        requestLayout();
    }
    public void setExpandDisable(boolean isDisable) {
        mIsDisableExpand = isDisable;
        if (mIsDisableExpand) {
            mIsExpanded = false;
        }

    }
    public void setExpanded(boolean expanded) {
        if (mIsDisableExpand) {
            return;
        }
        if (mIsExpanded != expanded) {
            mIsExpanded = expanded;
            requestLayout();
        }
    }
    public void setMaxCollapseLine(int lines){
        mMaxCollapseLine = lines;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getVisibility() == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        if(mOperation!=null) {
            mOperation.setVisibility(View.GONE);
        }
        mTv.setMaxLines(Integer.MAX_VALUE);
        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTv.getLineCount() <= mMaxCollapseLine) {
            return;
        }
        if (!mIsExpanded) {
            mTv.setMaxLines(mMaxCollapseLine);
        }
        if(mOperation!=null) {
            mOperation.setVisibility(mIsDisableExpand ? View.GONE : View.VISIBLE);
            mOperation.setText(getText());
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initViews(Context context) {
        if(getChildCount() > 0){
            removeAllViewsInLayout();
        }
        mTv = new TextView(context);
        mTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,mDisplayTextSize);
        mTv.setTextColor(mDisplayTextColor);
         mTv.setId(R.id.expandtextview_display);
        addView(mTv);
        if(mCollapseText!=null && !"".equals(mCollapseText) && mExpandText!=null && !"".equals(mExpandText)) {
            mOperation = new TextView(context);
            mOperation.setTextSize(TypedValue.COMPLEX_UNIT_PX,mOperateTextSize);
            mOperation.setTextColor(mOpertateTextColor);
            mOperation.setOnClickListener(this);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.BELOW,mTv.getId());
            switch (mDirection) {
                case 0:
                    lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    break;
                case 1:
                    lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                    break;
                case 2:
                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    break;
                default:
                    break;
            }
            addView(mOperation,lp);

        }
    }
    private String getText(){
        return mIsExpanded?mCollapseText:mExpandText;
    }
}
