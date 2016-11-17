package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;

/**
 * 资产类别表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:08:24
 */
@Table(name = "MaterialModelInfo")
public class MaterialModelInfo extends BaseTable {
    
    public String Name;// 分类名称
    @Unique
    public String Code;// 分类编码
    public String ParentCode;// 上级编码
    public String Levels;// 层级标识
    
    public String Remark;
//    public String CompanyCode;
    @Override
    public String toString() {
        return Name;
    }
    public String getName() {
        return Name;
    }
    
    public void setName(String name) {
        Name = name;
    }
    
    public String getCode() {
        return Code;
    }
    
    public void setCode(String code) {
        Code = code;
    }
    
    public String getParentCode() {
        return ParentCode;
    }
    
    public void setParentID(String parentCode) {
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
