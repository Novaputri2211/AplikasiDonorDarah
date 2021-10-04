package com.example.aplikasidonordarah.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.aplikasidonordarah.UtilsApi.ApiInterface;
import com.example.aplikasidonordarah.UtilsApi.UtilsApi;
import com.example.aplikasidonordarah.databinding.ActivityRegisterBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private Context context;
    ApiInterface apiInterface;

    AlertDialog dialog;

    private String gender = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        apiInterface = UtilsApi.getApiService();

        dialog = new SpotsDialog.Builder().setCancelable(false).setContext(context).setMessage("Please Wait").build();

        selectGender();

        binding.goLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
        binding.btnSignup.setOnClickListener(view -> {
            if (gender.equalsIgnoreCase("")){
                Toast.makeText(this, "Pilih Jenis Kelamin", Toast.LENGTH_SHORT).show();
            }else if(binding.inputPass.getText().length() < 6){
                binding.inputPass.setError("Password minimal 6 karakter");
            }else {
                sendDataRegister();
            }

        });

    }

    private void sendDataRegister() {
        dialog.show();
        apiInterface.register(
                binding.inputNama.getText().toString(),
                binding.inputEmail.getText().toString(),
                gender,
                binding.inputUser.getText().toString(),
                binding.inputPass.getText().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    dialog.dismiss();
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){
                            Toast.makeText(context, object.getString("message")+", Silahkan Login", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(context,LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                        }else {
                            Toast.makeText(context, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
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
                dialog.dismiss();
                Toast.makeText(context, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectGender() {
        binding.lk.setOnClickListener(view -> {
            gender = "Laki-Laki";
            binding.lk.setBorderWidth(3);
            binding.pr.setBorderWidth(0);
        } );
        binding.pr.setOnClickListener(view -> {
            gender = "Perempuan";
            binding.lk.setBorderWidth(0);
            binding.pr.setBorderWidth(3);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}