package com.example.pizza_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pizza_app.Main.MainActivity2;
import com.example.pizza_app.adapter.rcv_tinnhan;
import com.example.pizza_app.adapter_ql.adapterKhachhang;
import com.example.pizza_app.database.TaikhoanDatabase;
import com.example.pizza_app.doi_tuong.taikhoan;
import com.example.pizza_app.doi_tuong.tinnhan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity_tuvan extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    adapterKhachhang adapterKhachhang;
    String url="https://greatashbook27.conveyor.cloud/";
    List<taikhoan> taikhoanList=  new ArrayList<taikhoan>();
    //
    RecyclerView recyclerView;
    rcv_tinnhan rcv_tinnhan;
    EditText noidung;
    List<tinnhan> list= new ArrayList<tinnhan>();
    Button gui;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tuvan);
        toolbar=findViewById(R.id.toolbarqltinnhan);
        ActionToolbar();
        listView=findViewById(R.id.dstinnhan);
        adapterKhachhang= new adapterKhachhang(this,taikhoanList,R.layout.item_khachhang);
        listView.setAdapter(adapterKhachhang);
        Getkhachhang();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Hopnhan(""+taikhoanList.get(i).getMakh());
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
    private void Getkhachhang(){
        String u="https://greatashbook27.conveyor.cloud/api/khachhangs";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_tuvan.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, u, null,
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
                                String gioitinh=object.getString("Gioitinh");
                                String diachi=object.getString("diachi");
                                int makh=Integer.parseInt(object.getString("makh"));
                                String mk=object.getString("mk");
                                String namsinh=object.getString("namsinh");
                                String sodienthoai=object.getString("sodienthoai");
                                String tenkh=object.getString("tenkh");
                                String tk=object.getString("tk");
                                String vaitro=object.getString("vaitro");
                                taikhoanList.add(new taikhoan(makh,tenkh,tk,mk,gioitinh,namsinh,diachi,sodienthoai,vaitro));
                                adapterKhachhang.notifyDataSetChanged();
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
    private void Hopnhan(String a){
        list.clear();
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_main_nhantin);
        Window window= dialog.getWindow();
        if(window== null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable( new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes= window.getAttributes();
        windowAttributes.gravity= Gravity.BOTTOM;
        window.setAttributes(windowAttributes);

        dialog.setCancelable(true);
        //
        recyclerView=dialog.findViewById(R.id.rcv_tinnhan);
        noidung=dialog.findViewById(R.id.thongtingui);
        gui=dialog.findViewById(R.id.gui);
        ActionToolbar();
        rcv_tinnhan= new rcv_tinnhan(this);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(MainActivity_tuvan.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager1);
        rcv_tinnhan.setData(list);
        Laytinnhan(a);
        recyclerView.setAdapter(rcv_tinnhan);
        gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nd=noidung.getText().toString();
                Guitinnhan(nd,a);
            }
        });
        //
        dialog.show();
    }
    private void Laytinnhan(String a){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_tuvan.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+"tinnhan/"+a, null,
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
                                String magui=object.getString("magui");
                                String manhan=object.getString("manhan");
                                String matinnhan=object.getString("matinnhan");
                                String noidung1=object.getString("noidung");
                                // Xử lý thông tin đối tượng
                                list.add(new tinnhan(noidung1,magui,manhan,matinnhan));
                                rcv_tinnhan.notifyDataSetChanged();
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
    private void Guitinnhan(String nd,String nhan){
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_tuvan.this);
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("magui","1");
            jsonObject.put("manhan",""+nhan);
            jsonObject.put("matinnhan",""+randomNumber);
            jsonObject.put("noidung",nd);

        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url+"api/tinnhans", jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        noidung.setText("");
                        list.clear();
                        Laytinnhan(nhan);
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