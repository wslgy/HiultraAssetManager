package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 资产盘点主表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午2:39:43
 */
@Table(name = "Asset_CheckInfo01")
public class Asset_CheckInfo01 extends BatchTable {
    
    public String CheckDate;// 盘点时间
    public String CheckTheme;// 盘点主题
    public String CheckStatus;// 盘点状态（录入，盘点中，结束）
    public String CheckDepartmentCode;// 盘点部门code
    public String Remark;//
    public String CheckEndDate;
    public String CheckUserCode;
    
    public String getCheckEndDate() {
        return CheckEndDate;
    }
    
    public void setCheckEndDate(String checkEndDate) {
        CheckEndDate = checkEndDate;
    }
    
    public String getCheckUserCode() {
        return CheckUserCode;
    }
    
    public void setCheckUserCode(String checkUserCode) {
        CheckUserCode = checkUserCode;
    }
    
    public String getCheckDate() {
        return CheckDate;
    }
    
    public void setCheckDate(String checkDate) {
        CheckDate = checkDate;
    }
    
    public String getCheckTheme() {
        return CheckTheme;
    }
    
    public void setCheckTheme(String checkTheme) {
        CheckTheme = checkTheme;
    }
    
    public String getCheckStatus() {
        return CheckStatus;
    }
    
    public void setCheckStatus(String checkStatus) {
        CheckStatus = checkStatus;
    }
    
    public String getCheckDepartmentCode() {
        return CheckDepartmentCode;
    }
    
    public void setCheckDepartmentCode(String checkDepartmentCode) {
        CheckDepartmentCode = checkDepartmentCode;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
    public String toString() {
        return CheckTheme;
    }
    
}
