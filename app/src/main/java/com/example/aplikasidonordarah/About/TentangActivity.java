package com.example.aplikasidonordarah.About;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aplikasidonordarah.databinding.ActivityTentangBinding;

public class TentangActivity extends AppCompatActivity {
    private ActivityTentangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTentangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backTentang.setOnClickListener(view -> finish());
    }
}