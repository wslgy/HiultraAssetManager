package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.NotNull;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;
import com.lidroid.xutils.db.annotation.Unique;

/**
 * 资产总表
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午3:09:13
 */
@Table(name="AssetMaterialInfo")
public class AssetMaterialInfo extends BaseTable {

    public String AssetType;// 资产类型（主资产，附资产）
    public String Name;// 名称
    @Unique@NotNull
    public String Code;// 编码
    /** 原资产编码 */
    public String OriginalCode;
    public String BarCode;// 标签号
    public String MaterialModelCode;// 资产分类编码
    public String Specification;// 规格
    public String AssetModel;// 型号
    public String Unit;// 计量单位
    /** 产地 */
    public String ProduceAddress;
    /** 购置方式 */
    public int PurchaseType;
    /** 使用状态 */
    public int UseStatus;
    /** 资产原值 decimal(18, 2)保留小数点后两位 */
    public String AssetValue;
    /** 资产净值 */
    public double NetWorth;
    /** 使用年限 */
    public int UseYears;
    /** 折旧方式 */
    public String DepreciationType;
    /** 月折旧 */
    public String MonthDepreciation;
    /** 采购时间 */
    public String PurchaseDate;
    /** 生产日期 */
    public String ProduceDate;
    /** 入账日期 */
    public String RecordDate;
    /** 启用日期 */
    public String EnableDate;
    /** 供应商Code */
    public String ProviderCode;
    /** 生产商Code */
    public String ManufacturerCode;
    /** 使用部门编码 */
    public String DepartmentCode;
    public String UserCode;// 使用人编码
    public String StorageDepartmentCode;// 管理部门编码
    public String StorageCode;// 管理人编码
    /** 附件ID */
    public String AttachmentKey;
    /** 资产用途 */
    public int AssetUse;
    /** 品牌 */
    public String Brand;
    /** 采购登记号 */
    public String PurchaseNo;
    public String MaterialType;
    /** 额定功率 */
    public String RatedPower;
    /** 额定电压 */
    public String RatedVoltage;
    /** 转速 */
    public String RotatingSpeed;
    /** 重量 */
    public String Weight;
    /** 绝缘等级 */
    public String InsulationClass;
    /** 防护等级 */
    public String ProtectionClass;
    /** 机座号 */
    public String Frame;
    /** 配套设备名称 */
    public String GroupedEquipment;
    /** 安全状况 */
    public String Security;
    /** 井号 */
    public String Hashtag;
    /** 安装地点 */
    public String InstallLocation;
    /** 主资产编码 */
    public String MainAssetCode;
    /** 资产状态（报废、报停、维修中等）（调拨中、维修中、借用等） */
    public String AssetStatus;// 资产状态
    public String WarehouseCode;// 存放地点编码

    /** 标记该资产是否曾通过其他模块修改过数据,比如调拨/报废,默认为0,修改过后,标志位1 */
    public transient int isChanged;
    
    @Transient
    public transient String DepartmentName;
    @Transient
    public transient String UserName;
    @Transient
    public transient String StorageDepartmentName;
    @Transient
    public transient String StorageName;
    @Transient
    public transient String WarehouseName;
    @Transient
    public transient String modelName;

//    public String Parameter1;// AHU系统
//    public String Parameter2;// system name
//    public String Parameter3;// Devices Instance
//    public String Parameter4;// MS MAC地址
//    public String Parameter5;// BOX分类
//    public String Parameter6;// BOX厂家
//    public String Parameter7;// CMH CLG Min flow
//    public String Parameter8;// CMH CLG Max flow
//    public String Parameter9;// HTG Min
//    public String Parameter10;// HTG Max
//    public String Parameter11;// Box入口面积
//    public String Parameter12;// Flow Efficient
//    public String Parameter13;// CLG Max flow
//    public String Parameter14;// CLG Min flow
//    public String Parameter15;// HTG Min flow
//    public String Parameter16;// HTG Max flow
//    public String Parameter17;// 控制器型号
//    public String Parameter18;// 控制器厂家

    public int getIsChanged() {
        return isChanged;
    }

