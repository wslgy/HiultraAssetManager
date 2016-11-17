package com.hiultra.assetManagerNeutral.dao.table;


/**
 * 所有带BatchNumber字段的表
 * @author Tom
 * @date 2016年11月5日
 * @Time 下午2:30:56
 */
public abstract class BatchTable extends BaseTable {
    
    public String BatchNumber;
    
    public String getBatchNumber() {
        return BatchNumber;
    }
    
    public void setBatchNumber(String batchNumber) {
        BatchNumber = batchNumber;
    }
}
