package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 物品类别表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:52:05
 */
@Table(name="StockModelInfo")
public class StockModelInfo extends BaseTable {
    
    private String Code;//分类编码（20）
    private String Name;//类别名称（50）
    private String ParentCode;//上级分类编码（20）
    private String Remark;//备注（1000）
    private String Levels;//层级标识（50）
    
    public String getCode() {
        return Code;
    }
    public void setCode(String code) {
        Code = code;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getParentCode() {
        return ParentCode;
    }
    public void setParentCode(String parentCode) {
        ParentCode = parentCode;
    }
    public String getRemark() {
        return Remark;
    }
    public void setRemark(String remark) {
        Remark = remark;
    }
    public String getLevels() {
        return Levels;
    }
    public void setLevels(String levels) {
        Levels = levels;
    }
    
    
}
