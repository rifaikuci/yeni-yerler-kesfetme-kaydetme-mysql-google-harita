package com.rifaikuci.yeni.yerler.kesfetme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
//   public dataInfo(String turAd, String turDetay, String turResim, Double turEnlem, Double turBoylam, String tur, String turDurum) {
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


    @GET("turler.php")
    Call<List<dataInfo>> getTurler();

    @FormUrlEncoded
    @POST("deleteTur.php")
    Call<dataInfo>  deleteTur(
            @Field("idTur") int idTur,
            @Field("turResim") String turResim);


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
