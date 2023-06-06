package com.example.pizza_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.telecom.PhoneAccount;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pizza_app.Main.MainActivity2;
import com.example.pizza_app.MainActivity_chinh;
import com.example.pizza_app.MainActivity_chitiet;
import com.example.pizza_app.R;
import com.example.pizza_app.doi_tuong.sanpham;

import java.util.List;

public class rcv_sp extends  RecyclerView.Adapter<rcv_sp.ViewHoler_sanpham> {
    List<sanpham> sanphamList;
    private  ItemClickListener itemClickListener;
    public rcv_sp(Context context,ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener=itemClickListener;
    }
    public void setData(List<sanpham> sanphamList){
        this.sanphamList=sanphamList;
        notifyDataSetChanged();
    }
    Context context;
    @NonNull
    @Override
    public ViewHoler_sanpham onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham,parent,false);
        return new rcv_sp.ViewHoler_sanpham(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler_sanpham holder, int position) {
         sanpham sanpham=sanphamList.get(position);
         holder.tensp.setText(sanpham.getTensp());
         holder.giasp.setText(sanpham.getGiasp());
        // chuyen anh
        Glide.with(context).load(sanpham.getHinhanh()).into(holder.hinhsp);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(sanpham);
            }
        });
    }
    @Override
    public int getItemCount() {
        if(sanphamList!=null){
            return sanphamList.size();
        }
        return 0;
    }
public  interface  ItemClickListener{
        void onItemClick(sanpham sanpham);
}
    public  class ViewHoler_sanpham extends RecyclerView.ViewHolder{
        private TextView tensp,giasp;
        private ImageView hinhsp;
        public ViewHoler_sanpham(@NonNull View itemView) {
            super(itemView);
            tensp=itemView.findViewById(R.id.ten_sp);
            giasp=itemView.findViewById(R.id.giasp);
            hinhsp=itemView.findViewById(R.id.img_sp);
        }
    }

}
