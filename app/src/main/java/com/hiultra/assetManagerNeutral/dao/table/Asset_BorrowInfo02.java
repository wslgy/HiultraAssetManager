package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 资产借用从表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午2:31:54
 */
@Table(name="Asset_BorrowInfo02")
public class Asset_BorrowInfo02 extends BatchTable {
    
    public String MaterialCode;
    /** 归还日期 */
    public String BackDate;
    public String Remark;

    public String getMaterialCode() {
        return MaterialCode;
    }

    public void setMaterialCode(String materialCode) {
        MaterialCode = materialCode;
    }

    public String getBackDate() {
        return BackDate;
    }
    
    public void setBackDate(String backDate) {
        BackDate = backDate;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
