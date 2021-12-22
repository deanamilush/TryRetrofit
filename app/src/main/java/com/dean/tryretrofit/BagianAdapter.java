package com.dean.tryretrofit;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dean.tryretrofit.api.DataItem;
import com.dean.tryretrofit.api.nama.NameItem;

import java.util.ArrayList;

public class BagianAdapter extends RecyclerView.Adapter<BagianAdapter.DaysViewHolder>{

    private ArrayList<DataItem> mData;
/*
    public BagianAdapter(ArrayList<DataUser> mData, Activity activity) {
        this.mData = mData;
        this.activity = activity;
    }*/

    public BagianAdapter(ArrayList<DataItem> mData) {
        this.mData = mData;
    }


    @NonNull
    @Override
    public DaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new DaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaysViewHolder holder, int position) {
        DataItem dataUser = mData.get(position);
        holder.kode.setText(dataUser.getKode());
        holder.bagian.setText(dataUser.getDeskripsiBagian());
        holder.lantai.setText(dataUser.getLantai());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class DaysViewHolder extends RecyclerView.ViewHolder {
        private final TextView kode, bagian, lantai, nama;

        public DaysViewHolder(@NonNull View itemView) {
            super(itemView);

            kode = itemView.findViewById(R.id.kode);
            bagian = itemView.findViewById(R.id.bagian);
            lantai = itemView.findViewById(R.id.lantai);
            nama = itemView.findViewById(R.id.nama);
        }
    }
}