package com.example.pizza_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.pizza_app.Main.MainActivity;
import com.example.pizza_app.adapter.adapterGiohang;
import com.example.pizza_app.adapter.rcv_giohang;
import com.example.pizza_app.database.TaikhoanDatabase;
import com.example.pizza_app.doi_tuong.HoantienEvent;
import com.example.pizza_app.doi_tuong.giohang;
import com.example.pizza_app.doi_tuong.sanpham;
import com.example.pizza_app.doi_tuong.taikhoan;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity_giohang extends AppCompatActivity {
    Toolbar toolbar;
    List<taikhoan> daco= new ArrayList<taikhoan>();
    RecyclerView recyclerView;
    rcv_giohang rcv_giohang;
    TextView tb;
    String pt="Trực tiếp";
    int g;

    Button thanhtoan;
    List<giohang> list= new ArrayList<giohang>();
    List<giohang> chonmua= new ArrayList<giohang>();
    adapterGiohang adapterGiohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_giohang);
        toolbar=findViewById(R.id.toolbar_giohang);
        recyclerView=findViewById(R.id.rcv_giohang);
        daco= TaikhoanDatabase.getInstance(MainActivity_giohang.this).data().getList();
        tb=findViewById(R.id.tb);
        thanhtoan=findViewById(R.id.pay);
        ActionToolbar();
        rcv_giohang= new rcv_giohang(MainActivity_giohang.this);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(MainActivity_giohang.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager1);
        rcv_giohang.setdata(list);
        recyclerView.setAdapter(rcv_giohang);
        s(""+daco.get(0).getMakh());
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPay();
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
    private void s(String a){
        String u="https://greatashbook27.conveyor.cloud/giohangs2/"+a;
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_giohang.this);
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
                                list.add(new giohang(matk,masp,soluong,hinhanh,ten,gia));
                                rcv_giohang.notifyDataSetChanged();
                                if (list.size()!=0){
                                    tb.setHeight(0);
                                }
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
    private void DialogPay(){
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_dathang);
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
        // anh xa dialog
        EditText magiamgia,tennhan,diachinhan,sdtnhan;
        magiamgia=dialog.findViewById(R.id.ma_giam_gia);
        tennhan=dialog.findViewById(R.id.ten_nguoi_nhan);
        diachinhan=dialog.findViewById(R.id.diachi_nhan);
        sdtnhan=dialog.findViewById(R.id.sdt_nhan);
        Button xacnhan;
        LinearLayout tructiep,nganhang;
        tructiep=dialog.findViewById(R.id.tttructipe);
        nganhang=dialog.findViewById(R.id.ttatm);
        tructiep.setBackgroundResource(R.drawable.khung_sp2);
        tructiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tructiep.setBackgroundResource(R.drawable.khung_sp2);
                nganhang.setBackgroundResource(R.drawable.khung_sp);
                pt="Trực tiếp";
            }
        });
        nganhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nganhang.setBackgroundResource(R.drawable.khung_sp2);
                tructiep.setBackgroundResource(R.drawable.khung_sp);
                pt="Ngân hàng";
            }
        });
        xacnhan= dialog.findViewById(R.id.xacnhan);
        TextView tong_don;
        tong_don=dialog.findViewById(R.id.tong_dathang);
        ListView hangdat;
        hangdat=dialog.findViewById(R.id.sanphanmua);
        // lay thong tin khach hang
        tennhan.setText(daco.get(0).getTenkh());
        sdtnhan.setText(daco.get(0).getSdt());
        diachinhan.setText(daco.get(0).getDiachi());
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        float so_tien=0;
        //
        adapterGiohang= new adapterGiohang(this,R.layout.item_dat,chonmua);
        if(chonmua.size()>0){
            for(int i=0;i<chonmua.size();i++){
                String[] array= chonmua.get(i).getGiasp().split("k");
                so_tien=so_tien+ (Integer.parseInt(array[0])*Integer.parseInt(chonmua.get(i).getSoluong()));
            }
            hangdat.setAdapter(adapterGiohang);
        }
        tong_don.setText(""+so_tien+"k");
        //
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng được đánh số từ 0 -> 11, nên phải cộng thêm 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String currentDate = month + "/" + day + "/" + year;
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    GetHdb(daco.get(0).getDiachi(),pt,""+randomNumber,
                            ""+daco.get(0).getMakh(),currentDate,"Đang xác nhận",chonmua);
                    dialog.dismiss();
            }
        });
        dialog.show();
    }
    // tao hoa don ban
    private void GetHdb(String diachi,String ghichu,String mahdb, String makh,String ngaydat, String tinhtrang,List<giohang> sp){
        String url="https://greatashbook27.conveyor.cloud/api/hoadonbans";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_giohang.this);
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("diachi",diachi);
            jsonObject.put("ghichu",ghichu);
            jsonObject.put("mahdb",mahdb);
            jsonObject.put("makh",makh);
            jsonObject.put("ngaydat",ngaydat);
            jsonObject.put("tinhtrang",tinhtrang);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MainActivity_giohang.this,"Đơn hàng đã được khởi tạo",Toast.LENGTH_LONG).show();
                        // Handle response
                        GetCTHdb(sp,mahdb);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        });
        requestQueue.add(postRequest);
    }
    // tao chi tiet hoa don ban
    private void GetCTHdb(List<giohang> sp,String mahdb){
        g=0;
        for(int i=0;i<sp.size();i++){
            String u="https://greatashbook27.conveyor.cloud/api/chitiethoadonbans";
            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_giohang.this);
            JSONObject jsonObject= new JSONObject();
            try {
                jsonObject.put("dongia",sp.get(i).getGiasp());
                jsonObject.put("mahdb",mahdb);
                jsonObject.put("masp",sp.get(i).getMasp());
                jsonObject.put("soluong",sp.get(i).getSoluong());
            }catch (JSONException e){
                e.printStackTrace();
            }
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, u, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle response
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle error
                }
            });
            requestQueue.add(postRequest);
            String a=""+ daco.get(0).getMakh()+","+sp.get(i).getMasp();
            String url1="https://greatashbook27.conveyor.cloud/xoagiohangs2/"+a;
            RequestQueue queue = Volley.newRequestQueue(MainActivity_giohang.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Xử lý dữ liệu trả về ở đây nếu cần thiết
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Xử lý lỗi ở đây nếu có
                }
            });
            queue.add(stringRequest);
        }
    }
    //bat xu kien checkbox
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public  void giohang(giohang event){
        int h=1;
        if(event!=null){
            String[] array= event.getGiasp().split("k");
//            tong=tong+ (Integer.parseInt(array[0])*Integer.parseInt(event.getSoluong()));
            for(int i=0;i<chonmua.size();i++){
                if(chonmua.get(i).getMasp()==event.getMasp()){
                    h++;
                }
            }
            if(h==1){
                chonmua.add(new giohang(event.getMa(),event.getMasp(),
                        event.getSoluong(),event.getHinhanh(),event.getTensp(),event.getGiasp()));
            }
        }
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void eventHoantien(HoantienEvent event){
        if(event!=null){
            String[] array= event.getGiasp().split("k");
//            tong=tong-(Integer.parseInt(array[0])*Integer.parseInt(event.getSoluong()));
            for(int i=0;i<chonmua.size();i++){
                if(chonmua.get(i).getMasp()==event.getMasp()){
                    chonmua.remove(i);
                }
            }
        }
    }

}