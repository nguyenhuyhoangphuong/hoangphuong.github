package com.example.pizza_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.pizza_app.Main.MainActivity2;
import com.example.pizza_app.Main.MainActivity_dangky;
import com.example.pizza_app.database.TaikhoanDatabase;
import com.example.pizza_app.doi_tuong.taikhoan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainActivity_settting extends AppCompatActivity {
    Toolbar toolbar;
    String ma="";
    Button thongtin,quanly,dangxuat;
    String gender="nam";
    String url="https://greatashbook27.conveyor.cloud/api/khachhangs";
    List<taikhoan> daco= new ArrayList<taikhoan>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_settting);
        toolbar=findViewById(R.id.toolbar_setting);
        thongtin=findViewById(R.id.thongtincn);
        quanly=findViewById(R.id.ql);
        dangxuat=findViewById(R.id.exit);
        daco= TaikhoanDatabase.getInstance(MainActivity_settting.this).data().getList();
        ma=""+daco.get(0).getMakh();
        ActionToolbar();
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaikhoanDatabase.getInstance(MainActivity_settting.this).data().deleteTK(ma);
                Intent intent=new Intent(MainActivity_settting.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
        thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_sua(daco.get(0));
            }
        });
        quanly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daco.get(0).getVaitro().contains("admin")){
                    Intent intent1=new Intent(MainActivity_settting.this, MainActivity_qly.class);
                    startActivity(intent1);
                }else {
                    Toast.makeText(MainActivity_settting.this, "Chức năng không phải của bạn", Toast.LENGTH_SHORT).show();
                }
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
    private void Dialog_sua(taikhoan taikhoan){
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // thay layout dialog vào đây
        dialog.setContentView(R.layout.dialog_thongtin);
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
        // anh xa o đay
        EditText tk,mk,ten,sdt,diachi;
        TextView namsinh;
        RadioGroup radioGroup;
        radioGroup=dialog.findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = findViewById(checkedId);
                gender = checkedRadioButton.getText().toString();
            }
        });
        Button huy,luu;
        huy=dialog.findViewById(R.id.huy_sua);
        luu=dialog.findViewById(R.id.luu_sua);
        tk=dialog.findViewById(R.id.tk_sua);
        mk=dialog.findViewById(R.id.mk_sua);
        ten=dialog.findViewById(R.id.ten_sua);
        sdt=dialog.findViewById(R.id.sdt_sua);
        diachi=dialog.findViewById(R.id.diachi_sua);
        namsinh=dialog.findViewById(R.id.namsinh_sua);
        tk.setText(taikhoan.getTk());
        mk.setText(taikhoan.getMk());
        ten.setText(taikhoan.getTenkh());
        sdt.setText(taikhoan.getSdt());
        diachi.setText(taikhoan.getDiachi());
        namsinh.setText(taikhoan.getNamsinh());
        namsinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar canlendar= Calendar.getInstance();
                int ngay = canlendar.get(Calendar.DATE);
                int thang = canlendar.get(Calendar.MONDAY);
                int nam = canlendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog= new DatePickerDialog(MainActivity_settting.this, new DatePickerDialog.OnDateSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        canlendar.set(i,i1,i2);
                        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
                        namsinh.setText(simpleDateFormat.format(canlendar.getTime()));
                    }
                },nam,thang,ngay);
                datePickerDialog.show();
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_settting.this);
                String a,b,c,d,e1,g;
                a=diachi.getText().toString();
                b=mk.getText().toString();
                c=namsinh.getText().toString();
                d=sdt.getText().toString();
                e1=ten.getText().toString();
                g=tk.getText().toString();
                taikhoan taikhoan1=new taikhoan(taikhoan.getMakh(),e1,g,b,gender,c,a,d,taikhoan.getVaitro());
                JSONObject jsonObject= new JSONObject();
                try {
                    jsonObject.put("Gioitinh",gender);
                    jsonObject.put("diachi",""+a);
                    jsonObject.put("makh",""+taikhoan.getMakh());
                    jsonObject.put("mk",b);
                    jsonObject.put("namsinh",c);
                    jsonObject.put("sodienthoai",d);
                    jsonObject.put("tenkh",e1);
                    jsonObject.put("tk",g);
                    jsonObject.put("vaitro",taikhoan.getVaitro());
                }catch (JSONException e){
                    e.printStackTrace();
                }
                JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PUT, url+"/"+taikhoan.getMakh(), jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(MainActivity_settting.this,"Sửa thành công",Toast.LENGTH_LONG).show();
                                // Handle response
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });
                requestQueue.add(postRequest);
                TaikhoanDatabase.getInstance(MainActivity_settting.this).data().deleteTK(ma);
                TaikhoanDatabase.getInstance(MainActivity_settting.this).data().insert(taikhoan1);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}