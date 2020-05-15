package com.rifaikuci.yeni.yerler.kesfetme.API;

import com.rifaikuci.yeni.yerler.kesfetme.datas.dataKullanici;
import com.rifaikuci.yeni.yerler.kesfetme.datas.dataTur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {


    //Post işlemi yapıldı, POST işlemi save.php dosyasına yapıldı,
    // post edilecek veriler parametre olarak gönderilir.
    @FormUrlEncoded
    @POST("save.php")
    Call<dataTur> saveData(
            @Field("turAd") String turAd,
            @Field("turDetay") String turDetay,
            @Field("turResim") String turResim,
            @Field("turEnlem") Double turEnlem,
            @Field("turBoylam") Double turBoylam,
            @Field("tur") String tur,
            @Field("durum") String durum
    );

    // Get işlemleri
    @GET("turler.php")
    Call<List<dataTur>> getTurler();


    //delete işlemleri
    @FormUrlEncoded
    @POST("deleteTur.php")
    Call<dataTur> deleteTur(
            @Field("id") int id,
            @Field("turResim") String turResim);


    // update işlemleri
    @FormUrlEncoded
    @POST("update.php")
    Call<dataTur> updateData(
            @Field("id") int id,
            @Field("turAd") String turAd,
            @Field("turDetay") String turDetay,
            @Field("turResim") String turResim,
            @Field("tur") String tur,
            @Field("durum") String durum
    );


    @FormUrlEncoded
    @POST("kullanici_kaydet.php")
    Call<dataKullanici> saveKullanici(
            @Field("adSoyad") String adSoyad,
            @Field("mail") String mail,
            @Field("sifre") String sifre,
            @Field("resim") String resim
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<dataKullanici> login(
            @Field("mail") String mail,
            @Field("sifre") String sifre
    );
}
