package com.example.pizza_app.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.pizza_app.MainActivity_giohang;
import com.example.pizza_app.R;
import com.example.pizza_app.doi_tuong.HoantienEvent;
import com.example.pizza_app.doi_tuong.giohang;
import com.example.pizza_app.doi_tuong.sanpham;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class rcv_giohang extends RecyclerView.Adapter<rcv_giohang.Viewholder_giohang> {
    List<giohang> list;
    Context context;
    int i=0;
    boolean kt;
    public rcv_giohang(Context context) {
        this.context = context;
    }
    public  void setdata(List<giohang> list){
        this.list=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Viewholder_giohang onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang,parent,false);
        return new rcv_giohang.Viewholder_giohang(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder_giohang holder, int position) {
            giohang giohang= list.get(position);
            kt=true;
            holder.tensp.setText(giohang.getTensp());
            holder.soluong.setText(giohang.getSoluong());
            i= Integer.parseInt(giohang.getSoluong());
            holder.gia.setText(giohang.getGiasp());
       // chuyen anh
        Glide.with(context).load(giohang.getHinhanh()).into(holder.anh);
       holder.cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                holder.soluong.setText(""+i);
            }
        });
        holder.tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i>0){
                    i--;
                }
                holder.soluong.setText(""+i);
            }
        });
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a=""+ giohang.getMa()+","+giohang.getMasp();
                String url1="https://greatashbook27.conveyor.cloud/xoagiohangs2/"+a;
                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Xử lý dữ liệu trả về ở đây nếu cần thiết
                                Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Xử lý lỗi ở đây nếu có
                    }
                });
                queue.add(stringRequest);
            }
        });
        holder.thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kt){
                    holder.thanhtoan.setBackgroundColor(Color.parseColor("#FF5722"));
                    EventBus.getDefault().postSticky(new giohang(giohang.getMa(),giohang.getMasp(),
                            giohang.getSoluong(), giohang.getHinhanh(),giohang.getTensp()
                            ,giohang.getGiasp()));
                    kt=false;
                }else {
                    holder.thanhtoan.setBackgroundColor(Color.parseColor("#C6BABA"));
                    EventBus.getDefault().postSticky(new HoantienEvent(giohang.getMa(),giohang.getMasp(),
                            giohang.getSoluong(), giohang.getHinhanh(),giohang.getTensp()
                            ,giohang.getGiasp()));
                    kt=true;
                }
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

    public  class  Viewholder_giohang extends RecyclerView.ViewHolder {
        ImageView anh, cong, tru;
        TextView tensp, soluong, gia;
        Button xoa, thanhtoan;
        public Viewholder_giohang(@NonNull View itemView) {
            super(itemView);
            anh=itemView.findViewById(R.id.anh_giohang);
            cong=itemView.findViewById(R.id.cong_gio);
            tru=itemView.findViewById(R.id.tru_gio);
            tensp=itemView.findViewById(R.id.ten_gio);
            soluong= itemView.findViewById(R.id.soluong_gio);
            gia=itemView.findViewById(R.id.gia_giohang);
            xoa=itemView.findViewById(R.id.xoa);
            thanhtoan=itemView.findViewById(R.id.chitiet);
        }
    }
}
