package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 部门信息表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:02:57
 */
@Table(name = "DepartmentInfo")
public class DepartmentInfo extends BaseTable {
    
    public String Name;// 名称
    public String ParentCode;// 编码
    public String Code;// 分类编码
    /** 联系人 */
    public String Contacts;
    /** 联系电话 */
    public String TelPhone;
    /** 传真 */
    public String Fax;
    public int DepartmentType;// 部门类型
    public String Levels;// 层级标识
    public String Remark;
    
    // public String Contacts;
    // public String TelPhone;
    // public String Fax;
    // public String Remark;
    // public String CompanyID;
    // public String sortLetters; // 显示数据拼音的首字母
    
    public String getContacts() {
        return Contacts;
    }

    public void setContacts(String contacts) {
        Contacts = contacts;
    }

    public String getTelPhone() {
        return TelPhone;
    }

    public void setTelPhone(String telPhone) {
        TelPhone = telPhone;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public void setParentCode(String parentCode) {
        ParentCode = parentCode;
    }

    public String getName() {
        return Name;
    }
    
    public void setName(String name) {
        Name = name;
    }
    
    public String getParentCode() {
        return ParentCode;
    }
    
    public void setParentID(String parentCode) {
        ParentCode = parentCode;
    }
    
    public String getCode() {
        return Code;
    }
    
    public void setCode(String code) {
        Code = code;
    }
    
    public int getDepartmentType() {
        return DepartmentType;
    }
    
    public void setDepartmentType(int departmentType) {
        DepartmentType = departmentType;
    }
    
    public String getLevels() {
        return Levels;
    }
    
    public void setLevels(String levels) {
        Levels = levels;
    }
    
    public String toString() {
        return Name;
    }
}
