package com.rifaikuci.yeni.yerler.kesfetme.datas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class dataKullanici {

    @Expose
    @SerializedName("id") private int id;
    @Expose
    @SerializedName("adSoyad") private String adSoyad;
    @Expose
    @SerializedName("mail") private String mail;
    @Expose
    @SerializedName("sifre") private String sifre;
    @Expose
    @SerializedName("durum") private String durum;
    @Expose
    @SerializedName("message") private String message;
    @Expose
    @SerializedName("resim") private String resim;
    @Expose
    @SerializedName("success") private boolean success = false;

    public boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public dataKullanici(int id, String adSoyad, String mail, String resim) {
        this.id = id;
        this.adSoyad = adSoyad;
        this.mail = mail;
        this.resim = resim;
    }

    public String getResim() {
        return resim;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
}
