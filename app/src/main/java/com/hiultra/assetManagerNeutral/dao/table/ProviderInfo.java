package com.hiultra.assetManagerNeutral.dao.table;

import java.io.Serializable;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 供应商表
 * 
 * @author Tom
 * @date 2016年10月28日
 * @Time 下午4:10:21
 */
@Table(name = "ProviderInfo")
public class ProviderInfo extends BaseTable {
    
    public String Code;
    public String Name;
    /** 传真 */
    public String Fax;
    /** 邮编 */
    public String ZipCode;
    /** 联系人 */
    public String Contacts;
    /** 联系电话 */
    public String TelPhone;
    /** 地址 */
    public String Address;
    /** 税号 */
    public String TaxID;
    /** 开户银行 */
    public String Bank;
    /** 开户行账号 */
    public String BankAccount;
    /** 电子邮箱 */
    public String Email;
    /** 供应商类型 */
    public String ProviderType;
    public String Remark;
    
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
    
    public String getFax() {
        return Fax;
    }
    
    public void setFax(String fax) {
        Fax = fax;
    }
    
    public String getZipCode() {
        return ZipCode;
    }
    
    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }
    
    public String getContacts() {
        return Contacts;
    }
    
    public void setContacts(String contacts) {
        Contacts = contacts;
    }
    
    public String getTelPhone() {
        return TelPhone;
    }
    
    public void setTelPhone(String telPhone) {
        TelPhone = telPhone;
    }
    
    public String getAddress() {
        return Address;
    }
    
    public void setAddress(String address) {
        Address = address;
    }
    
    public String getTaxID() {
        return TaxID;
    }
    
    public void setTaxID(String taxID) {
        TaxID = taxID;
    }
    
    public String getBank() {
        return Bank;
    }
    
    public void setBank(String bank) {
        Bank = bank;
    }
    
    public String getBankAccount() {
        return BankAccount;
    }
    
    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }
    
    public String getEmail() {
        return Email;
    }
    
    public void setEmail(String email) {
        Email = email;
    }
    
    public String getProviderType() {
        return ProviderType;
    }
    
    public void setProviderType(String providerType) {
        ProviderType = providerType;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
}
