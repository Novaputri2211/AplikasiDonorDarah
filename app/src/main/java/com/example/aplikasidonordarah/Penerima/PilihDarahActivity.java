package com.example.aplikasidonordarah.Penerima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.aplikasidonordarah.databinding.ActivityPilihDarahBinding;

public class PilihDarahActivity extends AppCompatActivity {
    ActivityPilihDarahBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPilihDarahBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.selectA.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListPendonorActivity.class);
            intent.putExtra("goldar", "A");
            startActivity(intent);
        });

        binding.selectAB.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListPendonorActivity.class);
            intent.putExtra("goldar", "AB");
            startActivity(intent);
        });

        binding.selectB.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListPendonorActivity.class);
            intent.putExtra("goldar", "B");
            startActivity(intent);
        });

        binding.selectO.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListPendonorActivity.class);
            intent.putExtra("goldar", "O");
            startActivity(intent);
        });
    }
}