package com.example.pennymead.model;

import com.google.gson.annotations.SerializedName;

public class AboutUsContent {
    @SerializedName("header")
    private String header;
    @SerializedName("about_pennymead")
    private String aboutPennymead;
    @SerializedName("about_image")
    private String aboutImage;
    @SerializedName("admin_info")
    private String adminInfo;
    @SerializedName("about_admin")
    private String aboutAdmin;
    @SerializedName("admin_image")
    private String adminImage;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getAboutPennymead() {
        return aboutPennymead;
    }

    public void setAboutPennymead(String aboutPennymead) {
        this.aboutPennymead = aboutPennymead;
    }

    public String getAboutImage() {
        return aboutImage;
    }

    public void setAboutImage(String aboutImage) {
        this.aboutImage = aboutImage;
    }

    public String getAdminInfo() {
        return adminInfo;
    }

    public void setAdminInfo(String adminInfo) {
        this.adminInfo = adminInfo;
    }

    public String getAboutAdmin() {
        return aboutAdmin;
    }

    public void setAboutAdmin(String aboutAdmin) {
        this.aboutAdmin = aboutAdmin;
    }

    public String getAdminImage() {
        return adminImage;
    }

    public void setAdminImage(String adminImage) {
        this.adminImage = adminImage;
    }

}
