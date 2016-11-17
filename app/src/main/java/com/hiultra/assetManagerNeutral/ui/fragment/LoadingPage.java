package com.hiultra.assetManagerNeutral.ui.fragment;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.minttown.hiultra.R;

/**
 * 统一处理加载数据的逻辑,根据数据加载的结果(是否成功)去决定显示的布局
 * @author Tom
 * @date 2016年9月5日
 * @Time 下午1:57:26
 */
public abstract class LoadingPage extends FrameLayout {

    // 定义三种页面加载的状态
    enum PageState {
        STATE_LOADING, // 加载中
        STATE_SUCCESS, // 加载成功
        STATE_ERROR; // 加载失败
    }

    public LoadingPage(Context context) {
        this(context, null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLoadingpage();
    }

    /* 定义三个View对象,对应三种状态 */
    private View errorView;
    private View loadingView;
    private View successView;
    /** 默认状态为加载中 */
    private PageState mState = PageState.STATE_LOADING;
    private Handler handler = new Handler();

    /**
     * 初始化布局
     */
    private void initLoadingpage() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        if (loadingView == null)
            loadingView = View.inflate(getContext(), R.layout.page_loading, null);
        addView(loadingView, params);

        if (errorView == null) {
            errorView = View.inflate(getContext(), R.layout.page_error, null);
            Button btn_reload = (Button) errorView.findViewById(R.id.btn_reload);
            btn_reload.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    loadDataAndRefreshPage();
                }
            });
        }
        addView(errorView, params);

        if (successView == null)
            successView = createSuccessView();

        if (successView != null)
            addView(successView, params);
        else
            throw new IllegalArgumentException("createSuccessView()方法返回值不能为空");
        
        showPage();
        loadDataAndRefreshPage();
    }

    /**
     * 请求数据并且刷新界面
     */
    public void loadDataAndRefreshPage() {
     // 1,将状态变为加载中
        mState = PageState.STATE_LOADING;
        showPage();
        new Thread(){
            public void run() {
//                SystemClock.sleep(1000);
                // 1,请求数据
                Object data = requestData();
                mState = (data == null ? PageState.STATE_ERROR : PageState.STATE_SUCCESS);
                handler.post(new Runnable() {
                    
                    @Override
                    public void run() {
                        showPage();
                    }
                });
            };
        }.start();
    }


    /**
     * 根据状态切换不同的View显示
     */
    private void showPage() {
        loadingView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        successView.setVisibility(View.INVISIBLE);
        switch (mState) {
        case STATE_ERROR:
            errorView.setVisibility(View.VISIBLE);
            break;
        case STATE_LOADING:
            loadingView.setVisibility(View.VISIBLE);
            break;
        case STATE_SUCCESS:
            successView.setVisibility(View.VISIBLE);
            break;
        }
    }

    /**
     * 由每个界面自己去生成加载成功后的View
     * 
     * @return
     */
    protected abstract View createSuccessView();
    

    /**
     * 由具体的界面去处理请求数据的逻辑
     * @return
     */
    protected abstract Object requestData();

}
