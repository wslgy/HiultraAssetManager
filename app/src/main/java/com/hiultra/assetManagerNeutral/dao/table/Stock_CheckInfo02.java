package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 物品盘点从表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:20:29
 */
@Table(name="Stock_CheckInfo02")
public class Stock_CheckInfo02 extends BaseTable {
    
    public String BatchNumber;//
    public String MaterialCode;// 物品编码（20）
    public String CheckResult;//盘点结果（盘盈、盘亏、正常）（10）
    public int StockQuantity;//库存数量
    public String StockAmount;//库存金额（decimal(18,2)）
    public int CheckQuantity;//盘点数量
    public String CheckAmount;//盘点金额（decimal(18,2)）
    
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
    public String getCheckResult() {
        return CheckResult;
    }
    public void setCheckResult(String checkResult) {
        CheckResult = checkResult;
    }
    public String getStockAmount() {
        return StockAmount;
    }
    public void setStockAmount(String stockAmount) {
        StockAmount = stockAmount;
    }
    public String getCheckAmount() {
        return CheckAmount;
    }
    public void setCheckAmount(String checkAmount) {
        CheckAmount = checkAmount;
    }
    public int getStockQuantity() {
        return StockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        StockQuantity = stockQuantity;
    }
    public int getCheckQuantity() {
        return CheckQuantity;
    }
    public void setCheckQuantity(int checkQuantity) {
        CheckQuantity = checkQuantity;
    }
    
}
