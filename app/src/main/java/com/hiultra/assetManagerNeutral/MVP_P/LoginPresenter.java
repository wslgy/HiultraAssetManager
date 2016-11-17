package com.hiultra.assetManagerNeutral.MVP_P;

import com.hiultra.assetManagerNeutral.MVP_M.LoginModel;
import com.hiultra.assetManagerNeutral.ui.activity.LoginActivity;
import com.hiultra.assetManagerNeutral.util.Util;

public class LoginPresenter extends BasePresenter<LoginActivity, LoginModel> {
    
    public LoginPresenter(LoginActivity view) {
        super(view);
    }

    @Override
    protected LoginModel initModel() {
        return new LoginModel();
    }
    
    public void login(String name, String pass) {
        mView.showLoading();
        mModel.login(name, pass, new LoginModel.LoginObserver() {
            
            @Override
            public void loginSuccess() {
                Util.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        mView.hideLoading();
                        mView.loginSuccess();
                    }
                });
            }
            
            @Override
            public void loginFailed() {
                Util.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        mView.hideLoading();
                        mView.loginFailed();
                    }
                });
            }
        });
    }
    
}
