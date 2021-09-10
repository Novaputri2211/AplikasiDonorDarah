package com.example.aplikasidonordarah.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.aplikasidonordarah.Intro.LoginActivity;
import com.example.aplikasidonordarah.Pendonor.EditPendonorActivity;
import com.example.aplikasidonordarah.Pendonor.FormPendonorActivity;
import com.example.aplikasidonordarah.R;
import com.example.aplikasidonordarah.SharedPreferences.PrefManager;
import com.example.aplikasidonordarah.UtilsApi.ApiInterface;
import com.example.aplikasidonordarah.UtilsApi.UtilsApi;
import com.example.aplikasidonordarah.databinding.ActivityProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private Context context;
    ApiInterface apiInterface;

    PrefManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        apiInterface = UtilsApi.getApiService();
        manager = new PrefManager(context);

        Intent getintent = getIntent();
        binding.namaUser.setText(getintent.getStringExtra("namauser"));

        if (getintent.getStringExtra("gender").equalsIgnoreCase("Laki-Laki")){
            Glide.with(context).load(R.drawable.ic_group_5).into(binding.gambarUser);
        }else if (getintent.getStringExtra("gender").equalsIgnoreCase("Perempuan")){
            Glide.with(context).load(R.drawable.ic_group_4).into(binding.gambarUser);
        }

        binding.backProfile.setOnClickListener(view -> finish());
        binding.formEditData.setOnClickListener(view -> {
            cekPendonor();
        });
        binding.kontakMenu.setOnClickListener(view -> {
            Intent intent = new Intent(this,KontakActivity.class);
            startActivity(intent);
        });

        binding.logout.setOnClickListener(view -> {
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                    .setTitle("Logout")
                    .setMessage("Keluar akun dari aplikasi?")
                    .addButton("Ya", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.END, (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                        finish();
                        manager.removeSessionUser();

                        Intent intent = new Intent(this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    });

            builder.show();
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
                            Intent intent = new Intent(context, EditPendonorActivity.class);
                            startActivity(intent);
                        }else {
                            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context)
                                    .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                                    .setMessage("Isi Form Pendonor Dahulu")
                                    .addButton("Ya", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.END, (dialogInterface, i) -> {
                                        dialogInterface.dismiss();
                                    });

                            builder.show();
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
}