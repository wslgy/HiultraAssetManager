package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 物品入库主表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:24:12
 */
@Table(name="Stock_InInfo01")
public class Stock_InInfo01 extends BaseTable {
    
    public String InDate;//入库时间
    public String BatchNumber;//入库单号（64）
    public String WarehouseCode;//存放位置编码（20）
    public String Remark;//备注（500）
    
    public String getInDate() {
        return InDate;
    }
    public void setInDate(String inDate) {
        InDate = inDate;
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
