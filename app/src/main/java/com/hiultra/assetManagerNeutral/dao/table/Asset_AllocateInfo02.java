package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * 资产调拨从表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午2:14:48
 */
@Table(name = "Asset_AllocateInfo02")
public class Asset_AllocateInfo02 extends BatchTable {
    
    public String MaterialCode;// 资产编码
    public String OldDepartmentCode;// 原使用部门
    public String OldUserCode;// 原使用人员
    public String OldStorageCode;// 原管理人员
    public String OldStorageDepartmentCode;// 原管理部门
    public String OldWarehouseCode;// 原存放位置
    public String AllocateInDate;// 调用时间
    public String Remark;// 备注

    @Transient
    public transient String name;
    @Transient
    public transient String departmentName;
    @Transient
    public transient String userName;
    @Transient
    public transient String storageDepartmentName;
    @Transient
    public transient String storageUserName;
    @Transient
    public transient String specification;
    
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStorageDepartmentName() {
        return storageDepartmentName;
    }

    public void setStorageDepartmentName(String storageDepartmentName) {
        this.storageDepartmentName = storageDepartmentName;
    }

    public String getStorageUserName() {
        return storageUserName;
    }

    public void setStorageUserName(String storageUserName) {
        this.storageUserName = storageUserName;
    }
    
    public String getMaterialCode() {
        return MaterialCode;
    }
    
    public void setMaterialCode(String materialCode) {
        MaterialCode = materialCode;
    }
    
    public String getOldDepartmentCode() {
        return OldDepartmentCode;
    }
    
    public void setOldDepartmentCode(String oldDepartmentCode) {
        OldDepartmentCode = oldDepartmentCode;
    }
    
    public String getOldUserCode() {
        return OldUserCode;
    }
    
    public void setOldUserCode(String oldUserCode) {
        OldUserCode = oldUserCode;
    }
    
    public String getOldStorageCode() {
        return OldStorageCode;
    }
    
    public void setOldStorageCode(String oldStorageCode) {
        OldStorageCode = oldStorageCode;
    }
    
    public String getOldStorageDepartmentCode() {
        return OldStorageDepartmentCode;
    }
    
    public void setOldStorageDepartmentCode(String oldStorageDepartmentCode) {
        OldStorageDepartmentCode = oldStorageDepartmentCode;
    }
    
    public String getOldWarehouseCode() {
        return OldWarehouseCode;
    }
    
    public void setOldWarehouseCode(String oldWarehouseCode) {
        OldWarehouseCode = oldWarehouseCode;
    }
    
    public String getAllocateInDate() {
        return AllocateInDate;
    }
    
    public void setAllocateInDate(String allocateInDate) {
        AllocateInDate = allocateInDate;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
