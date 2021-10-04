package com.example.aplikasidonordarah.Pendonor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.aplikasidonordarah.SharedPreferences.PrefManager;
import com.example.aplikasidonordarah.UtilsApi.ApiInterface;
import com.example.aplikasidonordarah.UtilsApi.InterfaceJenisKelamin;
import com.example.aplikasidonordarah.UtilsApi.UtilsApi;
import com.example.aplikasidonordarah.databinding.ActivityEditPendonorBinding;
import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.GetLocationDetail;
import com.example.easywaylocation.Listener;
import com.example.easywaylocation.LocationData;
import com.shawnlin.numberpicker.NumberPicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import dmax.dialog.SpotsDialog;
import lib.kingja.switchbutton.SwitchMultiButton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.easywaylocation.EasyWayLocation.LOCATION_SETTING_REQUEST_CODE;

public class EditPendonorActivity extends AppCompatActivity implements LocationData.AddressCallBack, Listener {
    private ActivityEditPendonorBinding binding;
    Context context;
    PrefManager manager;
    ApiInterface apiInterface;
    InterfaceJenisKelamin interfaceJenisKelamin;

    AlertDialog dialog;
    DatePickerDialog dateLahirDialog, dateDonorDialog;
    int th,bln,tgl;
    int lastStokDarah;
    String tglLahir, tglDonor, idPendonor;

    String selectGoldar = "";
    String pernyataanSehat = "YA";
    String pernyataanPenyakit = "ADA";
//    String lastSehat = "";
//    String lastPenyakit = "";

    EasyWayLocation easyWayLocation = null;
    GetLocationDetail getLocationDetail;
    String lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPendonorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        manager = new PrefManager(context);
        dialog = new SpotsDialog.Builder().setContext(context).setCancelable(false).build();
        apiInterface = UtilsApi.getApiService();
        interfaceJenisKelamin = new InterfaceJenisKelamin() {
            @Override
            public void setJekel(String jekel) {
                binding.selectJekel.setText(jekel);
            }
        };

        getLocationDetail = new GetLocationDetail(this,getApplicationContext());
        easyWayLocation = new EasyWayLocation(context,false,false,this);
        easyWayLocation.startLocation();

        binding.btnCancel.setOnClickListener(view -> finish());
        binding.backFormEdit.setOnClickListener(view -> finish());
        binding.btnUpdate.setOnClickListener(view -> {
            sendUpdateDataPendonor();
            cekKantongDarah();
        });

