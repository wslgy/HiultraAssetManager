package com.hiultra.assetManagerNeutral.MVP_P;

import com.hiultra.assetManagerNeutral.MVP_M.IModel;
import com.hiultra.assetManagerNeutral.MVP_V.IView;

public abstract class BasePresenter<V extends IView, M extends IModel> implements IPresenter<V, M> {
    
    protected V mView;
    protected M mModel;
    
    public BasePresenter(V view) {
        mView = view;
        mModel = initModel();
        if(mModel == null) throw new IllegalArgumentException("initModel()方法返回值不能为null");
    }
    
    protected abstract M initModel();

    public boolean isViewAttached() {
        return mView != null;
    }
    
    public void detachView() {
        mView = null;
    }
    
    public V getView() {
        return mView;
    }
    
    public M getModel() {
        return mModel;
    }
    
}
