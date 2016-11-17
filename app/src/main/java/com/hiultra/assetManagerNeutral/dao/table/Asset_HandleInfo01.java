package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 资产处置主表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午2:43:16
 */
@Table(name="Asset_HandleInfo01")
public class Asset_HandleInfo01 extends BatchTable {
    
    public String HandleDate;//处置日期
    public String HandleDepartmentCode;//处置部门编码
    public int HandleType;//处置方式
    public String Income;//所得收入
    public String Costs;//所花成本
    public String Remark;//备注
    
    public String getHandleDate() {
        return HandleDate;
    }
    public void setHandleDate(String handleDate) {
        HandleDate = handleDate;
    }
    public String getHandleDepartmentCode() {
        return HandleDepartmentCode;
    }
    public void setHandleDepartmentCode(String handleDepartmentCode) {
        HandleDepartmentCode = handleDepartmentCode;
    }
    public int getHandleType() {
        return HandleType;
    }
    public void setHandleType(int handleType) {
        HandleType = handleType;
    }
    public String getIncome() {
        return Income;
    }
    public void setIncome(String income) {
        Income = income;
    }
    public String getCosts() {
        return Costs;
    }
    public void setCosts(String costs) {
        Costs = costs;
    }
    public String getRemark() {
        return Remark;
    }
    public void setRemark(String remark) {
        Remark = remark;
    }
    public String toString(){
        return BatchNumber;
    }
    
}
