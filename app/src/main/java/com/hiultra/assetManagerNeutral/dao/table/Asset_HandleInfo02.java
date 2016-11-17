package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * 资产处置从表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午2:43:29
 */
@Table(name = "Asset_HandleInfo02")
public class Asset_HandleInfo02 extends BatchTable {
    public String MaterialCode;// 资产编码
    public String Remark;// 备注
    
    
    @Transient
    public transient String name;
    @Transient
    public transient String modelName;
    @Transient
    public transient String specification;
    @Transient
    public transient String warehouseName;
    @Transient
    public transient String departmentName;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

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
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
