package com.rifaikuci.yeni.yerler.kesfetme.datas;

import android.media.Image;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static java.lang.Boolean.FALSE;

public class dataTur {
    @Expose
    @SerializedName("id") private int id;
    @Expose
    @SerializedName("turAd") private String turAd;
    @Expose
    @SerializedName("turDetay") private String turDetay;
    @Expose
    @SerializedName("turResim") private String turResim;
    @Expose
    @SerializedName("turEnlem") private Double turEnlem;
    @Expose
    @SerializedName("turBoylam") private Double turBoylam;
    @Expose
    @SerializedName("tur") private String tur;
    @Expose
    @SerializedName("durum") private String durum;

    public String getTurKayitTarih() {
        return turKayitTarih;
    }

    public void setTurKayitTarih(String turKayitTarih) {
        this.turKayitTarih = turKayitTarih;
    }

    @Expose
    @SerializedName("turKayitTarih") private String turKayitTarih;
    @Expose
    @SerializedName("idKullanici") private int idKullanici;
    @Expose
    @SerializedName("isSelected") private boolean isSelected = false;
    @Expose
    @SerializedName("success") private boolean success = false;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public dataTur(String turAd, String turDetay, String turResim, Double turEnlem, Double turBoylam, String tur, String durum) {
        this.turAd = turAd;
        this.turDetay = turDetay;
        this.turResim = turResim;
        this.turEnlem = turEnlem;
        this.turBoylam = turBoylam;
        this.tur = tur;
        this.durum = durum;
        this.isSelected =true;
    }

    public dataTur(int id, String turAd, String turDetay, String turResim, Double turEnlem, Double turBoylam, String tur, String turKayitTarih, boolean isSelected) {
        this.id = id;
        this.turAd = turAd;
        this.turDetay = turDetay;
        this.turResim = turResim;
        this.turEnlem = turEnlem;
        this.turBoylam = turBoylam;
        this.tur = tur;
        this.turKayitTarih = turKayitTarih;
        isSelected = true;
    }



    public dataTur(int id, String turAd, String turDetay, String turResim, Double turEnlem, Double turBoylam, String tur, boolean isSelected, int idKullanici) {
        this.turAd = turAd;
        this.turDetay = turDetay;
        this.turResim = turResim;
        this.turEnlem = turEnlem;
        this.turBoylam = turBoylam;
        this.tur = tur;
        this.isSelected = isSelected;
        this.id =id;
        this.idKullanici=idKullanici;
    }

    public void setTurAd(String turAd) {
        this.turAd = turAd;
    }

    public String getTurDetay() {
        return turDetay;
    }

    public void setTurDetay(String turDetay) {
        this.turDetay = turDetay;
    }

    public String getTurResim() {
        return turResim;
    }

    public void setTurResim(String turResim) {
        this.turResim = turResim;
    }

    public Double getTurEnlem() {
        return turEnlem;
    }

    public void setTurEnlem(Double turEnlem) {
        this.turEnlem = turEnlem;
    }

    public Double getTurBoylam() {
        return turBoylam;
    }

    public void setTurBoylam(Double turBoylam) {
        this.turBoylam = turBoylam;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getdurum() {
        return durum;
    }

    public void setdurum(String durum) {
        this.durum = durum;
    }

    public int getIdKullanici() {
        return idKullanici;
    }

    public void setIdKullanici(int idKullanici) {
        this.idKullanici = idKullanici;
    }

    public boolean isSelected() { return isSelected; }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getTurAd() {
        return turAd;
    }
}
