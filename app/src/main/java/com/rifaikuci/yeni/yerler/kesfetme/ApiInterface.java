package com.rifaikuci.yeni.yerler.kesfetme;

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
    Call<dataInfo> saveData(
            @Field("turAd") String turAd,
            @Field("turDetay") String turDetay,
            @Field("turResim") String turResim,
            @Field("turEnlem") Double turEnlem,
            @Field("turBoylam") Double turBoylam,
            @Field("tur") String tur,
            @Field("turDurum") String turDurum
            );

    // Get işlemleri
    @GET("turler.php")
    Call<List<dataInfo>> getTurler();

    //delete işlemleri
    @FormUrlEncoded
    @POST("deleteTur.php")
    Call<dataInfo>  deleteTur(
            @Field("idTur") int idTur,
            @Field("turResim") String turResim);



    // update işlemleri
    @FormUrlEncoded
    @POST("update.php")
    Call<dataInfo> updateData(
            @Field("idTur") int idTur,
            @Field("turAd") String turAd,
            @Field("turDetay") String turDetay,
            @Field("turResim") String turResim,
            @Field("tur") String tur,
            @Field("turDurum") String turDurum
    );
}
