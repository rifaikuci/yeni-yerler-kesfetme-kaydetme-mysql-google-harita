package com.rifaikuci.yeni.yerler.kesfetme.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //GET-POST işlemlerinin yapılacağı alan
    //Gradle dosyasına 2 adet impletion işlemi yapılır.

    private static  final  String BASE_URL = "https://rifaikuci.com/lisans/";
    private static Retrofit retrofit;

    public static Retrofit getApiClient(){

        if(retrofit ==null){
            retrofit  = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
