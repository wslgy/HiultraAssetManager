package com.hiultra.assetManagerNeutral.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.minttown.hiultra.R;

public class HorizontalProgressBarWithProgress extends ProgressBar {

    private static final int DEFAULT_TEXT_SIZE = 16; // 默认字体大小sp
    private static final int DEFAULT_TEXT_COLOR = 0xFFFC00D1;
    private static final int DEFAULT_UNREACH_COLOR = 0xFFD3D6DA;
    private static final int DEFAULT_UNREACH_HEIGHT = 5; // dp
    private static final int DEFAULT_REACH_COLOR = DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_REACH_HEIGHT = 5; // dp
    private static final int DEFAULT_TEXT_OFFSET = 10; // dp

    private int mTextSize = sp2px(DEFAULT_TEXT_SIZE);
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mUnreachColor = DEFAULT_UNREACH_COLOR;
    private int mUnreachHeight = dp2px(DEFAULT_UNREACH_HEIGHT);
    private int mReachColor = DEFAULT_REACH_COLOR;
    private int mReachHeight = dp2px(DEFAULT_REACH_HEIGHT);
    private int mTextOffset = dp2px(DEFAULT_TEXT_OFFSET);

    private Paint mPaint = new Paint();

    private int mRealWidth;

    public HorizontalProgressBarWithProgress(Context context) {
        this(context, null);
    }

    public HorizontalProgressBarWithProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBarWithProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    /**
     * 获取自定义属性
     * 
     * @param attrs
     */
    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBarWithProgress);

        mTextSize = (int) ta.getDimension(R.styleable.HorizontalProgressBarWithProgress_progress_text_size, mTextSize);
        mTextColor = ta.getColor(R.styleable.HorizontalProgressBarWithProgress_progress_text_color, mTextColor);
        mTextOffset = (int) ta.getDimension(R.styleable.HorizontalProgressBarWithProgress_progress_text_offset, mTextOffset);
        mUnreachColor = ta.getColor(R.styleable.HorizontalProgressBarWithProgress_progress_unreach_color, mUnreachColor);
        mUnreachHeight = (int) ta.getDimension(R.styleable.HorizontalProgressBarWithProgress_progress_unreach_height,
                mUnreachHeight);
        mReachColor = ta.getColor(R.styleable.HorizontalProgressBarWithProgress_progress_reach_color, mReachColor);
        mReachHeight = (int) ta.getDimension(R.styleable.HorizontalProgressBarWithProgress_progress_reach_height, mReachHeight);

        ta.recycle();
        mPaint.setTextSize(mTextSize);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthValue = MeasureSpec.getSize(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(widthValue, height);
        mRealWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightValue = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            result = heightValue;
        } else {
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());
            result = getPaddingBottom() + getPaddingTop()
                    + Math.max(Math.max(mReachHeight, mUnreachHeight), Math.abs(textHeight));
            if(heightMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, heightValue);
            }
        }
        return result;
    }
    
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(), getHeight()/2);
        
        boolean noNeedUnreach = false;
        
        String text = getProgress()*100 / getMax() + "%";
        int textWidth = (int) mPaint.measureText(text);
        float radio = getProgress()*1.0f / getMax();
        float progressX = radio*mRealWidth;
        if(progressX+ textWidth > mRealWidth) {
            progressX = mRealWidth - textWidth;
            noNeedUnreach = true;
        }
        
        float endX = progressX - mTextOffset/2;
        if(endX > 0) {
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0, 0, endX, 0, mPaint);
        }
        
        mPaint.setColor(mTextColor);
        int y = (int) (-(mPaint.descent()+mPaint.ascent())/2);
        canvas.drawText(text, progressX, y, mPaint);
        
        if(!noNeedUnreach) {
            float start = progressX + mTextOffset / 2 + textWidth;
            mPaint.setColor(mUnreachColor);
            mPaint.setStrokeWidth(mUnreachHeight);
            canvas.drawLine(start, 0, mRealWidth, 0, mPaint);
        }
        
        canvas.restore();
    
    
    }

    private int dp2px(int dpValues) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValues, getResources().getDisplayMetrics());
    }

    private int sp2px(int spValues) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValues, getResources().getDisplayMetrics());
    }

}
