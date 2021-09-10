package com.example.aplikasidonordarah.Pendonor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Contacts;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.aplikasidonordarah.R;
import com.example.aplikasidonordarah.SharedPreferences.PrefManager;
import com.example.aplikasidonordarah.UtilsApi.ApiInterface;
import com.example.aplikasidonordarah.UtilsApi.InterfaceJenisKelamin;
import com.example.aplikasidonordarah.UtilsApi.UtilsApi;
import com.example.aplikasidonordarah.databinding.ActivityFormPendonorBinding;
import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.GetLocationDetail;
import com.example.easywaylocation.Listener;
import com.example.easywaylocation.LocationData;
import com.shawnlin.numberpicker.NumberPicker;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.easywaylocation.EasyWayLocation.LOCATION_SETTING_REQUEST_CODE;

public class FormPendonorActivity extends AppCompatActivity implements LocationData.AddressCallBack, Listener {
    private ActivityFormPendonorBinding binding;
    Context context;
    ApiInterface apiInterface;
    InterfaceJenisKelamin interfaceJenisKelamin;
    AlertDialog dialog;

    DatePickerDialog dateLahirDialog, dateDonorDialog;
    int th,bln,tgl;
    String tglLahir, tglDonor, iduser;

    String selectGoldar = "";

    EasyWayLocation easyWayLocation = null;
    GetLocationDetail getLocationDetail;
    String latUser,lngUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormPendonorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        apiInterface = UtilsApi.getApiService();

        dialog = new SpotsDialog.Builder().setCancelable(false).setContext(context).build();

        Intent edit = getIntent();
        iduser = edit.getStringExtra("iduser");

        getLocationDetail = new GetLocationDetail(this,getApplicationContext());
        easyWayLocation = new EasyWayLocation(context,false,false,this);
        easyWayLocation.startLocation();

        interfaceJenisKelamin = new InterfaceJenisKelamin() {
            @Override
            public void setJekel(String jekel) {
                binding.selectJekel.setText(jekel);
            }
        };

        binding.btnCancel.setOnClickListener(view -> finish());
        binding.backForm.setOnClickListener(view -> finish());
        binding.btnSubmit.setOnClickListener(view -> {
            sendDataPendonor();
            updateStokDarah();
        });

        setData();

    }

    private void updateStokDarah() {
        apiInterface.tambahStokDarah(selectGoldar,binding.jmlKantong.getText().toString()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){

                        }else {

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

    private void sendDataPendonor() {
        dialog.show();
        apiInterface.tambahPendonor( iduser,
                binding.inputNama.getText().toString(),
                binding.inputTempatLahir.getText().toString(),
                tglLahir,
                binding.inputUmur.getText().toString(),
                binding.selectJekel.getText().toString(),
                binding.inputAlamat.getText().toString(),
                latUser,
                lngUser,
                binding.inputNoHp.getText().toString(),
                selectGoldar,
                binding.inputBb.getText().toString(),
                binding.inputTensi.getText().toString(),
                binding.inputHb.getText().toString(),
                tglDonor,
                binding.jmlKantong.getText().toString()
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    dialog.dismiss();
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){
                            Intent intent = new Intent(context, NotifikasiPendonorActivity.class);
                            finish();
                            startActivity(intent);
                        }else {
                            Alerter.create(FormPendonorActivity.this)
                                    .setTitle("Error")
                                    .setText(object.getString("message"))
                                    .setBackgroundResource(R.color.red)
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
                Alerter.create(FormPendonorActivity.this).setTitle("Error").setText(t.getLocalizedMessage()).setBackgroundResource(R.color.red).show();
            }
        });

    }

    private void setData() {
        //btn plus minus kantong darah
        binding.plus.setOnClickListener(view -> {
            int jml = Integer.parseInt(binding.jmlKantong.getText().toString()) + 1;
            binding.jmlKantong.setText(jml+"");
        });
        binding.minus.setOnClickListener(view -> {
            if (Integer.parseInt(binding.jmlKantong.getText().toString()) <= 0){
                Toast.makeText(this, "Tidak bisa dikurangi lagi", Toast.LENGTH_SHORT).show();
            }else {
                int jml = Integer.parseInt(binding.jmlKantong.getText().toString()) - 1;
                binding.jmlKantong.setText(jml+"");
            }
        });

        //pilih jenis kelamin
        binding.lk.setOnClickListener(view -> {
            interfaceJenisKelamin.setJekel("Laki-Laki");
            binding.lk.setBorderWidth(3);
            binding.pr.setBorderWidth(0);
        });
        binding.pr.setOnClickListener(view -> {
            interfaceJenisKelamin.setJekel("Perempuan");
            binding.lk.setBorderWidth(0);
            binding.pr.setBorderWidth(3);
        });

        //datepicker lahir
        Calendar calendar = Calendar.getInstance();
        th = calendar.get(Calendar.YEAR);
        bln = calendar.get(Calendar.MONTH);
        tgl = calendar.get(Calendar.DAY_OF_MONTH);

        dateLahirDialog = new DatePickerDialog(context,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                binding.inputTglLahir.setText(dayOfMonth+"-"+(month+1)+"-"+year);

                tglLahir = year+"-"+(month+1)+"-"+dayOfMonth;
            }
        },th,bln,tgl);
        binding.inputTglLahir.setOnClickListener(view -> {
            dateLahirDialog.show();
        });

        //datepicker donor
        dateDonorDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                binding.inputTglDonor.setText(dayOfMonth+"-"+month+"-"+year);

                tglDonor = year+"-"+(month+1)+"-"+dayOfMonth;
            }
        },th,bln,tgl);
        binding.inputTglDonor.setOnClickListener(view -> {
            dateDonorDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dateDonorDialog.show();
        });

        //picker donor darah
        String[] dataGoldar = {"A", "B", "AB", "O"};
        binding.inputGolDarah.setMinValue(0);
        binding.inputGolDarah.setMaxValue(3);
        binding.inputGolDarah.setDisplayedValues(dataGoldar);

        binding.inputGolDarah.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int gd = binding.inputGolDarah.getValue();
                selectGoldar = dataGoldar[gd];
            }
        });

    }

    //punya location data callback
    @Override
    public void locationData(LocationData locationData) {
        binding.inputAlamat.setText(locationData.getFull_address());
    }

    //punya easy way location
    @Override
    public void locationOn() {
        Toast.makeText(this, "Location ON", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void currentLocation(Location location) {
        latUser = location.getLatitude()+"";
        lngUser = location.getLongitude()+"";

//        binding.txtyyy.setText(location.getLatitude()+"--"+location.getLongitude());
        getLocationDetail.getAddress(location.getLatitude(),location.getLongitude(),"com.google.android.geo.API_KEY");
    }

    @Override
    public void locationCancelled() {
        Toast.makeText(this, "Location Canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        easyWayLocation.startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        easyWayLocation.endUpdates();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_SETTING_REQUEST_CODE){
            easyWayLocation.onActivityResult(resultCode);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}