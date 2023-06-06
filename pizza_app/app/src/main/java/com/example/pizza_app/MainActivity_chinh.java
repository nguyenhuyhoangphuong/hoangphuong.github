package com.example.pizza_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.pizza_app.fragment.Fragment_home;
import com.example.pizza_app.fragment.Fragment_suport;
import com.example.pizza_app.fragment.Fragment_toi;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity_chinh extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chinh);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        replaceFragament(new Fragment_home());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        replaceFragament(new Fragment_home());
                        break;
                    case R.id.toi:
                        replaceFragament(new Fragment_toi());
                        break;
                    case R.id.sp:
                        replaceFragament(new Fragment_suport());
                        break;
                }
                return true;
            }
        });
    }
    private  void replaceFragament(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }
}