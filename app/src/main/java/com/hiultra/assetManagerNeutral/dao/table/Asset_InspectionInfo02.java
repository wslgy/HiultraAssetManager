package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * 资产巡检从表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午2:48:48
 */
@Table(name="Asset_InspectionInfo02")
public class Asset_InspectionInfo02 extends BatchTable{
    public String MaterialCode;
    public String Result;
    public String Contents;
    
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
    public String getMaterialCode() {
        return MaterialCode;
    }
    public void setMaterialCode(String materialCode) {
        MaterialCode = materialCode;
    }
    public String getResult() {
        return Result;
    }
    public void setResult(String result) {
        Result = result;
    }
    public String getContents() {
        return Contents;
    }
    public void setContents(String contents) {
        Contents = contents;
    }

    
}
