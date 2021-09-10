package com.example.aplikasidonordarah.Intro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.aplikasidonordarah.MainActivity;
import com.example.aplikasidonordarah.R;
import com.example.aplikasidonordarah.SharedPreferences.PrefManager;
import com.example.aplikasidonordarah.UtilsApi.ApiInterface;
import com.example.aplikasidonordarah.UtilsApi.UtilsApi;
import com.example.aplikasidonordarah.databinding.ActivityLoginBinding;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private Context context;
    ApiInterface apiInterface;
    PrefManager manager;

    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        apiInterface = UtilsApi.getApiService();
        manager = new PrefManager(context);

        dialog = new SpotsDialog.Builder().setContext(context).setCancelable(false).setTheme(R.style.CustomDialogTheme).setMessage("Please wait").build();

        binding.btnLogin.setOnClickListener(view -> {
            processLogin();

        });

        binding.goRegister.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void processLogin() {
        dialog.show();
        apiInterface.login(
                binding.inputUsername.getText().toString(),
                binding.inputPassword.getText().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    dialog.dismiss();
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){
                            JSONObject data = object.getJSONObject("DATA");

                            //save data to shared preferences
                            manager.saveSessionUser();
                            manager.setIdUser(PrefManager.ID_USER, data.getString("id_user"));
                            manager.setNamaUser(PrefManager.NAMA_USER, data.getString("nama_user"));
                            manager.setGenderUser(PrefManager.GENDER, data.getString("jekel"));
                            manager.setTokenUser(PrefManager.TOKEN, data.getString("token"));

                            Intent intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }else {
                            Alerter.create(LoginActivity.this).
                                    setTitle("Error").setText(object.getString("message"))
                                    .setBackgroundResource(R.color.red).setIcon(R.drawable.ic_warning)
                                    .show();

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

    @Override
    protected void onStart() {
        super.onStart();
        PrefManager manager = new PrefManager(context);
        boolean id = manager.getSessionUser();

        if (id){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}