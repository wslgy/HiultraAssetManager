package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

@Table(name = "SystemArgument")
public class SystemArgument extends BaseTable {
    
    public String Name;
    public String Value;
    public String Remark;
    /** 类别ID */
    public int TypeID;
    @Override
    public String toString() {
        return Name;
    }
    public String getName() {
        return Name;
    }
    
    public void setName(String name) {
        Name = name;
    }
    
    public String getValue() {
        return Value;
    }
    
    public void setValue(String value) {
        Value = value;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
    public int getTypeID() {
        return TypeID;
    }
    
    public void setTypeID(int typeID) {
        TypeID = typeID;
    }
    
}
