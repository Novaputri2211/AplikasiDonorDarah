package com.example.aplikasidonordarah.Penerima.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aplikasidonordarah.Penerima.NotifikasiDarahActivity;
import com.example.aplikasidonordarah.R;
import com.example.aplikasidonordarah.SharedPreferences.PrefManager;
import com.example.aplikasidonordarah.UtilsApi.ApiInterface;
import com.example.aplikasidonordarah.UtilsApi.UtilsApi;
import com.example.aplikasidonordarah.databinding.FragmentConfirmJemputBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmJemputFragment extends BottomSheetDialogFragment {

    private FragmentConfirmJemputBinding binding;
    Context context;
    ApiInterface apiInterface;
    PrefManager manager;

    String id_pendonor, goldar, kantong_user;

    AlertDialog alertDialog;

    public ConfirmJemputFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentConfirmJemputBinding.inflate(inflater,container,false);
        context = getContext();
        apiInterface = UtilsApi.getApiService();
        manager = new PrefManager(context);

        alertDialog = new SpotsDialog.Builder().setContext(context).setCancelable(false).build();

        Bundle bundle = this.getArguments();
        id_pendonor = bundle.getString("id_pendonor");
        goldar = bundle.getString("goldar");
        kantong_user = bundle.getString("kantonguser");

        binding.plus.setOnClickListener(view -> {
            int total = Integer.parseInt(binding.jmlKantong.getText().toString()) + 1;
            binding.jmlKantong.setText(total+"");
        });
        binding.minus.setOnClickListener(view -> {
            if (Integer.parseInt(binding.jmlKantong.getText().toString()) <= 0){
                Toast.makeText(context, "Tidak bisa dikurangi lagi", Toast.LENGTH_SHORT).show();
            }else {
                int total = Integer.parseInt(binding.jmlKantong.getText().toString()) - 1;
                binding.jmlKantong.setText(total+"");
            }
        });

        binding.btnConfirm.setOnClickListener(view -> {
            int minus = Integer.parseInt(binding.jmlKantong.getText().toString());
            if (minus > Integer.parseInt(kantong_user)){
                Toast.makeText(context, "Tidak bisa diambil melebihi stok kantong user", Toast.LENGTH_SHORT).show();
            }else {
                kurangiKantongPendonor(id_pendonor);
                kurangiStokDarah(goldar);
                
                addtoHistory(id_pendonor);
            }

        });

        return binding.getRoot();
    }

    private void addtoHistory(String id_pendonor) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());

        apiInterface.addHistory(manager.getIdUser(),
                id_pendonor,
                currentDate,
                binding.jmlKantong.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){

                        }else {
                            Toast.makeText(context, object.getString("message")+"", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, t.getLocalizedMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void kurangiKantongPendonor(String id_pendonor) {
        apiInterface.kurangKantongPendonor(id_pendonor,binding.jmlKantong.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){

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
                Toast.makeText(context, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void kurangiStokDarah(String goldar) {
        alertDialog.show();
        apiInterface.kurangStokDarah(goldar,binding.jmlKantong.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    alertDialog.dismiss();
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){

                            Intent intent = new Intent(context, NotifikasiDarahActivity.class);
                            getActivity().onBackPressed();
                            startActivity(intent);

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
                alertDialog.dismiss();
                Toast.makeText(context, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        alertDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        alertDialog.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
        alertDialog.dismiss();
    }
}