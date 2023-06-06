package com.example.pizza_app.adapter_ql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pizza_app.R;
import com.example.pizza_app.doi_tuong.taikhoan;

import java.util.List;

public class adapterKhachhang extends BaseAdapter {
    Context context;
    List<taikhoan> list;
    int layout;

    public adapterKhachhang(Context context, List<taikhoan> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
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
        TextView tentk= convertView.findViewById(R.id.tentinnhan);
        TextView tk= convertView.findViewById(R.id.tknhantin);
        tentk.setText(list.get(position).getTenkh());
        tk.setText(list.get(position).getTk());
        return convertView;
    }
}
