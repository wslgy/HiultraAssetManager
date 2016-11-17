package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 资产报废主表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午3:04:02
 */
@Table(name = "Asset_ScrapInfo01")
public class Asset_ScrapInfo01 extends BatchTable {
    
    public String ScrapDate;
    public String Remark;
    
    public String getScrapDate() {
        return ScrapDate;
    }
    
    public void setScrapDate(String scrapDate) {
        ScrapDate = scrapDate;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
