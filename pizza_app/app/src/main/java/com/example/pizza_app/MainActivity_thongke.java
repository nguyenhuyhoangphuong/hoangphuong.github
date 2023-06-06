package com.example.pizza_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pizza_app.adapter.adapterSanpham;
import com.example.pizza_app.doi_tuong.sanpham;
import com.skydoves.progressview.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_thongke extends AppCompatActivity {
    Toolbar toolbar;
    Button tinhtongma,theothang;
    Spinner spinner;
    ArrayAdapter arrayAdapter;
    String chon="";
    String url="https://greatashbook27.conveyor.cloud/";
    List<String> dsmasp=new ArrayList<>();
    List<sanpham> list=new ArrayList<sanpham>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thongke);
        toolbar=findViewById(R.id.toolbarqlthongke);
        tinhtongma=findViewById(R.id.chonma);
        theothang=findViewById(R.id.chonngaythongke);
        spinner=findViewById(R.id.masptk);
        ListAdd_sp();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chon=spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tinhtongma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThongKemasp(chon);
            }
        });
        theothang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TKthang();
            }
        });
        ActionToolbar();
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
    private  void ListAdd_sp(){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_thongke.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url+"api/sanphams", null,
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
                                dsmasp.add(masp);
                                arrayAdapter= new ArrayAdapter(MainActivity_thongke.this, android.R.layout.simple_spinner_item,dsmasp);
                                spinner.setAdapter(arrayAdapter);
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
   // thong ke san pham
    private  void ThongKemasp(String ma){
        RequestQueue queue = Volley.newRequestQueue(MainActivity_thongke.this);
        StringRequest stringRequestxoa = new StringRequest(Request.Method.GET, url+"thongkemasp/"+ma,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Xử lý dữ liệu trả về ở đây nếu cần thiết
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_thongke.this);
                        builder.setTitle("Thống kê sản phẩm: "+ma);   // Đặt tiêu đề cho dialog
                        builder.setMessage(response);  // Đặt nội dung cho dialog
                        builder.setCancelable(false);   // Đặt dialog không thể huỷ bỏ bằng phím Back
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Xử lý sự kiện khi người dùng nhấn vào nút OK
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi ở đây nếu có
            }
        });
        queue.add(stringRequestxoa);
    }
    // theo thang
    private void TKthang(){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_thongke);
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
        //
        TextView tv1=dialog.findViewById(R.id.tv1);
        TextView tv2=dialog.findViewById(R.id.tv2);
        TextView tv3=dialog.findViewById(R.id.tv3);
        TextView tv4=dialog.findViewById(R.id.tv4);

        ProgressView pro1=dialog.findViewById(R.id.pro1);
        ProgressView pro2=dialog.findViewById(R.id.pro2);
        ProgressView pro3=dialog.findViewById(R.id.pro3);
        ProgressView pro4=dialog.findViewById(R.id.pro4);

        TextView thang1=dialog.findViewById(R.id.thang1);
        TextView thang2=dialog.findViewById(R.id.thang2);
        TextView thang3=dialog.findViewById(R.id.thang3);
        TextView thang4=dialog.findViewById(R.id.thang4);

        pro1.setProgress(100);
        pro2.setProgress(20);
        pro3.setProgress(50);
        pro4.setProgress(10);
        RequestQueue queue = Volley.newRequestQueue(MainActivity_thongke.this);
        StringRequest stringRequestxoa = new StringRequest(Request.Method.GET, url+"thongk/"+5,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Xử lý dữ liệu trả về ở đây nếu cần thiết
                        String[] kq=response.split("/");
                        pro1.setProgress(Float.parseFloat(kq[1]));
                        pro2.setProgress(Float.parseFloat(kq[2]));
                        pro3.setProgress(Float.parseFloat(kq[3]));
                        pro4.setProgress(Float.parseFloat(kq[4]));

                        tv1.setText(kq[1]+" K");
                        tv2.setText(kq[2]+" K");
                        tv3.setText(kq[3]+" K");
                        tv4.setText(kq[4]+" K");

                        thang1.setText("Tháng 5");
                        thang2.setText("Tháng 4");
                        thang3.setText("Tháng 3");
                        thang4.setText("Tháng 2");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi ở đây nếu có
            }
        });
        queue.add(stringRequestxoa);
        //
        dialog.show();
    }
}