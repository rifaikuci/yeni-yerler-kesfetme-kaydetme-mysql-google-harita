package com.rifaikuci.yeni.yerler.kesfetme;

import android.media.Image;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static java.lang.Boolean.FALSE;

public class dataInfo {
    @Expose
    @SerializedName("idTur") private int idTur;
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
    @SerializedName("turDurum") private String turDurum;

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

    public dataInfo(String turAd, String turDetay, String turResim, Double turEnlem, Double turBoylam, String tur, String turDurum) {
        this.turAd = turAd;
        this.turDetay = turDetay;
        this.turResim = turResim;
        this.turEnlem = turEnlem;
        this.turBoylam = turBoylam;
        this.tur = tur;
        this.turDurum = turDurum;
        this.isSelected =true;
    }

    public dataInfo(int idTur, String turAd, String turDetay, String turResim, Double turEnlem, Double turBoylam, String tur, String turKayitTarih,boolean isSelected) {
        this.idTur = idTur;
        this.turAd = turAd;
        this.turDetay = turDetay;
        this.turResim = turResim;
        this.turEnlem = turEnlem;
        this.turBoylam = turBoylam;
        this.tur = tur;
        this.turKayitTarih = turKayitTarih;
        isSelected = true;
    }



    public dataInfo(int idTur,String turAd, String turDetay, String turResim, Double turEnlem, Double turBoylam, String tur, boolean isSelected,int idKullanici) {
        this.turAd = turAd;
        this.turDetay = turDetay;
        this.turResim = turResim;
        this.turEnlem = turEnlem;
        this.turBoylam = turBoylam;
        this.tur = tur;
        this.isSelected = isSelected;
        this.idTur =idTur;
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

    public String getTurDurum() {
        return turDurum;
    }

    public void setTurDurum(String turDurum) {
        this.turDurum = turDurum;
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

    public int getIdTur() {
        return idTur;
    }

    public void setIdTur(int idTur) {
        this.idTur = idTur;
    }

    public String getTurAd() {
        return turAd;
    }
}
