package com.example.aplikasidonordarah.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aplikasidonordarah.databinding.ActivityKontakBinding;

public class KontakActivity extends AppCompatActivity {
    private ActivityKontakBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKontakBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.igpmi.setText("@pmipadang");
        binding.backForm.setOnClickListener(view -> finish());
    }
}