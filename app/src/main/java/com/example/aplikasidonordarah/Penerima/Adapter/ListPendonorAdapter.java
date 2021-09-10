package com.example.aplikasidonordarah.Penerima.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasidonordarah.Penerima.DetailPendonorActivity;
import com.example.aplikasidonordarah.Penerima.ListPendonor;
import com.example.aplikasidonordarah.databinding.RowListPendonorBinding;

import java.util.ArrayList;
import java.util.List;

public class ListPendonorAdapter extends RecyclerView.Adapter<ListPendonorAdapter.viewHolder> {
    Context context;
    ArrayList<ListPendonor.DATABean> listPendonor;

    public ListPendonorAdapter(Context context, ArrayList<ListPendonor.DATABean> dataBeans) {
        this.context = context;
        this.listPendonor = dataBeans;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowListPendonorBinding view = RowListPendonorBinding.inflate(LayoutInflater.from(context),parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ListPendonor.DATABean data = listPendonor.get(position);

        holder.binding.no.setText((position+1)+"");
        holder.binding.txtNamaPendonor.setText(data.getNama());
        holder.binding.txtGoldarlist.setText(data.getGol_darah());

        holder.binding.selectPendonor.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailPendonorActivity.class);
            intent.putExtra("id_pendonor", data.getId_pendonor());
            intent.putExtra("id_user", data.getId_user());
            intent.putExtra("nama", data.getNama());
            intent.putExtra("goldar", data.getGol_darah());
            intent.putExtra("umur", data.getUmur());
            intent.putExtra("kantong", data.getJlh_kantong_pendonor());
            intent.putExtra("alamat", data.getAlamat());
            intent.putExtra("hp", data.getNo_hp());
            intent.putExtra("lat", data.getLat());
            intent.putExtra("lng", data.getLng());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return listPendonor.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        RowListPendonorBinding binding;
        public viewHolder(RowListPendonorBinding rowListPendonorBinding) {
            super(rowListPendonorBinding.getRoot());
            this.binding = rowListPendonorBinding;
        }
    }
}
