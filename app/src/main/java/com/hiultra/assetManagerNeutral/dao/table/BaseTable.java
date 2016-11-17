package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Unique;

public abstract class BaseTable {
    
    @Id@NoAutoIncrement
    public int ID;
    // /更新人
    public String UpdateOperater;
    // /更新日期
    public String UpdateDateTime;
    // 创建人
    public String CreateOperater;
    // /创建日期
    public String CreateDateTime;
    /** 是否删除 */
    public transient boolean DelFlg;
    public transient String ifuodate;
    
    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public boolean isDelFlg() {
        return DelFlg;
    }

    public void setDelFlg(boolean delFlg) {
        DelFlg = delFlg;
    }

    public String getUpdateOperater() {
        return UpdateOperater;
    }

    public void setUpdateOperater(String updateOperater) {
        UpdateOperater = updateOperater;
    }

    public String getUpdateDateTime() {
        return UpdateDateTime;
    }

    public void setUpdateDateTime(String updateDateTime) {
        UpdateDateTime = updateDateTime;
    }

    public String getCreateOperater() {
        return CreateOperater;
    }

    public void setCreateOperater(String createOperater) {
        CreateOperater = createOperater;
    }

    public String getCreateDateTime() {
        return CreateDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        CreateDateTime = createDateTime;
    }

    public String getIfuodate() {
        return ifuodate;
    }

    public void setIfuodate(String ifuodate) {
        this.ifuodate = ifuodate;
    }
    
}
