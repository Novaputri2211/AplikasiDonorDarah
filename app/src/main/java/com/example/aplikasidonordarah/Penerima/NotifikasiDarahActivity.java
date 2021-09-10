package com.example.aplikasidonordarah.Penerima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.aplikasidonordarah.MainActivity;
import com.example.aplikasidonordarah.R;
import com.example.aplikasidonordarah.databinding.ActivityNotifikasiJemputDarahBinding;

public class NotifikasiDarahActivity extends AppCompatActivity {
    private ActivityNotifikasiJemputDarahBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotifikasiJemputDarahBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnHome2.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        });
    }
}