package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

@Table(name = "SystemArgumentType")
public class SystemArgumentType extends BaseTable {
    
    public int TypeID;
    public String TypeName;
    
    public int getTypeID() {
        return TypeID;
    }
    
    public void setTypeID(int typeID) {
        TypeID = typeID;
    }
    
    public String getTypeName() {
        return TypeName;
    }
    
    public void setTypeName(String typeName) {
        TypeName = typeName;
    }
    
}
