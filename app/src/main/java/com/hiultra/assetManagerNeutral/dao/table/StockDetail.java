package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:46:30
 */
@Table(name="StockDetail")
public class StockDetail extends BaseTable {
    
    public String MaterialCode;//物品编码（20）
    public String WarehouseCode;//位置编码（20）
    public String UnitPrice;//库存平均单价（decimal(18,2)）
    public int Quantity;//库存数量（decimal(18,2)）
    public String Amount;//库存金额（decimal(18,2)）
    
    public String getMaterialCode() {
        return MaterialCode;
    }
    public void setMaterialCode(String materialCode) {
        MaterialCode = materialCode;
    }
    public String getWarehouseCode() {
        return WarehouseCode;
    }
    public void setWarehouseCode(String warehouseCode) {
        WarehouseCode = warehouseCode;
    }
    public String getUnitPrice() {
        return UnitPrice;
    }
    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }
    public String getAmount() {
        return Amount;
    }
    public void setAmount(String amount) {
        Amount = amount;
    }
    public int getQuantity() {
        return Quantity;
    }
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
    
}
