package com.example.pizza_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity_qly extends AppCompatActivity {
    Toolbar toolbar;
    Button qlsp,thongke,tuvan,donhang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_qly);
        Getid();
        ActionToolbar();
        qlsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity_qly.this, MainActivity_qlsanpham.class);
                startActivity(intent);
            }
        });
        tuvan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MainActivity_qly.this, MainActivity_tuvan.class);
                startActivity(intent1);
            }
        });
        // don hang
        donhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity_qly.this, MainActivity_qldonhang.class);
                startActivity(intent);
            }
        });
        thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity_qly.this,MainActivity_thongke.class);
                startActivity(intent);
            }
        });
    }
    private void Getid(){
        toolbar=findViewById(R.id.toolbarql);
        qlsp=findViewById(R.id.qlsanpham);
        thongke=findViewById(R.id.thongke);
        tuvan=findViewById(R.id.tinnhankh);
        donhang=findViewById(R.id.qldonhang);
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
}