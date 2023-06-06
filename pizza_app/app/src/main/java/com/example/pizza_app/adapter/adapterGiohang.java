package com.example.pizza_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pizza_app.R;
import com.example.pizza_app.doi_tuong.giohang;

import java.util.List;

public class adapterGiohang  extends BaseAdapter {
    public adapterGiohang(Context context, int layout, List<giohang> giohangList) {
        this.context = context;
        this.layout = layout;
        this.giohangList = giohangList;
    }

    Context context;
    int layout;
    List<giohang> giohangList;
    @Override
    public int getCount() {
        return giohangList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater1=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater1.inflate(layout,null);
        TextView ten_mua = convertView.findViewById(R.id.tensp_mua);
        TextView soluong_mua= convertView.findViewById(R.id.soluongsp_mua);
        TextView gia_mua=convertView.findViewById(R.id.giasp_mua);
        ImageView anh= convertView.findViewById(R.id.anhsp_mua);
        // chuyen anh
        Glide.with(context).load(giohangList.get(position).getHinhanh()).into(anh);
        ten_mua.setText(giohangList.get(position).getTensp());
        soluong_mua.setText(giohangList.get(position).getSoluong());
        gia_mua.setText(giohangList.get(position).getGiasp());
        return convertView;
    }
}
