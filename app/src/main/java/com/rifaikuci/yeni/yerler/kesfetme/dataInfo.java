package com.rifaikuci.yeni.yerler.kesfetme;

import android.media.Image;

import com.google.android.gms.maps.model.LatLng;

import static java.lang.Boolean.FALSE;

public class dataInfo {
    String name;
    String desc;
    int image;
    LatLng latLng;
    String tur;
    boolean isSelected = false;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public dataInfo(String name, String desc, int image, LatLng latLng, String tur) {
        this.name = name;
        this.desc = desc;
        this.image = image;
        this.latLng = latLng;
        this.tur = tur;
        this.isSelected=false;
    }
}
