package com.hiultra.assetManagerNeutral.dao.table;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 用户信息表
 *
 */
@Table(name = "UserInfo")
public class UserInfo extends BaseTable {
    public String Code; // 编码
    public String Name;// 名称
    public String LoginName;// 登录名
    public String Password;// 登录密码
    public String RoleCode;// 角色编码
    public String PositionCode;// 职位编码
    public String Sex;
    public String Birthday;
    /** 是否启用，启用、禁用 */
    public String Status;
    /** 移动电话 */
    public String TelPhone;
    public String Fax;
    public String Email;
    /** 办公电话 */
    public String OfficePhone;
    public String DataPower;// 查看数据权限
    public String DepartmentCode;// 部门编码
    public String Remark;
    /** 用户登录，开始时间 */
    public String UseDate;
    /** 用户登录，截止时间 */
    public String EndDate;
    
    public String getSex() {
        return Sex;
    }
    
    public void setSex(String sex) {
        Sex = sex;
    }
    
    public String getBirthday() {
        return Birthday;
    }
    
    public void setBirthday(String birthday) {
        Birthday = birthday;
    }
    
    public String getStatus() {
        return Status;
    }
    
    public void setStatus(String status) {
        Status = status;
    }
    
    public String getTelPhone() {
        return TelPhone;
    }
    
    public void setTelPhone(String telPhone) {
        TelPhone = telPhone;
    }
    
    public String getFax() {
        return Fax;
    }
    
    public void setFax(String fax) {
        Fax = fax;
    }
    
    public String getEmail() {
        return Email;
    }
    
    public void setEmail(String email) {
        Email = email;
    }
    
    public String getOfficePhone() {
        return OfficePhone;
    }
    
    public void setOfficePhone(String officePhone) {
        OfficePhone = officePhone;
    }
    
    public String getRemark() {
        return Remark;
    }
    
    public void setRemark(String remark) {
        Remark = remark;
    }
    
    public String getUseDate() {
        return UseDate;
    }
    
    public void setUseDate(String useDate) {
        UseDate = useDate;
    }
    
    public String getEndDate() {
        return EndDate;
    }
    
    public void setEndDate(String endDate) {
        EndDate = endDate;
    }
    
    public String getCode() {
        return Code;
    }
    
    public void setCode(String code) {
        Code = code;
    }
    
    public String getRoleCode() {
        return RoleCode;
    }
    
    public void setRoleCode(String roleCode) {
        RoleCode = roleCode;
    }
    
    public String getName() {
        return Name;
    }
    
    public void setName(String name) {
        Name = name;
    }
    
    public String getLoginName() {
        return LoginName;
    }
    
    public void setLoginName(String loginName) {
        LoginName = loginName;
    }
    
    public String getPassword() {
        return Password;
    }
    
    public void setPassword(String password) {
        Password = password;
    }
    
    public String getPositionCode() {
        return PositionCode;
    }
    
    public void setPositionCode(String positionCode) {
        PositionCode = positionCode;
    }
    
    public String getDataPower() {
        return DataPower;
    }
    
    public void setDataPower(String dataPower) {
        DataPower = dataPower;
    }
    
    public String getDepartmentCode() {
        return DepartmentCode;
    }
    
    public void setDepartmentCode(String departmentCode) {
        DepartmentCode = departmentCode;
    }
    
    @Override
    public String toString() {
        return Name;
    }
}
