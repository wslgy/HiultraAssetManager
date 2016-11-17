package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 物品归还从表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:42:26
 */
@Table(name = "Stock_ReturnInfo02")
public class Stock_ReturnInfo02 extends BaseTable {
    
    public String BatchNumber;// 退库单号
    public String MaterialCode;// 物品编码
    public String UnitPrice;// 物品单价
    public int Quantity;// 退库数量
    public String Amount;// 退库金额
    
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
