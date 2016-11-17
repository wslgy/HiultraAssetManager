package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 物品出库从表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:34:57
 */
@Table(name = "Stock_OutInfo02")
public class Stock_OutInfo02 extends BaseTable {
    
    public String BatchNumber;
    public String MaterialCode;
    /** 出库单价（库存的平均单价） */
    public String UnitPrice;
    public int Quantity;
    /** 出库金额 */
    public String Amount;
    
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
    
}
