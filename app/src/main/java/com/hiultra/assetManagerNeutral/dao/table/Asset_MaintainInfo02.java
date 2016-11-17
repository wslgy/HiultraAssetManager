package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 资产保养从表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午2:55:10
 */
@Table(name = "Asset_MaintainInfo02")
public class Asset_MaintainInfo02 extends BatchTable {
    
    public String MaterialCode;
    public String MaintainCost;
    public String Contents;
    public String Remark;
    
    public String getMaterialCode() {
        return MaterialCode;
    }
    
    public void setMaterialCode(String materialCode) {
        MaterialCode = materialCode;
    }
    
    public String getMaintainCost() {
        return MaintainCost;
    }
    
    public void setMaintainCost(String maintainCost) {
        MaintainCost = maintainCost;
    }
    
    public String getContents() {
        return Contents;
    }
    
    public void setContents(String contents) {
        Contents = contents;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