        getDataPendonor();

    }

    private void cekKantongDarah() {
        int toStokDarah = 0;
        toStokDarah = Integer.parseInt(binding.jmlKantongEdit.getText().toString()) - lastStokDarah;

        updateStokKantongDarah(toStokDarah);
    }
    private void updateStokKantongDarah(int toStokDarah) {
        apiInterface.tambahStokDarah(
                selectGoldar,String.valueOf(toStokDarah)

        ).enqueue(new Callback<ResponseBody>() {
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

    private void setDataUpdate(String lastSehat, String lastPenyakit) {
        //datepicker lahir
        Calendar calendar = Calendar.getInstance();
        th = calendar.get(Calendar.YEAR);
        bln = calendar.get(Calendar.MONTH);
        tgl = calendar.get(Calendar.DAY_OF_MONTH);

        dateLahirDialog = new DatePickerDialog(context,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                binding.editTglLahir.setText(dayOfMonth+"-"+(month+1)+"-"+year);

                tglLahir = year+"-"+(month+1)+"-"+dayOfMonth;
            }
        },th,bln,tgl);
        binding.editTglLahir.setOnClickListener(view -> {
            dateLahirDialog.show();
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

        //picker donor darah
        String[] dataGoldar = {"A", "B", "AB", "O"};
        binding.editGolDarah.setMinValue(0);
        binding.editGolDarah.setMaxValue(3);
        binding.editGolDarah.setDisplayedValues(dataGoldar);

        binding.editGolDarah.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int gd = binding.editGolDarah.getValue();
                selectGoldar = dataGoldar[gd];
            }
        });

        //datepicker donor
        dateDonorDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                binding.editTglDonor.setText(dayOfMonth+"-"+month+"-"+year);

                tglDonor = year+"-"+(month+1)+"-"+dayOfMonth;
            }
        },th,bln,tgl);
        binding.editTglDonor.setOnClickListener(view -> {
            dateDonorDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dateDonorDialog.show();
        });

        //plus minus kantong darah
        binding.plus.setOnClickListener(view -> {
            int jml = Integer.parseInt(binding.jmlKantongEdit.getText().toString()) + 1;
            binding.jmlKantongEdit.setText(jml+"");
        });
        binding.minus.setOnClickListener(view -> {
            if (Integer.parseInt(binding.jmlKantongEdit.getText().toString()) <= 0){
                Toast.makeText(this, "Tidak bisa dikurangi lagi", Toast.LENGTH_SHORT).show();
            }else {
                int jml = Integer.parseInt(binding.jmlKantongEdit.getText().toString()) - 1;
                binding.jmlKantongEdit.setText(jml+"");
            }
        });

        //switch button
        binding.switchSehat.setText("YA", "TIDAK").setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                pernyataanSehat = tabText;
            }
        });
        if (lastSehat.equalsIgnoreCase("TIDAK")){
            binding.switchSehat.setSelectedTab(1);
        }

        binding.switchPenyakit.setText("ADA", "TIDAK ADA").setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                pernyataanPenyakit = tabText;
            }
        });
        if (lastPenyakit.equalsIgnoreCase("TIDAK ADA")){
            binding.switchPenyakit.setSelectedTab(1);
        }


    }
    private void getDataPendonor() {
        apiInterface.getPendonorById(manager.getIdUser()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){
                            JSONObject data = object.getJSONObject("DATA");

                            //set data goldar ke form
                            String[] dataGoldar = {"A", "B", "AB", "O"};
                            for (int i = 0; i < dataGoldar.length; i++) {
                                if(dataGoldar[i].equals(data.getString("gol_darah"))){
                                    binding.editGolDarah.setValue(i);
                                    selectGoldar = dataGoldar[i];
                                }
                            }

                            //set tgl lahir ke form
                            String bornDate = data.getString("tgl_lahir");
                            tglLahir = bornDate; // kirim data ke variabel yg dipakai API
                            String formattedTglLahir = bornDate.substring(8,10) +"-"+ bornDate.substring(5,7) +"-"+ bornDate.substring(0,4);

                            //set tgl donor ke form
                            String donorDate = data.getString("tgl_donor");
                            tglDonor = donorDate;
                            String formattedTglDonor = donorDate.substring(8,10) +"-"+ donorDate.substring(5,7) +"-"+ donorDate.substring(0,4);

                            lastStokDarah = Integer.parseInt(data.getString("jlh_kantong"));
                            String lastSehat = data.getString("ket_sehat");
                            String lastPenyakit = data.getString("ket_penyakit");

                            idPendonor = data.getString("id_pendonor");
                            binding.editNama.setText(data.getString("nama_pendonor"));
                            binding.editTempatLahir.setText(data.getString("tempat_lahir"));
                            binding.editTglLahir.setText(formattedTglLahir);
                            binding.editUmur.setText(data.getString("umur"));
                            binding.selectJekel.setText(data.getString("jekel"));
                            binding.editNoHp.setText(data.getString("no_hp"));
                            binding.editBb.setText(data.getString("bb"));
                            binding.editTensi.setText(data.getString("tensi"));
                            binding.editHb.setText(data.getString("hb"));
                            binding.editTglDonor.setText(formattedTglDonor);
                            binding.jmlKantongEdit.setText(data.getString("jlh_kantong"));

                            setDataUpdate(lastSehat,lastPenyakit);
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

    private void sendUpdateDataPendonor() {
        dialog.show();
        apiInterface.updateDataPendonor(idPendonor,
                binding.editNama.getText().toString(),
                binding.editTempatLahir.getText().toString(),
                tglLahir,
                binding.editUmur.getText().toString(),
                binding.selectJekel.getText().toString(),
                binding.editAlamat.getText().toString(),
                lat,lng,
                binding.editNoHp.getText().toString(),
                selectGoldar,
                binding.editBb.getText().toString(),
                binding.editTensi.getText().toString(),
                binding.editHb.getText().toString(),
                tglDonor,
                binding.jmlKantongEdit.getText().toString(),
                pernyataanSehat,
                pernyataanPenyakit
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    dialog.dismiss();
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        if (object.getString("status").equalsIgnoreCase("200")){
                            Intent intent = new Intent(context, NotifikasiUpdatePendonorActivity.class);
                            finish();
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
                dialog.dismiss();
                Toast.makeText(context, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void locationData(LocationData locationData) {
        binding.editAlamat.setText(locationData.getFull_address());
    }

    @Override
    public void locationOn() {

    }

    @Override
    public void currentLocation(Location location) {
        lat = String.valueOf(location.getLatitude());
        lng = String.valueOf(location.getLongitude());

        getLocationDetail.getAddress(location.getLatitude(),location.getLongitude(),"com.google.android.geo.API_KEY");
    }

    @Override
    public void locationCancelled() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        dialog.dismiss();
        easyWayLocation.endUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dialog.dismiss();
        easyWayLocation.startLocation();
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