package com.example.pizza_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pizza_app.R;
import com.example.pizza_app.doi_tuong.Oder;
import com.example.pizza_app.doi_tuong.sanpham;

import java.util.ArrayList;
import java.util.List;

public class rcv_danhgia extends RecyclerView.Adapter<rcv_danhgia.DanhgiaVIewholed> {
    Context context;
    public rcv_danhgia(Context context) {
        this.context = context;
    }
    List<sanpham> sanphamList=new ArrayList<sanpham>();
    public void setData(List<sanpham> List){
        this.sanphamList=List;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DanhgiaVIewholed onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhgia,parent,false);
        return new rcv_danhgia.DanhgiaVIewholed(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhgiaVIewholed holder, int position) {
        sanpham sanpham=sanphamList.get(position);
        holder.ten.setText(sanpham.getTensp());
        // chuyen anh
        Glide.with(context).load(sanpham.getHinhanh()).into(holder.anh);
    }

    @Override
    public int getItemCount() {
        if(sanphamList!=null){
            return sanphamList.size();
        }
        return 0;
    }

    public class DanhgiaVIewholed extends RecyclerView.ViewHolder{
        ImageView anh;
        TextView ten;
        RatingBar sao;
        public DanhgiaVIewholed(@NonNull View itemView) {
            super(itemView);
            anh=itemView.findViewById(R.id.anhdanhgia);
            ten=itemView.findViewById(R.id.tendanhgia);
            sao=itemView.findViewById(R.id.sao);
            sao.setNumStars(5);
        }
    }
}
