package com.sulan.webshell.entities;

/**
 * Created by huangsx on 2016/11/8.
 */

public class Sys_User_LoginSystem_Ext_Resp extends BaseResponse{


    /**
     * SystemID : SYS_0001
     * UserName : admin
     * UserRealName : admin
     * Userpass : 21232f297a57a5a743894a0e4a801fc3
     * UserPhone : 15195855975
     * Position : 数据库管理员
     * Role : RO_0001
     * Department : DEP_0001
     * DisplayOrder : 1
     * Tag :
     * LoginSystemId :
     * TokenString : B+4UPfXuujUjvxopmBL2/D6NrzW/a+twxPmRsCzKiTso/4aefualsKW6GIvNwEZnkz1PCNgNtPni+4tMy+83nOpKpwfZE+vz
     * LoginFlag : true
     * Sys_Department : null
     * Sys_Role : null
     * Sys_System : {"SystemID":"SYS_0001","SystemName":"金佛山水水库预报系统","SystemSubName":"Online Automatic Hydrological Forecasting System For Hydrological Data","SystemLogo":"http://ico.ooopic.com/ajax/iconpng/?id=273683.png","SystemFirstPage":"http://192.168.0.203:5002/Map/Map_Gis_Realtime.aspx","SystemImage":"http://ico.ooopic.com/ajax/iconpng/?id=273683.png","Manufacturer":"江苏水迅通信息科技有限公司","ManufacturerLogo":"http://ico.ooopic.com/ajax/iconpng/?id=273683.png","ManufacturerTel":"025-68223069","ManufacturerQQ":"1252387041","ManufacturerEmail":"025-68223069","ManufacturerWebsite":"www.miuvo.com.cn","ProductVersion":"1","ClientCompany":"1","ClientCompanyLogo":"1","ClientTel":"1","ClientEMail":"1","ClientWebsite":"1","StructureUpdataTime":"0001-01-01T00:00:00","SystemKey":"","EnablePlatform_Win":false,"EnablePlatform_Web":false,"EnablePlatform_Phone":true,"SystemNum":0}
     */

    private String SystemID;
    private String UserName;
    private String UserRealName;
    private String Userpass;
    private String UserPhone;
    private String Position;
    private String Role;
    private String Department;
    private int DisplayOrder;
    private String Tag;
    private String LoginSystemId;
    private String TokenString;
    private boolean LoginFlag;
    private Object Sys_Department;
    private Object Sys_Role;
    /**
     * SystemID : SYS_0001
     * SystemName : 金佛山水水库预报系统
     * SystemSubName : Online Automatic Hydrological Forecasting System For Hydrological Data
     * SystemLogo : http://ico.ooopic.com/ajax/iconpng/?id=273683.png
     * SystemFirstPage : http://192.168.0.203:5002/Map/Map_Gis_Realtime.aspx
     * SystemImage : http://ico.ooopic.com/ajax/iconpng/?id=273683.png
     * Manufacturer : 江苏水迅通信息科技有限公司
     * ManufacturerLogo : http://ico.ooopic.com/ajax/iconpng/?id=273683.png
     * ManufacturerTel : 025-68223069
     * ManufacturerQQ : 1252387041
     * ManufacturerEmail : 025-68223069
     * ManufacturerWebsite : www.miuvo.com.cn
     * ProductVersion : 1
     * ClientCompany : 1
     * ClientCompanyLogo : 1
     * ClientTel : 1
     * ClientEMail : 1
     * ClientWebsite : 1
     * StructureUpdataTime : 0001-01-01T00:00:00
     * SystemKey :
     * EnablePlatform_Win : false
     * EnablePlatform_Web : false
     * EnablePlatform_Phone : true
     * SystemNum : 0
     */

    private SysSystemEntity Sys_System;

    public String getSystemID() {
        return SystemID;
    }

