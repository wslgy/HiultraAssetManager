package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 资产保养主表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午2:55:00
 */
@Table(name = "Asset_MaintainInfo02")
public class Asset_MaintainInfo01 extends BatchTable {
    
    public String MaintainDate;
    public String DepartmentCode;
    public String Remark;
    
    public String getMaintainDate() {
        return MaintainDate;
    }
    
    public void setMaintainDate(String maintainDate) {
        MaintainDate = maintainDate;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
