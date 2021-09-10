package com.example.aplikasidonordarah.Jadwal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aplikasidonordarah.databinding.ActivityJadwalBinding;

public class JadwalActivity extends AppCompatActivity {
    private ActivityJadwalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJadwalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backJadwal.setOnClickListener(view -> finish());
    }
}