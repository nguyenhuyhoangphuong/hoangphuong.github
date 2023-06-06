package com.example.pizza_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pizza_app.R;
import com.example.pizza_app.doi_tuong.Oder;
import com.example.pizza_app.doi_tuong.loai_pizza;

import java.util.List;

public class rcv_loai extends RecyclerView.Adapter<rcv_loai.Viewholder_loai> {
    List<loai_pizza> list;
    private rcv_loai.ItemClickListener itemClickListener;
    public rcv_loai(Context context,ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener=itemClickListener;
    }
    public void setData(List<loai_pizza> list){
        this.list=list;
        notifyDataSetChanged();
    }
    Context context;
    @NonNull
    @Override
    public Viewholder_loai onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai,parent,false);
        return new Viewholder_loai(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder_loai holder, int position) {
        loai_pizza loaiPizza=list.get(position);
        holder.textView.setText(loaiPizza.getTenloai());
        // chuyen anh
        Glide.with(context).load(loaiPizza.getHinhanh()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(loaiPizza);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }
    public  interface  ItemClickListener{
        void onItemClick(loai_pizza loai_pizza);
    }
    public  class  Viewholder_loai extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        public Viewholder_loai(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_loai);
            textView=itemView.findViewById(R.id.tenloai);
        }
    }
}
