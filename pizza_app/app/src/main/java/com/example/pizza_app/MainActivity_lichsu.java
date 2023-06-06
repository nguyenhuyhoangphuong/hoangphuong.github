package com.example.pizza_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pizza_app.adapter.rcv_danhgia;
import com.example.pizza_app.adapter.rcv_lichsu;
import com.example.pizza_app.adapter.rcv_oder;
import com.example.pizza_app.database.TaikhoanDatabase;
import com.example.pizza_app.doi_tuong.Oder;
import com.example.pizza_app.doi_tuong.sanpham;
import com.example.pizza_app.doi_tuong.taikhoan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_lichsu extends AppCompatActivity {
    Toolbar toolbar;
    rcv_lichsu rcv_lichsu;
    RecyclerView recyclerView;
    List<Oder> oderList=new ArrayList<Oder>();
    List<taikhoan> daco= new ArrayList<taikhoan>();
    String url="https://greatashbook27.conveyor.cloud/hoadbls/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lichsu);
        toolbar=findViewById(R.id.toolbar_lichsu);
        recyclerView=findViewById(R.id.rcv_lichsu);
        daco= TaikhoanDatabase.getInstance(MainActivity_lichsu.this).data().getList();
        rcv_lichsu=new rcv_lichsu(this, new rcv_lichsu.ItemClickListener() {
            @Override
            public void onItemClick(Oder oder) {

            }
        });

        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(MainActivity_lichsu.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager1);
        ListAdd_oder();
        rcv_lichsu.setData(oderList);
        recyclerView.setAdapter(rcv_lichsu);
        ActionToolbar();
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
    private void ListAdd_oder(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_lichsu.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+daco.get(0).getMakh(), null,
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
                                String diachi=object.getString("diachi");
                                String ghichu=object.getString("ghichu");
                                String mahdb=object.getString("mahdb");
                                int makh=object.getInt("makh");
                                String ngaydat=object.getString("ngaydat");
                                String tinhtrang=object.getString("tinhtrang");
                                // Xử lý thông tin đối tượng
                                oderList.add(new Oder(mahdb,tinhtrang,ghichu,diachi,ngaydat,makh));
                                rcv_lichsu.notifyDataSetChanged();
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
}