    public void setSystemID(String SystemID) {
        this.SystemID = SystemID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserRealName() {
        return UserRealName;
    }

    public void setUserRealName(String UserRealName) {
        this.UserRealName = UserRealName;
    }

    public String getUserpass() {
        return Userpass;
    }

    public void setUserpass(String Userpass) {
        this.Userpass = Userpass;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String UserPhone) {
        this.UserPhone = UserPhone;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public int getDisplayOrder() {
        return DisplayOrder;
    }

    public void setDisplayOrder(int DisplayOrder) {
        this.DisplayOrder = DisplayOrder;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String Tag) {
        this.Tag = Tag;
    }

    public String getLoginSystemId() {
        return LoginSystemId;
    }

    public void setLoginSystemId(String LoginSystemId) {
        this.LoginSystemId = LoginSystemId;
    }

    public String getTokenString() {
        return TokenString;
    }

    public void setTokenString(String TokenString) {
        this.TokenString = TokenString;
    }

    public boolean isLoginFlag() {
        return LoginFlag;
    }

    public void setLoginFlag(boolean LoginFlag) {
        this.LoginFlag = LoginFlag;
    }

    public Object getSys_Department() {
        return Sys_Department;
    }

    public void setSys_Department(Object Sys_Department) {
        this.Sys_Department = Sys_Department;
    }

    public Object getSys_Role() {
        return Sys_Role;
    }

    public void setSys_Role(Object Sys_Role) {
        this.Sys_Role = Sys_Role;
    }

    public SysSystemEntity getSys_System() {
        return Sys_System;
    }

    public void setSys_System(SysSystemEntity Sys_System) {
        this.Sys_System = Sys_System;
    }

    public static class SysSystemEntity {
        private String SystemID;
        private String SystemName;
        private String SystemSubName;
        private String SystemLogo;
        private String SystemFirstPage;
        private String SystemImage;
        private String Manufacturer;
        private String ManufacturerLogo;
        private String ManufacturerTel;
        private String ManufacturerQQ;
        private String ManufacturerEmail;
        private String ManufacturerWebsite;
        private String ProductVersion;
        private String ClientCompany;
        private String ClientCompanyLogo;
        private String ClientTel;
        private String ClientEMail;
        private String ClientWebsite;
        private String StructureUpdataTime;
        private String SystemKey;
        private boolean EnablePlatform_Win;
        private boolean EnablePlatform_Web;
        private boolean EnablePlatform_Phone;
        private int SystemNum;

        public String getSystemID() {
            return SystemID;
        }

        public void setSystemID(String SystemID) {
            this.SystemID = SystemID;
        }

        public String getSystemName() {
            return SystemName;
        }

        public void setSystemName(String SystemName) {
            this.SystemName = SystemName;
        }

        public String getSystemSubName() {
            return SystemSubName;
        }

        public void setSystemSubName(String SystemSubName) {
            this.SystemSubName = SystemSubName;
        }

        public String getSystemLogo() {
            return SystemLogo;
        }

        public void setSystemLogo(String SystemLogo) {
            this.SystemLogo = SystemLogo;
        }

        public String getSystemFirstPage() {
            return SystemFirstPage;
        }

        public void setSystemFirstPage(String SystemFirstPage) {
            this.SystemFirstPage = SystemFirstPage;
        }

        public String getSystemImage() {
            return SystemImage;
        }

        public void setSystemImage(String SystemImage) {
            this.SystemImage = SystemImage;
        }

        public String getManufacturer() {
            return Manufacturer;
        }

        public void setManufacturer(String Manufacturer) {
            this.Manufacturer = Manufacturer;
        }

        public String getManufacturerLogo() {
            return ManufacturerLogo;
        }

        public void setManufacturerLogo(String ManufacturerLogo) {
            this.ManufacturerLogo = ManufacturerLogo;
        }

        public String getManufacturerTel() {
            return ManufacturerTel;
        }

        public void setManufacturerTel(String ManufacturerTel) {
            this.ManufacturerTel = ManufacturerTel;
        }

        public String getManufacturerQQ() {
            return ManufacturerQQ;
        }

        public void setManufacturerQQ(String ManufacturerQQ) {
            this.ManufacturerQQ = ManufacturerQQ;
        }

        public String getManufacturerEmail() {
            return ManufacturerEmail;
        }

        public void setManufacturerEmail(String ManufacturerEmail) {
            this.ManufacturerEmail = ManufacturerEmail;
        }

        public String getManufacturerWebsite() {
            return ManufacturerWebsite;
        }

        public void setManufacturerWebsite(String ManufacturerWebsite) {
            this.ManufacturerWebsite = ManufacturerWebsite;
        }

        public String getProductVersion() {
            return ProductVersion;
        }

        public void setProductVersion(String ProductVersion) {
            this.ProductVersion = ProductVersion;
        }

        public String getClientCompany() {
            return ClientCompany;
        }

        public void setClientCompany(String ClientCompany) {
            this.ClientCompany = ClientCompany;
        }

        public String getClientCompanyLogo() {
            return ClientCompanyLogo;
        }

        public void setClientCompanyLogo(String ClientCompanyLogo) {
            this.ClientCompanyLogo = ClientCompanyLogo;
        }

        public String getClientTel() {
            return ClientTel;
        }

        public void setClientTel(String ClientTel) {
            this.ClientTel = ClientTel;
        }

        public String getClientEMail() {
            return ClientEMail;
        }

        public void setClientEMail(String ClientEMail) {
            this.ClientEMail = ClientEMail;
        }

        public String getClientWebsite() {
            return ClientWebsite;
        }

        public void setClientWebsite(String ClientWebsite) {
            this.ClientWebsite = ClientWebsite;
        }

        public String getStructureUpdataTime() {
            return StructureUpdataTime;
        }

        public void setStructureUpdataTime(String StructureUpdataTime) {
            this.StructureUpdataTime = StructureUpdataTime;
        }

        public String getSystemKey() {
            return SystemKey;
        }

        public void setSystemKey(String SystemKey) {
            this.SystemKey = SystemKey;
        }

        public boolean isEnablePlatform_Win() {
            return EnablePlatform_Win;
        }

        public void setEnablePlatform_Win(boolean EnablePlatform_Win) {
            this.EnablePlatform_Win = EnablePlatform_Win;
        }

        public boolean isEnablePlatform_Web() {
            return EnablePlatform_Web;
        }

        public void setEnablePlatform_Web(boolean EnablePlatform_Web) {
            this.EnablePlatform_Web = EnablePlatform_Web;
        }

        public boolean isEnablePlatform_Phone() {
            return EnablePlatform_Phone;
        }

        public void setEnablePlatform_Phone(boolean EnablePlatform_Phone) {
            this.EnablePlatform_Phone = EnablePlatform_Phone;
        }

        public int getSystemNum() {
            return SystemNum;
        }

        public void setSystemNum(int SystemNum) {
            this.SystemNum = SystemNum;
        }
    }
}
