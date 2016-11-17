package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 资产报停主表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午3:06:53
 */
@Table(name = "Asset_StopInfo01")
public class Asset_StopInfo01 extends BatchTable {
    
    public String StopDate;
    public String Remark;
    
    public String getStopDate() {
        return StopDate;
    }
    
    public void setStopDate(String stopDate) {
        StopDate = stopDate;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
