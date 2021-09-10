package com.example.aplikasidonordarah.Pendonor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.aplikasidonordarah.MainActivity;
import com.example.aplikasidonordarah.R;
import com.example.aplikasidonordarah.databinding.ActivityNotifikasiFormPendonorBinding;

public class NotifikasiPendonorActivity extends AppCompatActivity {
    private ActivityNotifikasiFormPendonorBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotifikasiFormPendonorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnHome.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        });
    }
}