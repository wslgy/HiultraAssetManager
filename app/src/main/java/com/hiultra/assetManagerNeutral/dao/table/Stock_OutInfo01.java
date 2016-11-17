package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 物品出库主表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:33:12
 */
@Table(name="Stock_OutInfo01")
public class Stock_OutInfo01 extends BaseTable {
    public String OutDate;//领用时间
    public String BatchNumber;//领用单号（64）
    public String WarehouseCode;//物品存放位置编码（20）
    public String DepartmentCode;//领用部门编码（20）
    public String OutUserCode;//领用人编码（20）
    public String Remark;//备注（500）
    
    public String getOutDate() {
        return OutDate;
    }
    public void setOutDate(String outDate) {
        OutDate = outDate;
    }
    public String getBatchNumber() {
        return BatchNumber;
    }
    public void setBatchNumber(String batchNumber) {
        BatchNumber = batchNumber;
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
    public String getOutUserCode() {
        return OutUserCode;
    }
    public void setOutUserCode(String outUserCode) {
        OutUserCode = outUserCode;
    }
    public String getRemark() {
        return Remark;
    }
    public void setRemark(String remark) {
        Remark = remark;
    }
    public String getCreateOperater() {
        return CreateOperater;
    }
    public void setCreateOperater(String createOperater) {
        CreateOperater = createOperater;
    }
    public String getCreateDateTime() {
        return CreateDateTime;
    }
    public void setCreateDateTime(String createDateTime) {
        CreateDateTime = createDateTime;
    }
    
    
}
