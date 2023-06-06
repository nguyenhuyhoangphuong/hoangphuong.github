package com.example.pizza_app.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizza_app.MainActivity_chinh;
import com.example.pizza_app.R;
import com.example.pizza_app.database.TaikhoanDatabase;
import com.example.pizza_app.doi_tuong.taikhoan;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView,textView1;
    int time =1500;
    List<taikhoan> daco= new ArrayList<taikhoan>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
        daco= TaikhoanDatabase.getInstance(MainActivity.this).data().getList();
        textView=findViewById(R.id.tv);
        textView1=findViewById(R.id.textView);
        Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.nhay);
        textView.startAnimation(animation1);
        Animation animation2= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        textView1.startAnimation(animation2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(daco.size()<=0){
                    Intent intent1= new Intent(MainActivity.this,MainActivity2.class );
                    startActivity(intent1);
                    finish();
                }else {
                    Intent intent= new Intent(MainActivity.this, MainActivity_chinh.class);
                    Toast.makeText(MainActivity.this, "Chào mừng "+daco.get(0).getTenkh(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }
        },time);
    }
}