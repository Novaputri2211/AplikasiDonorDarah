package com.example.aplikasidonordarah.Penerima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.aplikasidonordarah.Penerima.Adapter.ConfirmJemputFragment;
import com.example.aplikasidonordarah.R;
import com.example.aplikasidonordarah.SharedPreferences.PrefManager;
import com.example.aplikasidonordarah.databinding.ActivityDetailPendonorBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailPendonorActivity extends AppCompatActivity {
    private ActivityDetailPendonorBinding binding;
    Context context;
    PrefManager prefManager;

    String id_pendonor,id_user,nama,goldar,umur,kantong,alamat,hp,lat,lng;

    private GoogleMap gmaps;
    private CameraPosition cameraPosition;
    private LatLng center;
    private LatLng position;
    private MarkerOptions markerOptions = new MarkerOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailPendonorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        prefManager = new PrefManager(context);

        getDataPendonor();
        binding.backDetail.setOnClickListener(view -> finish());

        binding.goMaps.setOnClickListener(view -> {
            String uri = String.format("https://maps.google.com/maps?q="+lat+","+lng+"");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        });

        binding.goChat.setOnClickListener(view -> {
            String phoneNumber = "+6282268763883"; //no PMI

            String message = "Halo PMI, saya membutuhkan darah dan akan menjemput pendonor dengan nama " + nama +
                    " dengan golongan darah " +  goldar +" sebanyak "+kantong+" kantong";

            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumber, message))
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

        });

        FragmentManager manager = getSupportFragmentManager();
        binding.btnConfirmJemput.setOnClickListener(view -> {
            int jmk = Integer.parseInt(kantong);
            int iduser1 = Integer.parseInt(id_user);
            int iduser2 = Integer.parseInt(prefManager.getIdUser());
            
            if ( iduser1 == iduser2 ){
                Toast.makeText(this, "Tidak bisa mengambil darah sendiri", Toast.LENGTH_SHORT).show();
            }else
                if (jmk <= 0){
                Toast.makeText(this, "Darah tidak bisa dijemput kantong darah kosong", Toast.LENGTH_SHORT).show();
            }else {
                //kirim id pendonor, goldar
                Bundle bundle = new Bundle();
                bundle.putString("id_pendonor", id_pendonor);
                bundle.putString("goldar", goldar);
                bundle.putString("kantonguser", kantong);

                ConfirmJemputFragment fragment = new ConfirmJemputFragment();
                fragment.setArguments(bundle);
                fragment.show(manager, "Jemput");



            }
        });

        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.onResume();
        AsyncMap();

    }

    private void getDataPendonor() {
        Intent intent = getIntent();
        id_pendonor = intent.getStringExtra("id_pendonor");
        id_user = intent.getStringExtra("id_user");
        nama = intent.getStringExtra("nama");
        goldar = intent.getStringExtra("goldar");
        umur = intent.getStringExtra("umur");
        kantong = intent.getStringExtra("kantong");
        alamat = intent.getStringExtra("alamat");
        hp = intent.getStringExtra("hp");
        lat = intent.getStringExtra("lat");
        lng = intent.getStringExtra("lng");

        binding.detailNamaPendonor.setText(nama);
        binding.detailGoldar.setText(goldar);
        binding.detailUmur.setText(umur+ " tahun");
        binding.detailKantong.setText(kantong+" kantong");
        binding.detailAlamat.setText(alamat);
        binding.detailTelp.setText(hp);
    }

    private void AsyncMap() {
        binding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                getPointLocation(googleMap);
            }
        });
    }

    private void getPointLocation(GoogleMap googleMap) {
        position = new LatLng(Double.valueOf(lat),Double.valueOf(lng));

        gmaps = googleMap;
        cameraPosition = new CameraPosition.Builder().target(position).zoom(13F).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        
        setLocationToMap(position);
    }

    private void setLocationToMap(LatLng position) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.donor);
        Bitmap b = Bitmap.createScaledBitmap(bitmap,75,75,false);

        markerOptions.position(position);
        markerOptions.title(position.toString());
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(b));

        gmaps.addMarker(markerOptions);
    }
}