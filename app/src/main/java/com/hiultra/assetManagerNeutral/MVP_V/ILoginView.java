package com.hiultra.assetManagerNeutral.MVP_V;

public interface ILoginView extends IView {
    
    void showLoading();
    void hideLoading();
    void loginFailed();
    void loginSuccess();
}
