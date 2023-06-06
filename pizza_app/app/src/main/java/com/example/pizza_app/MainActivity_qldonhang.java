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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pizza_app.adapter.rcv_oder;
import com.example.pizza_app.doi_tuong.Oder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_qldonhang extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<Oder> list= new ArrayList<Oder>();
    rcv_oder rcv_oder;
    ArrayAdapter arrayAdapter;
    String tt="";
    String url="https://greatashbook27.conveyor.cloud/api/hoadonbans";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_qldonhang);
        toolbar=findViewById(R.id.toolbarqldonhang);
        recyclerView=findViewById(R.id.dsdonhang);
        rcv_oder=new rcv_oder(this, new rcv_oder.ItemClickListener() {
            @Override
            public void onItemClick(Oder oder) {
                Suaoder(oder);
            }
        });
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(MainActivity_qldonhang.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager1);
        ListAdd_oder();
        rcv_oder.setData(list);
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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_qldonhang.this);
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
                                String diachi=object.getString("diachi");
                                String ghichu=object.getString("ghichu");
                                String mahdb=object.getString("mahdb");
                                int makh=object.getInt("makh");
                                String ngaydat=object.getString("ngaydat");
                                String tinhtrang=object.getString("tinhtrang");
                                // Xử lý thông tin đối tượng
                                list.add(new Oder(mahdb,tinhtrang,ghichu,diachi,ngaydat,makh));
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
    //
    private void Suaoder(Oder oder){
        List<String> list= new ArrayList<>();
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_suaoder);
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
        // anh xa
        TextView madonhang=dialog.findViewById(R.id.madonhang);
        madonhang.setText(oder.getMahdb());
        Spinner spinner=dialog.findViewById(R.id.tinhtrangql);
        Button xong=dialog.findViewById(R.id.xacnhantinhtrang);
        list.add(oder.getTinhtrang());
        list.add("Đang xác nhận");
        list.add("Đang giao hàng");
        list.add("Đang vận chuyển");
        list.add("Đã nhận hàng");
        list.add("Hủy đơn");
        arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tt=spinner.getSelectedItem().toString();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //
        xong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_qldonhang.this);
                JSONObject jsonObject= new JSONObject();
                try {
                    jsonObject.put("diachi",oder.getDiachi());
                    jsonObject.put("ghichu",oder.getGhichu());
                    jsonObject.put("mahdb",oder.getMahdb());
                    jsonObject.put("makh",oder.getMakh());
                    jsonObject.put("ngaydat",oder.getNgaydat());
                    jsonObject.put("tinhtrang",tt);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, url+"/"+oder.getMahdb(), jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(MainActivity_qldonhang.this,"Done",Toast.LENGTH_LONG).show();
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
            }
        });
        dialog.show();
    }
}