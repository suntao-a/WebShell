package com.sulan.webshell.entities;

import java.util.List;

/**
 * Created by huangsx on 2016/11/8.
 */

public class Sys_System_GetByPlatform_Ext_Resp extends BaseResponse {

    /**
     * SystemID : SYS_0002
     * SystemName : 平塘县农田水利信息化建设监测系统
     * SystemSubName : Demonstration system for Water Affairs Of MIUVO Automotive NanJing Co.
     * SystemLogo : MAP.png
     * SystemFirstPage : http://192.168.0.203:5002/Map/Map_Gis_Realtime.aspx
     * SystemImage : MAP.png
     * Manufacturer : 江苏水迅通信息科技有限公司
     * ManufacturerLogo : MAP.png
     * ManufacturerTel : 12345678
     * ManufacturerQQ : 123456
     * ManufacturerEmail : 123
     * ManufacturerWebsite : a
     * ProductVersion : a
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

    private List<ListEntity> list;

    public List<ListEntity> getList() {
        return list;
    }

    public int findIndex(ListEntity entity) {
        for (ListEntity e : list) {
            if (e.getSystemID().equals(entity.getSystemID())) {
                return list.indexOf(e);
            }
        }
        return -1;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public static class ListEntity {
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

        @Override
        public String toString() {
            return getSystemName();
        }
    }
}
