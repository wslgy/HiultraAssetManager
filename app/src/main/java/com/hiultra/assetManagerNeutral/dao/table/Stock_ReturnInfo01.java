package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 物品归还主表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:39:16
 */
@Table(name = "Stock_ReturnInfo01")
public class Stock_ReturnInfo01 extends BaseTable {
    
    public String ReturnDate;// 退库时间（datetime）
    public String BatchNumber;// 退库单号（64）
    public String OutBatchNumber;// 领用单号（64）
    public String WarehouseCode;// 退回位置编码（20）
    public String DepartmentCode;// 退库部门编码（20）
    public String UserCode;// 退库人编码（20）
    public String Remark;// 备注（500）

    public String getReturnDate() {
        return ReturnDate;
    }
    
    public void setReturnDate(String returnDate) {
        ReturnDate = returnDate;
    }
    
    public String getBatchNumber() {
        return BatchNumber;
    }
    
    public void setBatchNumber(String batchNumber) {
        BatchNumber = batchNumber;
    }
    
    public String getOutBatchNumber() {
        return OutBatchNumber;
    }
    
    public void setOutBatchNumber(String outBatchNumber) {
        OutBatchNumber = outBatchNumber;
    }
    
    public String getWarehouseCode() {
        return WarehouseCode;
    }
    
    public void setWarehouseCode(String warehouseCode) {
        WarehouseCode = warehouseCode;
    }
    
    public String getDepartmentCode() {
        return DepartmentCode;
    }
    
    public void setDepartmentCode(String departmentCode) {
        DepartmentCode = departmentCode;
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
    
}
