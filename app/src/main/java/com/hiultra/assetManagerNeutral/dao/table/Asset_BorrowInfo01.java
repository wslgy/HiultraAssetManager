package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 资产借用主表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午2:31:24
 */
@Table(name="Asset_BorrowInfo01")
public class Asset_BorrowInfo01 extends BatchTable {
    
    public String BorrowDate;
    public String DepartmentCode;
    public String BorrowUserCode;
    public String Remark;
    /** 归还到期时间 */
    public String ExpireTime;
    
    public String getBatchNumber() {
        return BatchNumber;
    }
    
    public void setBatchNumber(String batchNumber) {
        BatchNumber = batchNumber;
    }
    
    public String getBorrowDate() {
        return BorrowDate;
    }
    
    public void setBorrowDate(String borrowDate) {
        BorrowDate = borrowDate;
    }
    
    public String getDepartmentCode() {
        return DepartmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        DepartmentCode = departmentCode;
    }

    public String getBorrowUserCode() {
        return BorrowUserCode;
    }

    public void setBorrowUserCode(String borrowUserCode) {
        BorrowUserCode = borrowUserCode;
    }

    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
    public String getExpireTime() {
        return ExpireTime;
    }
    
    public void setExpireTime(String expireTime) {
        ExpireTime = expireTime;
    }
    
}
