package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * 资产盘点从表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午2:39:52
 */
@Table(name = "Asset_CheckInfo02")
public class Asset_CheckInfo02 extends BatchTable {
    
    public String MaterialCode;// 资产编码
    public String CheckResult;// 盘点结果
    public String Remark;
    
    @Transient
    public String name;
    @Transient
    public String modelName;
    @Transient
    public String specification;
    @Transient
    public String warehouseName;
    @Transient
    public String departmentName;
    
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

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
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
