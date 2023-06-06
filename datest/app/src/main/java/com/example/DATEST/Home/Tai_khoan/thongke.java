package com.example.DATEST.Home.Tai_khoan;

import static com.example.DATEST.Home.Home.chipNavigationBar;
import static com.example.DATEST.Home.Home.idtk;
import static com.example.DATEST.xuly.kiemtra_tt;
import static com.example.DATEST.xuly.on_back1;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DATEST.API.API;
import com.example.DATEST.Home.Home;
import com.example.DATEST.Home.Trang_chu.sp_adapter;
import com.example.DATEST.Home.Trang_chu.trang_chu;
import com.example.DATEST.R;
import com.example.DATEST.models.san_pham;
import com.github.mikephil.charting.charts.PieChart;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class thongke extends Fragment {
    ImageView imgview;
    PieChart pieChart;
    TextView tvtksp,tvtkdh;
    AlertDialog.Builder resultBuilder;
    int idsp;
    ArrayList<san_pham> san_phams=new ArrayList<>();

 public thongke(){

 }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thongke, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgview = view.findViewById(R.id.img_backtk);
        tvtksp = view.findViewById(R.id.tksanpham);
        tvtkdh = view.findViewById(R.id.tkdonhang);
        getdata();
        chipNavigationBar.setVisibility(View.GONE);

        Home home = (Home) getActivity();
        on_back1(home, this, R.id.action_thongke_to_taikhoan2);
        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    chipNavigationBar.setVisibility(View.VISIBLE);
                    Navigation.findNavController(view).navigate(R.id.action_thongke_to_taikhoan2);
            }
        });
    }

    private void getdata() {
     //thống kê tổng sản phẩm đang có
     API.API.gettk().enqueue(new Callback<Integer>() {
         @Override
         public void onResponse(Call<Integer> call, Response<Integer> response) {
             if (response.isSuccessful()) {
                 tvtksp.setText("Tổng sản phẩm: " + response.body());
             } else {
                 // Xử lý lỗi nếu có
             }
         }

         @Override
         public void onFailure(Call<Integer> call, Throwable t) {

         }
     });
     //thống kê tổng sản phẩm đã giao
     API.API.gettkdh(idtk).enqueue(new Callback<String>() {
         @Override
         public void onResponse(Call<String> call, Response<String> response) {
             if (response.isSuccessful()) {
                 tvtkdh.setText("Tổng sản phẩm: " + response.body());
             } else {
                 // Xử lý lỗi nếu có
             }
         }

         @Override
         public void onFailure(Call<String> call, Throwable t) {

         }
     });
    }
}
