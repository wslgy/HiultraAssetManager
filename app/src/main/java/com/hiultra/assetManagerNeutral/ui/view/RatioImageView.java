package com.hiultra.assetManagerNeutral.ui.view;

import com.minttown.hiultra.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("Recycle")
public class RatioImageView extends ImageView {
    
    private float ratio = 0.75f;// width/height
    
    public RatioImageView(Context context, AttributeSet attrs) {
        // super(context, attrs);
        this(context, attrs, 0);
    }
    
    public RatioImageView(Context context) {
        // super(context);
        this(context, null);
    }
    
    public RatioImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        ratio = ta.getFloat(R.styleable.RatioImageView_riv_ratio, ratio);
        ta.recycle();
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (ratio != 0) {
            int height = (int) (width / ratio);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            // LogUtil.i(this, "width: "+width + "  height: "+height);
            // ToastUtil.showToast(getContext(), "width: "+width +
            // "  height: "+height);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
