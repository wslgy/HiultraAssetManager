package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

@Table(name = "PositionInfo")
public class PositionInfo extends BaseTable {
    
    public String Code;
    public String Name;
    public String Remark;
    
    public String getName() {
        return Name;
    }
    
    public void setName(String name) {
        Name = name;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
