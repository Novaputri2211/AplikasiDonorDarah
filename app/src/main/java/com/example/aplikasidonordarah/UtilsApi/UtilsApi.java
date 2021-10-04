package com.example.aplikasidonordarah.UtilsApi;

public class UtilsApi {
//    public static final String baseUrl = "http://192.168.100.82/api_donor/";
    public static final String baseUrl = "http://192.168.100.206/TugasAkhirDonorDarah/api_donor/"; //localhost

//  public static final String baseUrl = "http://18.234.162.60/api_donor/"; //AWS Server

    public static ApiInterface getApiService(){
        return RetrofitClient.getRetrofit(baseUrl).create(ApiInterface.class);
    }
}
