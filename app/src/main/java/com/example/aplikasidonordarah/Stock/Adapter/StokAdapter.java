package com.example.aplikasidonordarah.Stock.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasidonordarah.Stock.Stok;
import com.example.aplikasidonordarah.databinding.RowStokBinding;

import java.util.ArrayList;

public class StokAdapter extends RecyclerView.Adapter<StokAdapter.viewHolder> {
    private Context context;
    private ArrayList<Stok.DATABean> listStok;

    public StokAdapter(Context context, ArrayList<Stok.DATABean> dataBeans) {
        this.context = context;
        this.listStok = dataBeans;
    }

    @NonNull
    @Override
    public StokAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowStokBinding view = RowStokBinding.inflate(LayoutInflater.from(context),parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StokAdapter.viewHolder holder, int position) {
        Stok.DATABean data = listStok.get(position);

        holder.binding.txtGoldar.setText(data.getGol_darah());
        holder.binding.txtJumlahKantong.setText(data.getJlh_kantong_darah() + " kantong");
    }

    @Override
    public int getItemCount() {
        return listStok.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private RowStokBinding binding;
        public viewHolder(RowStokBinding rowStokBinding) {
            super(rowStokBinding.getRoot());
            this.binding = rowStokBinding;
        }
    }
}
