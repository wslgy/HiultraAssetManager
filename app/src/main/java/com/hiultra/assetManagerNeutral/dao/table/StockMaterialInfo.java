package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 物品主表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:48:18
 */
@Table(name="StockMaterialInfo")
public class StockMaterialInfo extends BaseTable {
    public String Code;//物品编码（20）
    public String Name;//物品名称（50）
    public String MaterialModelCode;//所属分类编码（20）
    public String UnitPrice;//建议单价（decimal(18,2)）
    public String BarCode;// 条形码（50）
    public String Specification;//规格（50）
    public String StockModel;//型号（50）
    public String Remark;//备注（1000）
    
    public String getCode() {
        return Code;
    }
    public void setCode(String code) {
        Code = code;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getMaterialModelCode() {
        return MaterialModelCode;
    }
    public void setMaterialModelCode(String materialModelCode) {
        MaterialModelCode = materialModelCode;
    }
    public String getUnitPrice() {
        return UnitPrice;
    }
    public void setUnitPrice(String unitPrice) {
        UnitPrice = unitPrice;
    }
    public String getBarCode() {
        return BarCode;
    }
    public void setBarCode(String barCode) {
        BarCode = barCode;
    }
    public String getSpecification() {
        return Specification;
    }
    public void setSpecification(String specification) {
        Specification = specification;
    }
    public String getStockModel() {
        return StockModel;
    }
    public void setStockModel(String stockModel) {
        StockModel = stockModel;
    }
    public String getRemark() {
        return Remark;
    }
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
