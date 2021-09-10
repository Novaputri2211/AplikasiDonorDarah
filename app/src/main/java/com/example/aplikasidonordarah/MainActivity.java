package com.example.aplikasidonordarah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.aplikasidonordarah.About.TentangActivity;
import com.example.aplikasidonordarah.Intro.LoginActivity;
import com.example.aplikasidonordarah.Jadwal.JadwalActivity;
import com.example.aplikasidonordarah.Pendonor.FormPendonorActivity;
import com.example.aplikasidonordarah.Penerima.ListPendonorActivity;
import com.example.aplikasidonordarah.Profile.ProfileActivity;
import com.example.aplikasidonordarah.SharedPreferences.PrefManager;
import com.example.aplikasidonordarah.Stock.StokActivity;
import com.example.aplikasidonordarah.UtilsApi.ApiInterface;
import com.example.aplikasidonordarah.UtilsApi.UtilsApi;
import com.example.aplikasidonordarah.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    Context context;
    PrefManager manager;
    ApiInterface apiInterface;

    private int REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        manager = new PrefManager(context);
        apiInterface = UtilsApi.getApiService();

        //get user login with shared preferences
        binding.userLogin.setText(manager.getNamaUser());

        auth();

        binding.pendonor.setOnClickListener(view -> {
            cekPendonor();
        });
        binding.profile.setOnClickListener(view -> {
            String nama_user = manager.getNamaUser();
            String gender = manager.getGenderUser();

            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("namauser", nama_user);
            intent.putExtra("gender", gender);
            startActivity(intent);
        });
        binding.penerima.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListPendonorActivity.class);
            startActivity(intent);
        });
        binding.jadwal.setOnClickListener(view -> {
            Intent intent = new Intent(this, JadwalActivity.class);
            startActivity(intent);
        });
        binding.tentang.setOnClickListener(view -> {
            Intent intent = new Intent(this, TentangActivity.class);
            startActivity(intent);
        });
        binding.stok.setOnClickListener(view -> {
            Intent intent = new Intent(this, StokActivity.class);
            startActivity(intent);
        });

        checkPermissionLocation(Manifest.permission.ACCESS_FINE_LOCATION,REQ_CODE);
        if (permissionGranted()){
//            Toast.makeText(this, "SUDAH BERIZIN", Toast.LENGTH_SHORT).show();
        }else {
//            Toast.makeText(this, "BELUM BERIZIN", Toast.LENGTH_SHORT).show();
        }
    }

    private void auth() {
        apiInterface.getUser(manager.getIdUser()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){
                            JSONObject data = object.getJSONObject("DATA");

                            if (!data.getString("token").equalsIgnoreCase(manager.getTokenUser())){
                                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context)
                                        .setTitle("Oops!!!")
                                        .setMessage("Token expired, silahkan login kembali")
                                        .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                                        .setCancelable(false)
                                        .addButton("Oke",-1,-1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.END,(dialogInterface, i) -> {
                                           Intent intent = new Intent(context, LoginActivity.class);
                                           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            manager.removeSessionUser();
                                            finish();
                                           startActivity(intent);
                                        });
                                builder.show();

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void cekPendonor() {
        apiInterface.cekPendonor(manager.getIdUser()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){
                            Toast.makeText(context, "Tidak bisa tambah data, silahkan edit data di menu profile", Toast.LENGTH_LONG).show();
                        }else {
                            Intent intent = new Intent(context, FormPendonorActivity.class);
                            intent.putExtra("iduser",manager.getIdUser());
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkPermissionLocation(String accessFineLocation, int REQ_CODE) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),accessFineLocation) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {accessFineLocation},REQ_CODE);
        }else {

        }
    }

    private boolean permissionGranted() {
        int permissionState = ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION);

        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQ_CODE){
            if (grantResults!=null && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "PERMISSION GRANTED", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}