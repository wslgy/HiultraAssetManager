package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 角色表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:16:22
 */
@Table(name = "RoleInfo")
public class RoleInfo extends BaseTable {
    
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

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
    
}
