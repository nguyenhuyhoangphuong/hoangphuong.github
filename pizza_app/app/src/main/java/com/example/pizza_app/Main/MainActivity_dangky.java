package com.example.pizza_app.Main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pizza_app.MainActivity_chitiet;
import com.example.pizza_app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Random;

public class MainActivity_dangky extends AppCompatActivity {
    Toolbar toolbar;
    EditText taikhoan,matkhau,hoten,sdt,diachi;
    TextView ngaysinh;
    Button dangky;
    String gender="nam";
    RadioGroup radioGroup;
    String url="https://greatashbook27.conveyor.cloud/api/khachhangs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dangky);
        toolbar=findViewById(R.id.toolbar_dangky);
        GetID();
        ngaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar canlendar= Calendar.getInstance();
                int ngay = canlendar.get(Calendar.DATE);
                int thang = canlendar.get(Calendar.MONDAY);
                int nam = canlendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog= new DatePickerDialog(MainActivity_dangky.this, new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        canlendar.set(i,i1,i2);
                        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
                        ngaysinh.setText(simpleDateFormat.format(canlendar.getTime()));
                    }
                },nam,thang,ngay);
                datePickerDialog.show();
            }
        });
        ActionToolbar();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = findViewById(checkedId);
                gender = checkedRadioButton.getText().toString();
                Toast.makeText(MainActivity_dangky.this, "Giới tính: " + gender, Toast.LENGTH_SHORT).show();
            }
        });
        //xu ly dang ky
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_dangky.this);
                String a,b,c,d,e1,g;
                a=diachi.getText().toString();
                b=matkhau.getText().toString();
                c=ngaysinh.getText().toString();
                d=sdt.getText().toString();
                e1=hoten.getText().toString();
                g=taikhoan.getText().toString();
                Random random = new Random();
                int randomNumber = random.nextInt(10000);
                JSONObject jsonObject= new JSONObject();
                try {
                    jsonObject.put("Gioitinh",gender);
                    jsonObject.put("diachi",""+a);
                    jsonObject.put("makh",""+randomNumber);
                    jsonObject.put("mk",b);
                    jsonObject.put("namsinh",c);
                    jsonObject.put("sodienthoai",d);
                    jsonObject.put("tenkh",e1);
                    jsonObject.put("tk",g);
                    jsonObject.put("vaitro","Khách hàng");
                }catch (JSONException e){
                    e.printStackTrace();
                }
                JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(MainActivity_dangky.this,"Đăng ký thành công",Toast.LENGTH_LONG).show();
                                // Handle response
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(MainActivity_dangky.this,"Yêu cầu nhập đủ thông tin",Toast.LENGTH_LONG).show();
                    }
                });
                requestQueue.add(postRequest);
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
    private void GetID(){
        taikhoan=findViewById(R.id.tk_dangky);
        matkhau=findViewById(R.id.mk_dangky);
        hoten=findViewById(R.id.ten);
        sdt=findViewById(R.id.sdt);
        diachi=findViewById(R.id.diachi);
        ngaysinh=findViewById(R.id.ngaysinh);
        radioGroup=findViewById(R.id.radioGroup);
        dangky=findViewById(R.id.dangky1);
    }
}