package com.example.aplikasidonordarah.Stock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;

import com.example.aplikasidonordarah.Stock.Adapter.StokAdapter;
import com.example.aplikasidonordarah.UtilsApi.ApiInterface;
import com.example.aplikasidonordarah.UtilsApi.RetrofitClient;
import com.example.aplikasidonordarah.UtilsApi.UtilsApi;
import com.example.aplikasidonordarah.databinding.ActivityStokBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StokActivity extends AppCompatActivity {
    private ActivityStokBinding binding;
    private Context context;

    private StokAdapter adapter;
    private ArrayList<Stok.DATABean> dataBeans;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStokBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        apiInterface = UtilsApi.getApiService();

        binding.backStok.setOnClickListener(view -> finish());

        getDataStok();
    }

    private void getDataStok() {
        apiInterface.getStockDarah().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){
                            JSONArray array = object.getJSONArray("DATA");

                            Gson gson = new Gson();
                            dataBeans = new ArrayList<>();

                            for (int i = 0; i < array.length() ; i++) {
                                Stok.DATABean stok = gson.fromJson(array.getJSONObject(i).toString(), Stok.DATABean.class);
                                dataBeans.add(stok);
                            }

                            adapter = new StokAdapter(context,dataBeans);
                            binding.recyclerStok.setAdapter(adapter);
                            binding.recyclerStok.setLayoutManager(new LinearLayoutManager(context));
                            binding.recyclerStok.setHasFixedSize(true);
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