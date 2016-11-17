package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 资产维修主表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午3:00:38
 */
@Table(name = "Asset_RepairInfo01")
public class Asset_RepairInfo01 extends BatchTable {
    
    public String RepairDate;// 维修日期
    public String DepartmentCode;// 维修部门编码
    public String RepairUserCode;// 维修负责人编码
    public String RepairStatus;// 维修状态
    public String RepairEndDate;// 维修结束日期
    public String Remark;// 备注
    
    public String getRepairDate() {
        return RepairDate;
    }
    @Override
    public String toString() {
        return BatchNumber;
    }
    public void setRepairDate(String repairDate) {
        RepairDate = repairDate;
    }
    
    public String getDepartmentCode() {
        return DepartmentCode;
    }
    
    public void setDepartmentCode(String departmentCode) {
        DepartmentCode = departmentCode;
    }
    
    public String getRepairUserCode() {
        return RepairUserCode;
    }
    
    public void setRepairUserCode(String repairUserCode) {
        RepairUserCode = repairUserCode;
    }
    
    public String getRepairStatus() {
        return RepairStatus;
    }
    
    public void setRepairStatus(String repairStatus) {
        RepairStatus = repairStatus;
    }
    
    public String getRepairEndDate() {
        return RepairEndDate;
    }
    
    public void setRepairEndDate(String repairEndDate) {
        RepairEndDate = repairEndDate;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
