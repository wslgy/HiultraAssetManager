package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 物品入库从表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:28:38
 */
@Table(name="Stock_InInfo02")
public class Stock_InInfo02 extends BaseTable {
    
    public String BatchNumber;
    public String MaterialCode;
    /** 入库单价 */
    public String UnitPrice;
    public int Quantity;
    /** 入库金额 */
    public String Amount;
    public String Remark;
    
    public String getBatchNumber() {
        return BatchNumber;
    }
    public void setBatchNumber(String batchNumber) {
        BatchNumber = batchNumber;
    }
    public String getMaterialCode() {
        return MaterialCode;
    }
    public void setMaterialCode(String materialCode) {
        MaterialCode = materialCode;
    }
    public String getUnitPrice() {
        return UnitPrice;
    }
    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }
    public int getQuantity() {
        return Quantity;
    }
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
    public String getAmount() {
        return Amount;
    }
    public void setAmount(String amount) {
        Amount = amount;
    }
    public String getRemark() {
        return Remark;
    }
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
