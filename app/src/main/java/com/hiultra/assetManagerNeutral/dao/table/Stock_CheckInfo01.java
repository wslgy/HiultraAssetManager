package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 物品盘点主表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:19:32
 */
@Table(name="Stock_CheckInfo01")
public class Stock_CheckInfo01 extends BaseTable {
    
    public String BatchNumber;// 盘点单号
    public String CheckDate;// 盘点时间
    public String CheckTheme;// 盘点主题
    public String CheckStatus;// 盘点状态
    public String CheckWarehouseCode;// 盘点位置编码
    public String Remark;// 备注（1000）
    public String CheckDepartmentCode;// 盘点部门编码
    public String CheckUserCode;// 盘点负责人编码
    
    public String getCheckDate() {
        return CheckDate;
    }
    
    public void setCheckDate(String checkDate) {
        CheckDate = checkDate;
    }
    
    public String getBatchNumber() {
        return BatchNumber;
    }
    
    public void setBatchNumber(String batchNumber) {
        BatchNumber = batchNumber;
    }
    
    public String getCheckTheme() {
        return CheckTheme;
    }
    
    public void setCheckTheme(String checkTheme) {
        CheckTheme = checkTheme;
    }
    
    public String getCheckStatus() {
        return CheckStatus;
    }
    
    public void setCheckStatus(String checkStatus) {
        CheckStatus = checkStatus;
    }
    
    public String getCheckWarehouseCode() {
        return CheckWarehouseCode;
    }
    
    public void setCheckWarehouseCode(String checkWarehouseCode) {
        CheckWarehouseCode = checkWarehouseCode;
    }
    
    public String getCheckDepartmentCode() {
        return CheckDepartmentCode;
    }
    
    public void setCheckDepartmentCode(String checkDepartmentCode) {
        CheckDepartmentCode = checkDepartmentCode;
    }
    
    public String getCheckUserCode() {
        return CheckUserCode;
    }
    
    public void setCheckUserCode(String checkUserCode) {
        CheckUserCode = checkUserCode;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
    public String toString() {
        return CheckTheme;
    }
    
}
