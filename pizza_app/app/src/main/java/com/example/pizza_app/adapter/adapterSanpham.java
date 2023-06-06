package com.example.pizza_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.pizza_app.MainActivity_chitiet;
import com.example.pizza_app.R;
import com.example.pizza_app.doi_tuong.sanpham;

import java.util.ArrayList;
import java.util.List;

public class adapterSanpham extends ArrayAdapter<sanpham> {
    List<sanpham> listdata=new ArrayList<>();
    List<sanpham> l=new ArrayList<>();
    public adapterSanpham(@NonNull Context context, int resource, @NonNull List<sanpham> objects) {
        super(context, resource, objects);
        if(context!=null&&objects!=null){
            l= new ArrayList<>(objects);
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<sanpham> lold= new ArrayList<>();
                if(charSequence==null|| charSequence.length()==0){
                    lold.addAll(l);
                }else {
                    String filter= charSequence.toString().toLowerCase().trim();
                    for(sanpham t: l){
                        if(t.getTensp().toLowerCase().contains(filter) || t.getMasp().toLowerCase().contains(filter)){
                            lold.add(t);
                        }
                    }
                }
                FilterResults filterResults= new FilterResults();
                filterResults.values=lold;
                filterResults.count=lold.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                clear();
                addAll((List<sanpham>)filterResults.values);
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((sanpham) resultValue).getTensp();
            }
        };
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.item_sanpham1, parent, false);
        TextView tensp,giasp;
        ImageView hinhsp;
        sanpham sanpham=getItem(position);
        tensp=view.findViewById(R.id.ten_sp1);
        giasp=view.findViewById(R.id.giasp1);
        hinhsp=view.findViewById(R.id.img_sp1);
        tensp.setText(sanpham.getTensp());
        giasp.setText(sanpham.getGiasp());
        Glide.with(getContext()).load(sanpham.getHinhanh()).into(hinhsp);
        return view;
    }
}
