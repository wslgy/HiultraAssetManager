package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * 资产维修从表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午3:00:12
 */
@Table(name = "Asset_RepairInfo02")
public class Asset_RepairInfo02 extends BatchTable {
    
    public String MaterialCode;// 资产编码
    public double RepairCost;// 维修费用
    public String Contents;// 维修内容
    public transient String IsRepair;// 是否修复
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

    public String getMaterialCode() {
        return MaterialCode;
    }
    
    public void setMaterialCode(String materialCode) {
        MaterialCode = materialCode;
    }
    
    public double getRepairCost() {
        return RepairCost;
    }

    public void setRepairCost(double repairCost) {
        RepairCost = repairCost;
    }

    public String getContents() {
        return Contents;
    }
    
    public void setContents(String contents) {
        Contents = contents;
    }
    
    public String getIsRepair() {
        return IsRepair;
    }
    
    public void setIsRepair(String isRepair) {
        IsRepair = isRepair;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
