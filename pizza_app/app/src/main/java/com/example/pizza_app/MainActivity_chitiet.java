package com.example.pizza_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.pizza_app.database.TaikhoanDatabase;
import com.example.pizza_app.doi_tuong.giohang;
import com.example.pizza_app.doi_tuong.sanpham;
import com.example.pizza_app.doi_tuong.taikhoan;
import com.google.gson.JsonObject;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity_chitiet extends AppCompatActivity {
    Toolbar toolbar;
    ImageView anh, giohang;
    Button mua;
    List<taikhoan> daco= new ArrayList<taikhoan>();
    TextView ten, gia,mota;
    sanpham sanpham;
    List<giohang> listgiohang= new ArrayList<>();
    //thong bao
    NotificationBadge badge;
    String url="https://greatashbook27.conveyor.cloud/api/giohangs";
    int i=1, t=0,g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chitiet2);
        daco= TaikhoanDatabase.getInstance(MainActivity_chitiet.this).data().getList();
        Intent intent= getIntent();
        Bundle bundle=intent.getBundleExtra("gui");
        sanpham= (com.example.pizza_app.doi_tuong.sanpham) bundle.getSerializable("sp");
        Getid();
        ActionToolbar();
        Glide.with(this).load(sanpham.getHinhanh()).into(anh);
        ten.setText(sanpham.getTensp());
        gia.setText(sanpham.getGiasp());
        mota.setText(sanpham.getMota());
        mua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s(""+daco.get(0).getMakh());
                dialog(sanpham,daco.get(0).getMakh());
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Read().execute(""+1);
            }
        });
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(MainActivity_chitiet.this,MainActivity_giohang.class);
                startActivity(intent1);
            }
        });
    }
    public  void Getid(){
        toolbar=findViewById(R.id.toolbar_chitiet);
        anh=findViewById(R.id.img_chitiep);
        ten=findViewById(R.id.ten_chitiet);
        gia=findViewById(R.id.giatien_ct);
        mota=findViewById(R.id.mota);
        mua=findViewById(R.id.mua_ct);
        badge=(NotificationBadge) findViewById(R.id.menu_sl);
        giohang=findViewById(R.id.giohang_ct);
    }
    class Read extends AsyncTask<String,Integer,String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            s(""+s);
            super.onPostExecute(s);
        }
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
    private  void dialog( sanpham s,int tk){
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chitiet);
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
        TextView ten= dialog.findViewById(R.id.textView9);
        TextView gia= dialog.findViewById(R.id.gia);
        TextView soluong= dialog.findViewById(R.id.soluong);
        TextView tong= dialog.findViewById(R.id.tong);
        ImageView a= dialog.findViewById(R.id.imageView3);
        Button btn= dialog.findViewById(R.id.them);
        ImageView bc= dialog.findViewById(R.id.cong);
        ImageView bt= dialog.findViewById(R.id.tru);
        ten.setText(s.getTensp());
        gia.setText(s.getGiasp());
        soluong.setText(""+i);
        tong.setText(s.getGiasp());
        String[] array= s.getGiasp().split("k");
        g= Integer.parseInt(array[0]);
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                soluong.setText(""+i);
                t=g*i;
                tong.setText(""+t+"k");
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i>0){
                    i--;
                }
                soluong.setText(""+i);
                t=g*i;
                tong.setText(""+t+"k");
            }
        });
        Glide.with(this).load(s.getHinhanh()).into(a);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_chitiet.this);
                JSONObject jsonObject= new JSONObject();
                try {
                    jsonObject.put("giasp",s.getGiasp());
                    jsonObject.put("hinhanh",""+s.getHinhanh());
                    jsonObject.put("masp",s.getMasp());
                    jsonObject.put("matk",""+tk);
                    jsonObject.put("soluong",""+i);
                    jsonObject.put("tensp",s.getTensp());

                }catch (JSONException e){
                    e.printStackTrace();
                }
                JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(MainActivity_chitiet.this,"ok",Toast.LENGTH_LONG).show();
                                // Handle response
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });
                requestQueue.add(postRequest);
                s(""+1);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    // hien thi sa pham trong gio hang
    private void s(String a){
        String u="https://greatashbook27.conveyor.cloud/giohangs2/"+a;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                                String gia=object.getString("giasp");
                                String hinhanh=object.getString("hinhanh");
                                String masp=object.getString("masp");
                                int matk=Integer.parseInt(object.getString("matk"));
                                String soluong=object.getString("soluong");
                                String ten=object.getString("tensp");
                                // Xử lý thông tin đối tượng
                                listgiohang.add(new giohang(matk,masp,soluong,hinhanh,ten,gia));
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
        if (listgiohang==null){
            badge.setText(String.valueOf(0));
        }
        badge.setText(String.valueOf(listgiohang.size()));
    }
}