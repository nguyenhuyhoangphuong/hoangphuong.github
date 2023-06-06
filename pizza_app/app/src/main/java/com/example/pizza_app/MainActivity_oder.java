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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pizza_app.adapter.rcv_oder;
import com.example.pizza_app.database.TaikhoanDatabase;
import com.example.pizza_app.doi_tuong.Oder;
import com.example.pizza_app.doi_tuong.Oder_ct;
import com.example.pizza_app.doi_tuong.sanpham;
import com.example.pizza_app.doi_tuong.taikhoan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity_oder extends AppCompatActivity {
    Toolbar toolbar;
    rcv_oder rcv_oder;
    RecyclerView recyclerView;
    int t=0;
    String d;
    List<sanpham> sanphamList=new ArrayList<sanpham>();
    List<Oder>  oderList=new ArrayList<Oder>();
    List<taikhoan> daco= new ArrayList<taikhoan>();
    String thay="https://greatashbook27.conveyor.cloud/api/hoadonbans";
    String url="https://greatashbook27.conveyor.cloud/hoadb/";
    String url1="https://greatashbook27.conveyor.cloud/api/sanphams";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_oder);
        daco= TaikhoanDatabase.getInstance(MainActivity_oder.this).data().getList();
        toolbar=findViewById(R.id.toolbar_oder);
        recyclerView=findViewById(R.id.rcv_oder);
        ListAdd_sp();
        rcv_oder=new rcv_oder(this, new rcv_oder.ItemClickListener() {
            @Override
            public void onItemClick(Oder oder) {
                CToder(oder);
            }
        });
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(MainActivity_oder.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager1);
        ListAdd_oder();
        rcv_oder.setData(oderList);
        recyclerView.setAdapter(rcv_oder);
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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_oder.this);
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
                                rcv_oder.notifyDataSetChanged();
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
    private void  CToder(Oder oder){
        List<Oder_ct> list=new ArrayList<Oder_ct>();
        d="";
        t=0;
        final Dialog dialog= new Dialog(MainActivity_oder.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_oder);
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

        // anh xa
        TextView tong,dschitiet,thanhtoan,ten,sdt,diachi;
        Button huy,xacnhan;
        tong= dialog.findViewById(R.id.tong_chitiet);
        dschitiet=dialog.findViewById(R.id.dschitiet);
        thanhtoan=dialog.findViewById(R.id.ttchitiet);
        ten=dialog.findViewById(R.id.tenct);
        sdt=dialog.findViewById(R.id.dtct);
        diachi=dialog.findViewById(R.id.diachict);
        ten.setText(daco.get(0).getTenkh());
        sdt.setText(daco.get(0).getSdt());
        diachi.setText(daco.get(0).getDiachi());
        huy=dialog.findViewById(R.id.huybo);
        xacnhan=dialog.findViewById(R.id.danhan);
        thanhtoan.setText(oder.getGhichu());
        // lay thong tin chi tiet don hang
        String u="https://greatashbook27.conveyor.cloud/chitiethdb/"+oder.getMahdb();
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_oder.this);
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
                                String dongia=object.getString("dongia");
                                String mahdb=object.getString("mahdb");
                                String masp=object.getString("masp");
                                int soluong=object.getInt("soluong");
                                // Xử lý thông tin đối tượng
                                list.add(new Oder_ct(masp,mahdb,dongia,""+soluong));
                                String[] array= dongia.split("k");
                                t=t+(Integer.parseInt(array[0])*soluong);
                                tong.setText(t+"K");
                                // ...
                                for(int l=0;l<sanphamList.size();l++){
                                    if(masp.equals(sanphamList.get(l).getMasp())){
                                        d=d+"- "+sanphamList.get(l).getTensp()+"("+sanphamList.get(l).getGiasp()+")-Số lượng: "+soluong+"\n";
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dschitiet.setText(d);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi khi gửi yêu cầu đến server
            }
        });
        requestQueue.add(jsonArrayRequest);
        //
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(oder.getTinhtrang().contains("Đang giao hàng")){
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_oder.this);
                    JSONObject jsonObject= new JSONObject();
                    try {
                        jsonObject.put("diachi",oder.getDiachi());
                        jsonObject.put("ghichu",oder.getGhichu());
                        jsonObject.put("mahdb",oder.getMahdb());
                        jsonObject.put("makh",oder.getMakh());
                        jsonObject.put("ngaydat",oder.getNgaydat());
                        jsonObject.put("tinhtrang","Đã nhận hàng");
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, thay+"/"+oder.getMahdb(), jsonObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(MainActivity_oder.this,"Done",Toast.LENGTH_LONG).show();
                                    // Handle response
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                        }
                    });
                    requestQueue.add(postRequest);
                    dialog.dismiss();
                }else {
                    dialog.dismiss();
                }
            }
        });
        //
        dialog.show();
    }
    private  void ListAdd_sp(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url1, null,
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
                                sanphamList.add(new sanpham(masp,tensp,maloai,donvi,mota,hinhanh));
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