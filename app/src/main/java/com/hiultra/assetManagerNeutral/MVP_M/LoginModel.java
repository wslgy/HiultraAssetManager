package com.hiultra.assetManagerNeutral.MVP_M;

import android.os.SystemClock;

import com.hiultra.assetManagerNeutral.dao.DBTools;
import com.hiultra.assetManagerNeutral.dao.table.UserInfo;
import com.hiultra.assetManagerNeutral.global.Varible;
import com.hiultra.assetManagerNeutral.util.Util;
import com.hiultra.assetManagerNeutral.web.WebUtil;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

public class LoginModel implements IModel {
    
    public void login(final String name, final String pass, final LoginObserver observer) {
        if(Util.isNetworkConnected()) { 
            new Thread(){
                public void run() {
                    SystemClock.sleep(5000);
                    UserInfo u = WebUtil.Login(name, pass);
                    if(u == null) {
                        observer.loginFailed();
                    } else {
                        observer.loginSuccess();
                        Varible.userInfo = u;
                        try {
                            save(u);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                };
            }.start();
        } else { // 离线登录
            UserInfo u = DBTools.findFirst(Selector.from(UserInfo.class).where("LoginName", "=", name).and("Password", "=", pass));
            if(u == null) {
                observer.loginFailed();
            } else {
                Varible.userInfo = u;
                observer.loginSuccess();
            }
        }
    }
    
    /**
     * 保存或者更新数据库
     * @param u
     * @throws DbException 
     */
    protected void save(UserInfo u) throws DbException {
        UserInfo info = DBTools.findFirst(Selector.from(UserInfo.class).where("LoginName", "=", u.getLoginName()).and("Password", "=", u.getPassword()));
        if(info == null) DBTools.save(u);
        else DBTools.update(u);
    }

    public interface LoginObserver {
        void loginSuccess();
        void loginFailed();
    }
}
