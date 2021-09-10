package com.example.aplikasidonordarah.UtilsApi;

public class UtilsApi {
    public static final String baseUrl = "http://192.168.100.82/api_donor/";

    public static ApiInterface getApiService(){
        return RetrofitClient.getRetrofit(baseUrl).create(ApiInterface.class);
    }
}
