package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * 资产调拨主表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午2:10:41
 */
@Table(name="Asset_AllocateInfo01")
public class Asset_AllocateInfo01 extends BatchTable {
    public String AllocateDate;// 调拨时间
    public String NewWarehouseCode;// 新存放位置
    public String NewDepartmentCode;// 新使用部门
    public String NewUserCode;// 新用人
    public String NewStorageCode;// 新管理人
    public String NewStorageDepartmentCode;// 新管理部门
    public String Remark;// 备注
    
    public String getAllocateDate() {
        return AllocateDate;
    }
    
    public void setAllocateDate(String allocateDate) {
        AllocateDate = allocateDate;
    }
    
    public String getNewDepartmentCode() {
        return NewDepartmentCode;
    }
    
    public void setNewDepartmentCode(String newDepartmentCode) {
        NewDepartmentCode = newDepartmentCode;
    }
    
    public String getNewUserCode() {
        return NewUserCode;
    }
    
    public void setNewUserCode(String newUserCode) {
        NewUserCode = newUserCode;
    }
    
    public String getNewStorageCode() {
        return NewStorageCode;
    }
    
    public void setNewStorageCode(String newStorageCode) {
        NewStorageCode = newStorageCode;
    }
    
    public String getNewStorageDepartmentCode() {
        return NewStorageDepartmentCode;
    }
    
    public void setNewStorageDepartmentCode(String newStorageDepartmentCode) {
        NewStorageDepartmentCode = newStorageDepartmentCode;
    }
    
    public String getNewWarehouseCode() {
        return NewWarehouseCode;
    }
    
    public void setNewWarehouseCode(String newWarehouseCode) {
        NewWarehouseCode = newWarehouseCode;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