    public void setIsChanged(int isChanged) {
        this.isChanged = isChanged;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getStorageDepartmentName() {
        return StorageDepartmentName;
    }

    public void setStorageDepartmentName(String storageDepartmentName) {
        StorageDepartmentName = storageDepartmentName;
    }

    public String getStorageName() {
        return StorageName;
    }

    public void setStorageName(String storageName) {
        StorageName = storageName;
    }

    public String getWarehouseName() {
        return WarehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        WarehouseName = warehouseName;
    }

    public String getPurchaseDate() {
        return PurchaseDate;
    }

    public String getOriginalCode() {
        return OriginalCode;
    }

    public void setOriginalCode(String originalCode) {
        OriginalCode = originalCode;
    }

    public String getProduceAddress() {
        return ProduceAddress;
    }

    public void setProduceAddress(String produceAddress) {
        ProduceAddress = produceAddress;
    }

    public int getPurchaseType() {
        return PurchaseType;
    }

    public void setPurchaseType(int purchaseType) {
        PurchaseType = purchaseType;
    }

    public int getUseStatus() {
        return UseStatus;
    }

    public void setUseStatus(int useStatus) {
        UseStatus = useStatus;
    }

    public double getNetWorth() {
        return NetWorth;
    }

    public void setNetWorth(double netWorth) {
        NetWorth = netWorth;
    }

    public int getUseYears() {
        return UseYears;
    }

    public void setUseYears(int useYears) {
        UseYears = useYears;
    }

    public String getDepreciationType() {
        return DepreciationType;
    }

    public void setDepreciationType(String depreciationType) {
        DepreciationType = depreciationType;
    }

    public String getMonthDepreciation() {
        return MonthDepreciation;
    }

    public void setMonthDepreciation(String monthDepreciation) {
        MonthDepreciation = monthDepreciation;
    }

    public String getProduceDate() {
        return ProduceDate;
    }

    public void setProduceDate(String produceDate) {
        ProduceDate = produceDate;
    }

    public String getRecordDate() {
        return RecordDate;
    }

    public void setRecordDate(String recordDate) {
        RecordDate = recordDate;
    }

    public String getEnableDate() {
        return EnableDate;
    }

    public void setEnableDate(String enableDate) {
        EnableDate = enableDate;
    }

    public String getProviderCode() {
        return ProviderCode;
    }

    public void setProviderCode(String providerCode) {
        ProviderCode = providerCode;
    }

    public String getManufacturerCode() {
        return ManufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        ManufacturerCode = manufacturerCode;
    }

    public String getAttachmentKey() {
        return AttachmentKey;
    }

    public void setAttachmentKey(String attachmentKey) {
        AttachmentKey = attachmentKey;
    }

    public int getAssetUse() {
        return AssetUse;
    }

    public void setAssetUse(int assetUse) {
        AssetUse = assetUse;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getPurchaseNo() {
        return PurchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        PurchaseNo = purchaseNo;
    }

    public String getMaterialType() {
        return MaterialType;
    }

    public void setMaterialType(String materialType) {
        MaterialType = materialType;
    }

    public String getRatedPower() {
        return RatedPower;
    }

    public void setRatedPower(String ratedPower) {
        RatedPower = ratedPower;
    }

    public String getRatedVoltage() {
        return RatedVoltage;
    }

    public void setRatedVoltage(String ratedVoltage) {
        RatedVoltage = ratedVoltage;
    }

    public String getRotatingSpeed() {
        return RotatingSpeed;
    }

    public void setRotatingSpeed(String rotatingSpeed) {
        RotatingSpeed = rotatingSpeed;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getInsulationClass() {
        return InsulationClass;
    }

    public void setInsulationClass(String insulationClass) {
        InsulationClass = insulationClass;
    }

    public String getProtectionClass() {
        return ProtectionClass;
    }

    public void setProtectionClass(String protectionClass) {
        ProtectionClass = protectionClass;
    }

    public String getFrame() {
        return Frame;
    }

    public void setFrame(String frame) {
        Frame = frame;
    }

    public String getGroupedEquipment() {
        return GroupedEquipment;
    }

    public void setGroupedEquipment(String groupedEquipment) {
        GroupedEquipment = groupedEquipment;
    }

    public String getSecurity() {
        return Security;
    }

    public void setSecurity(String security) {
        Security = security;
    }

    public String getHashtag() {
        return Hashtag;
    }

    public void setHashtag(String hashtag) {
        Hashtag = hashtag;
    }

    public String getInstallLocation() {
        return InstallLocation;
    }

    public void setInstallLocation(String installLocation) {
        InstallLocation = installLocation;
    }

    public String getMainAssetCode() {
        return MainAssetCode;
    }

    public void setMainAssetCode(String mainAssetCode) {
        MainAssetCode = mainAssetCode;
    }

    public void setPurchaseDate(String purchaseDate) {
        PurchaseDate = purchaseDate;
    }

    public String getAssetType() {
        return AssetType;
    }

    public void setAssetType(String assetType) {
        AssetType = assetType;
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

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getMaterialModelCode() {
        return MaterialModelCode;
    }

    public void setMaterialModelCode(String materialModelCode) {
        MaterialModelCode = materialModelCode;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }

    public String getAssetModel() {
        return AssetModel;
    }

    public void setAssetModel(String assetModel) {
        AssetModel = assetModel;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getStorageDepartmentCode() {
        return StorageDepartmentCode;
    }

    public void setStorageDepartmentCode(String storageDepartmentCode) {
        StorageDepartmentCode = storageDepartmentCode;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String userCode) {
        UserCode = userCode;
    }

    public String getStorageCode() {
        return StorageCode;
    }

    public void setStorageCode(String storageCode) {
        StorageCode = storageCode;
    }

    public String getAssetValue() {
        return AssetValue;
    }

    public void setAssetValue(String assetValue) {
        AssetValue = assetValue;
    }

    public String getDepartmentCode() {
        return DepartmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        DepartmentCode = departmentCode;
    }

    public String getAssetStatus() {
        return AssetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        AssetStatus = assetStatus;
    }

    public String getWarehouseCode() {
        return WarehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        WarehouseCode = warehouseCode;
    }

    @Override
    public String toString() {
        return Name;
    }
}
