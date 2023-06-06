package com.example.pizza_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pizza_app.adapter.adapterSanpham;
import com.example.pizza_app.adapter.rcv_tinnhan;
import com.example.pizza_app.database.TaikhoanDatabase;
import com.example.pizza_app.doi_tuong.sanpham;
import com.example.pizza_app.doi_tuong.taikhoan;
import com.example.pizza_app.doi_tuong.tinnhan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity_nhantin extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    rcv_tinnhan rcv_tinnhan;
    EditText noidung;
    List<tinnhan> list= new ArrayList<tinnhan>();
    Button gui;
    String url="https://greatashbook27.conveyor.cloud/";
    List<taikhoan> daco=new ArrayList<taikhoan>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daco= TaikhoanDatabase.getInstance(this).data().getList();
        setContentView(R.layout.activity_main_nhantin);
        toolbar=findViewById(R.id.toolbar_thongbao);
        recyclerView=findViewById(R.id.rcv_tinnhan);
        noidung=findViewById(R.id.thongtingui);
        gui=findViewById(R.id.gui);
        ActionToolbar();
        rcv_tinnhan= new rcv_tinnhan(this);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(MainActivity_nhantin.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager1);
        rcv_tinnhan.setData(list);
        Laytinnhan();
        recyclerView.setAdapter(rcv_tinnhan);
        gui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nd=noidung.getText().toString();
                Guitinnhan(nd);
            }
        });
    }
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
    private void Laytinnhan(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_nhantin.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+"tinnhan/"+daco.get(0).getMakh(), null,
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
    private void Guitinnhan(String nd){
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_nhantin.this);
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("magui",""+daco.get(0).getMakh());
            jsonObject.put("manhan","1");
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
                        Laytinnhan();
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