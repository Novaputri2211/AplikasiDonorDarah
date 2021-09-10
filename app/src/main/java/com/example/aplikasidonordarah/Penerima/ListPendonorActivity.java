package com.example.aplikasidonordarah.Penerima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;

import com.example.aplikasidonordarah.Penerima.Adapter.ListPendonorAdapter;
import com.example.aplikasidonordarah.R;
import com.example.aplikasidonordarah.UtilsApi.ApiInterface;
import com.example.aplikasidonordarah.UtilsApi.UtilsApi;
import com.example.aplikasidonordarah.databinding.ActivityListPendonorBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPendonorActivity extends AppCompatActivity {
    private ActivityListPendonorBinding binding;
    Context context;
    ApiInterface apiInterface;

    ListPendonorAdapter adapter;
    ArrayList<ListPendonor.DATABean> dataBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListPendonorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        apiInterface = UtilsApi.getApiService();

        binding.backList.setOnClickListener(view -> finish());

        getDataListPendonor();

    }

    private void getDataListPendonor() {
        apiInterface.getDataPendonor().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){
                            JSONArray array = object.getJSONArray("DATA");

                            Gson gson = new Gson();
                            dataBeans = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                ListPendonor.DATABean listPendonor = gson.fromJson(array.getJSONObject(i).toString(), ListPendonor.DATABean.class);
                                dataBeans.add(listPendonor);
                            }

                            adapter = new ListPendonorAdapter(context,dataBeans);
                            binding.recyclerListPendonor.setAdapter(adapter);
                            binding.recyclerListPendonor.setLayoutManager(new LinearLayoutManager(context));
                            binding.recyclerListPendonor.setHasFixedSize(true);
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