package com.example.pizza_app.adapter_ql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pizza_app.R;
import com.example.pizza_app.doi_tuong.sanpham;

import java.util.List;

public class adapterQlsanpham extends BaseAdapter {
    public adapterQlsanpham(Context context, int layout, List<sanpham> sanphamList) {
        this.context = context;
        this.layout = layout;
        this.sanphamList = sanphamList;
    }

    Context context;
    int layout;
    List<sanpham> sanphamList;
    @Override
    public int getCount() {
        return sanphamList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater1=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater1.inflate(layout,null);
        TextView ten_mua = convertView.findViewById(R.id.tensp_mua);
        TextView sp_mua= convertView.findViewById(R.id.loaisp_mua);
        TextView gia_mua=convertView.findViewById(R.id.giasp_mua);
        ImageView anh= convertView.findViewById(R.id.anhsp_mua);
        // chuyen anh
        Glide.with(context).load(sanphamList.get(i).getHinhanh()).into(anh);
        ten_mua.setText(sanphamList.get(i).getTensp());
        sp_mua.setText(sanphamList.get(i).getLoaisp());
        gia_mua.setText(sanphamList.get(i).getGiasp());
        return convertView;
    }
}
