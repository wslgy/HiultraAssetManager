package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * 资产报废从表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午3:03:37
 */
@Table(name = "Asset_ScrapInfo02")
public class Asset_ScrapInfo02 extends BatchTable {
    
    public String MaterialCode;
    public String DepartmentCode;
    public String UserCode;
    public String Remark;
    
    @Transient
    public transient String name;
    @Transient
    public transient String departmentName;
    @Transient
    public transient String modelName;
    @Transient
    public transient String specification;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getMaterialCode() {
        return MaterialCode;
    }
    
    public void setMaterialCode(String materialCode) {
        MaterialCode = materialCode;
    }
    
    public String getDepartmentCode() {
        return DepartmentCode;
    }
    
    public void setDepartmentCode(String departmentCode) {
        DepartmentCode = departmentCode;
    }
    
    public String getUserCode() {
        return UserCode;
    }
    
    public void setUserCode(String userCode) {
        UserCode = userCode;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
