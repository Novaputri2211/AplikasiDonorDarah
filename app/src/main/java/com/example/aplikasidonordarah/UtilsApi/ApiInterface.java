package com.example.aplikasidonordarah.UtilsApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("user/register.php")
    Call<ResponseBody>register(@Field("nama") String nama,
                               @Field("email") String email,
                               @Field("jenis_kelamin") String jenis_kelamin,
                               @Field("username") String username,
                               @Field("password") String password
                               );

    @FormUrlEncoded
    @POST("user/login.php")
    Call<ResponseBody>login(@Field("username") String username,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("user/get_user.php")
    Call<ResponseBody>getUser(@Field("id_user") String iduser);

    @FormUrlEncoded
    @POST("donor/cek_pendonor.php")
    Call<ResponseBody>cekPendonor(@Field("id_user") String idUser);

    @FormUrlEncoded
    @POST("donor/tambah_pendonor.php")
    Call<ResponseBody>tambahPendonor(@Field("id_user") String id_user,
                                     @Field("nama_pendonor") String nama_pendonor,
                                     @Field("tempat_lahir") String tempat_lahir,
                                     @Field("tgl_lahir") String tgl_lahir,
                                     @Field("umur") String umur,
                                     @Field("jekel") String jekel,
                                     @Field("alamat") String alamat,
                                     @Field("lat") String lat,
                                     @Field("lng") String lng,
                                     @Field("no_hp") String no_hp,
                                     @Field("gol_darah") String gol_darah,
                                     @Field("bb") String bb,
                                     @Field("tensi") String tensi,
                                     @Field("kadar_hb") String kadar_hb,
                                     @Field("tanggal_donor") String tanggal_donor,
                                     @Field("jlh_kantong") String jlh_kantong,
                                     @Field("ket_sehat") String ket_sehat,
                                     @Field("ket_penyakit") String ket_penyakit);

    @FormUrlEncoded
    @POST("donor/get_pendonor.php")
    Call<ResponseBody>getPendonorById(@Field("id_user") String iduser);

    @FormUrlEncoded
    @POST("donor/update_pendonor.php")
    Call<ResponseBody>updateDataPendonor(@Field("id_pendonor") String idPendonor,
                                         @Field("nama_pendonor") String nama_pendonor,
                                         @Field("tempat_lahir") String tempat_lahir,
                                         @Field("tgl_lahir") String tgl_lahir,
                                         @Field("umur") String umur,
                                         @Field("jekel") String jekel,
                                         @Field("alamat") String alamat,
                                         @Field("lat") String lat,
                                         @Field("lng") String lng,
                                         @Field("no_hp") String no_hp,
                                         @Field("gol_darah") String gol_darah,
                                         @Field("bb") String bb,
                                         @Field("tensi") String tensi,
                                         @Field("kadar_hb") String kadar_hb,
                                         @Field("tanggal_donor") String tanggal_donor,
                                         @Field("jlh_kantong") String jlh_kantong,
                                         @Field("ket_sehat") String ket_sehat,
                                         @Field("ket_penyakit") String ket_penyakit
    );
    
    @FormUrlEncoded
    @POST("stok/tambah_stok_darah.php")
    Call<ResponseBody>tambahStokDarah(@Field("gol_darah") String goldar,
                                      @Field("jlh_kantong_darah") String kantong);

    @GET("penerima/list_pendonor.php")
    Call<ResponseBody>getDataPendonor(@Query("goldar") String goldar);

    @FormUrlEncoded
    @POST("stok/kurang_stok_darah.php")
    Call<ResponseBody>kurangStokDarah(@Field("gol_darah") String goldar,
                                      @Field("jlh_kantong_darah") String kantong);

    @FormUrlEncoded
    @POST("penerima/kurang_kantong_pendonor.php")
    Call<ResponseBody>kurangKantongPendonor(@Field("id_pendonor") String id,
                                            @Field("jlh_kantong_kurang") String kurangKantong);

    @GET("stok/tampil_stock_darah.php")
    Call<ResponseBody>getStockDarah();

    @FormUrlEncoded
    @POST("penerima/add_history_pengambilan.php")
    Call<ResponseBody>addHistory(@Field("id_user") String id_user,
                                 @Field("id_pendonor") String id_pendonor,
                                 @Field("tgl_ambil") String tgl_ambil,
                                 @Field("kantong_diambil") String kantong_diambil
    );


}
