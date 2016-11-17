package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 资产巡检主表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午2:49:03
 */
@Table(name="Asset_InspectionInfo01")
public class Asset_InspectionInfo01 extends BatchTable{
    public String InspectionTheme;
    public String InspectionDate;
    public String InspectionStatus;
    public String DepartmentCode;
    public String UserCode;
    public String Remark;
    
    public String getInspectionTheme() {
        return InspectionTheme;
    }
    public void setInspectionTheme(String inspectionTheme) {
        InspectionTheme = inspectionTheme;
    }
    public String getInspectionDate() {
        return InspectionDate;
    }
    public void setInspectionDate(String inspectionDate) {
        InspectionDate = inspectionDate;
    }
    public String getInspectionStatus() {
        return InspectionStatus;
    }
    public void setInspectionStatus(String inspectionStatus) {
        InspectionStatus = inspectionStatus;
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
    public String toString() {
        return InspectionTheme;
    }
    
}
