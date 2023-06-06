package com.example.pizza_app.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pizza_app.MainActivity_chinh;
import com.example.pizza_app.MainActivity_giohang;
import com.example.pizza_app.MainActivity_quenmk;
import com.example.pizza_app.R;
import com.example.pizza_app.database.TaikhoanDatabase;
import com.example.pizza_app.doi_tuong.giohang;
import com.example.pizza_app.doi_tuong.taikhoan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    TextView taikhoan,matkhau,dangki,quenmk;
    Button dangnhap;
    TextView text_pizza;
    View view;
    List<taikhoan> taikhoandn= new ArrayList<taikhoan>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Getid();
        Hoatanh();
        dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(MainActivity2.this, MainActivity_dangky.class );
                startActivity(intent1);
            }
        });
        quenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity2.this, MainActivity_quenmk.class );
                startActivity(intent);
            }
        });
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t=taikhoan.getText().toString();
                String m=matkhau.getText().toString();
                Kiemtra(t,m);
            }
        });
    }
    private void Getid(){
        taikhoan=findViewById(R.id.tk);
        matkhau=findViewById(R.id.mk);
        dangki=findViewById(R.id.dangki);
        quenmk=findViewById(R.id.quen_mk);
        dangnhap=findViewById(R.id.dangnhap);
        text_pizza=findViewById(R.id.text_dn);
        view=findViewById(R.id.pizaa);
    }
    private  void Hoatanh(){
        text_pizza.setTranslationX(-500);
        text_pizza.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        view.setTranslationX(500);
        view.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }
    private  void Kiemtra(String tk, String mk){
        String u="https://greatashbook27.conveyor.cloud/kiemtra/"+tk+","+mk;
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
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
                                taikhoandn.add(new taikhoan(makh,tenkh,tk,mk,gioitinh,namsinh,diachi,sodienthoai,vaitro));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(taikhoandn.size()>0){
                            TaikhoanDatabase.getInstance(MainActivity2.this).data().insert(taikhoandn.get(0));
                            Intent intent= new Intent(MainActivity2.this,MainActivity_chinh.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(MainActivity2.this, "Tài khoản, mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
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
}