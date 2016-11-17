package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 附件表，资产上传图片用的
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午3:57:53
 */
@Table(name = "AttachmentInfo")
public class AttachmentInfo extends BaseTable {
    
    /** 附件原名称 */
    public String OldName;
    /** 附件新名称 */
    public String NewName;
    /** 附件唯一编码 */
    public String AttachmentKey;
    public String AttClass;
    public int AttNum;
    
    public int getAttNum() {
        return AttNum;
    }
    
    public void setAttNum(int attNum) {
        AttNum = attNum;
    }
    
    public String getOldName() {
        return OldName;
    }
    
    public void setOldName(String oldName) {
        OldName = oldName;
    }
    
    public String getNewName() {
        return NewName;
    }
    
    public void setNewName(String newName) {
        NewName = newName;
    }
    
    public String getAttachmentKey() {
        return AttachmentKey;
    }
    
    public void setAttachmentKey(String attachmentKey) {
        AttachmentKey = attachmentKey;
    }
    
    public String getAttClass() {
        return AttClass;
    }
    
    public void setAttClass(String attClass) {
        AttClass = attClass;
    }
    
}
