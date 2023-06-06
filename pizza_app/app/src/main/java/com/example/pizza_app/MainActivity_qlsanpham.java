package com.example.pizza_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pizza_app.adapter.adapterSanpham;
import com.example.pizza_app.adapter_ql.adapterQlsanpham;
import com.example.pizza_app.doi_tuong.sanpham;
import com.example.pizza_app.doi_tuong.taikhoan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_qlsanpham extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    List<sanpham> list=new ArrayList<sanpham>();
    adapterQlsanpham adapterQlsanpham;
    String url="https://greatashbook27.conveyor.cloud/api/sanphams";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_qlsanpham);
        toolbar=findViewById(R.id.toolbarqlsanpham);
        listView=findViewById(R.id.dssp);
        ActionToolbar();
        adapterQlsanpham=new adapterQlsanpham(this,R.layout.item_spql,list);
        listView.setAdapter(adapterQlsanpham);
        getSanpham();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sanpham sanpham=list.get(i);
                Dialogqlsp(sanpham);
            }
        });
    }
    // xu ly toolbar
    private void ActionToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getSanpham(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_qlsanpham.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Xử lý phản hồi từ server
                        try {
                            // Duyệt qua danh sách đối tượng JSON
                            for (int i = 0; i < response.length(); i++) {
                                // Lấy đối tượng JSON ở vị trí i
                                JSONObject object = response.getJSONObject(i);
                                // Lấy giá trị của các thuộc tính
                                String donvi=object.getString("donvi");
                                String hinhanh=object.getString("hinhanh");
                                String maloai=object.getString("maloai");
                                String masp=object.getString("masp");
                                String mota=object.getString("mota");
                                String tensp=object.getString("tensp");
                                // Xử lý thông tin đối tượng
                                list.add(new sanpham(masp,tensp,maloai,donvi,mota,hinhanh));
                                adapterQlsanpham.notifyDataSetChanged();
                                // ...
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi khi gửi yêu cầu đến server
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void Dialogqlsp(sanpham sanpham){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_qlsanpham);
        Window window=dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        WindowManager.LayoutParams windowAttributes=window.getAttributes();
        windowAttributes.gravity= Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(true);
        // anh xa dialog
        TextView masp;
        EditText tensp,loaisp,giasp,mota;
        Button luu,xoa;
        masp=dialog.findViewById(R.id.maspql);
        tensp=dialog.findViewById(R.id.tenspql);
        loaisp=dialog.findViewById(R.id.loaispql);
        giasp=dialog.findViewById(R.id.giaspql);
        mota=dialog.findViewById(R.id.motaspql);
        luu=dialog.findViewById(R.id.luuqlsp);
        xoa=dialog.findViewById(R.id.xoaqlsp);
        masp.setText(sanpham.getMasp());
        tensp.setText(sanpham.getTensp());
        loaisp.setText(sanpham.getLoaisp());
        giasp.setText(sanpham.getGiasp());
        mota.setText(sanpham.getMota());
        // su ly button
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a,b,c,d;
                a=giasp.getText().toString();
                b=loaisp.getText().toString();
                c=mota.getText().toString();
                d=tensp.getText().toString();
                Sua(a,sanpham.getHinhanh(),b,sanpham.getMasp(),c,d);
                dialog.dismiss();
            }
        });
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Xoa(sanpham.getMasp());
                dialog.dismiss();
                list.remove(sanpham);
                adapterQlsanpham.notifyDataSetChanged();
            }
        });
        dialog.show();
    }
    private void Xoa(String a){
        RequestQueue queue = Volley.newRequestQueue(MainActivity_qlsanpham.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+"/"+a,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Xử lý dữ liệu trả về ở đây nếu cần thiết
                        Toast.makeText(MainActivity_qlsanpham.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi ở đây nếu có
            }
        });
        queue.add(stringRequest);
    }
    private void Sua(String a,String b, String c,String d,String g,String j){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_qlsanpham.this);
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("donvi",a);
            jsonObject.put("hinhanh",b);
            jsonObject.put("maloai",c);
            jsonObject.put("masp",d);
            jsonObject.put("mota",g);
            jsonObject.put("tensp",j);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, url+"/"+d, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MainActivity_qlsanpham.this,"Sửa thành công",Toast.LENGTH_LONG).show();
                        getSanpham();
                        // Handle response
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        });
        requestQueue.add(postRequest);
    }
}