package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * �ֿ���Ϣ��
 * 
 * @author Tom
 * @date 2016��10��28��
 * @Time ����5:12:03
 */
@Table(name = "WarehouseInfo")
public class WarehouseInfo extends BaseTable {
    
    public String Name;
    public String Code;
    /** ����Ա���� */
    public String UserCode;
    public String ParentCode;// �ϼ�����
    public String Levels;// �㼶��ʶ
    
    public String Remark;
    
    // public String UserIDs;
    // public String CompanyID;
    
    public String getName() {
        return Name;
    }
    
    public String getUserCode() {
        return UserCode;
    }
    
    public void setUserCode(String userCode) {
        UserCode = userCode;
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
    
    public void setName(String name) {
        Name = name;
    }
    
    public String getCode() {
        return Code;
    }
    
    public void setCode(String code) {
        Code = code;
    }
    
    public String getParentCode() {
        return ParentCode;
    }
    
    public void setParentID(String parentCode) {
        ParentCode = parentCode;
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
