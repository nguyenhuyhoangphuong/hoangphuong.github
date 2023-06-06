package com.example.pizza_app.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.pizza_app.MainActivity_chitiet;
import com.example.pizza_app.MainActivity_giohang;
import com.example.pizza_app.MainActivity_oder;
import com.example.pizza_app.R;
import com.example.pizza_app.adapter.adapterSanpham;
import com.example.pizza_app.adapter.rcv_loai;
import com.example.pizza_app.adapter.rcv_sp;
import com.example.pizza_app.doi_tuong.loai_pizza;
import com.example.pizza_app.doi_tuong.sanpham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Fragment_home extends Fragment {

    RecyclerView recyclerView,recyclerView1;
    ViewFlipper viewFlipper;
    AutoCompleteTextView autoCompleteTextView;
    List<loai_pizza> loai_pizzas=new ArrayList<loai_pizza>();
    List<sanpham> sanphamList= new ArrayList<sanpham>();
    List<sanpham> sanphamLoc= new ArrayList<sanpham>();
    rcv_loai rcv_loai;
    rcv_sp rcv_sp;
    ImageView giohang;
    TextView hoat;
    adapterSanpham adapterSanpham;
    String url="https://greatashbook27.conveyor.cloud/api/sanphams";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home,container,false);
        //
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView1=view.findViewById(R.id.rv_sp);
        viewFlipper=view.findViewById(R.id.vf);
        autoCompleteTextView=view.findViewById(R.id.autoComplete);
        hoat=view.findViewById(R.id.textView5);
        hoat.setTranslationY(-300);
        hoat.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        giohang=view.findViewById(R.id.gio_hang);
        giohang.setTranslationY(-300);
        giohang.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        //
        //  san pham
        rcv_sp= new rcv_sp(getContext(), new rcv_sp.ItemClickListener() {
            @Override
            public void onItemClick(sanpham sanpham) {
                Intent intent=new Intent(getContext(), MainActivity_chitiet.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("sp", sanpham);
                intent.putExtra("gui",bundle);
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView1.setLayoutManager(linearLayoutManager1);
        rcv_sp.setData(sanphamList);
        ListAdd_sp();
        recyclerView1.setAdapter(rcv_sp);
        //
        ActionViewFlipper();
        // loai sp
        rcv_loai= new rcv_loai(getContext(), new rcv_loai.ItemClickListener() {
            @Override
            public void onItemClick(loai_pizza loai_pizza) {
                sanphamLoc.clear();
                for(sanpham s: sanphamList){
                    if(s.getLoaisp().contains(loai_pizza.getTenloai())){
                        sanphamLoc.add(s);
                    }
                }
                rcv_sp.setData(sanphamLoc);
                recyclerView1.setAdapter(rcv_sp);
                rcv_sp.notifyDataSetChanged();
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ListAdd();
        recyclerView.setAdapter(rcv_loai);
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), MainActivity_giohang.class);
                startActivity(intent);
            }
        });
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sanpham s= (sanpham) adapterView.getItemAtPosition(i);
                Intent intent=new Intent(getContext(), MainActivity_chitiet.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("sp", s);
                intent.putExtra("gui",bundle);
                startActivity(intent);
            }
        });

        return view;
    }
    // chay quang cao
    private void ActionViewFlipper(){
        List<String> quangcao= new ArrayList<>();
        quangcao.add("https://img.freepik.com/premium-psd/food-sale-instagram-post-design-template_72785-797.jpg?w=2000");
        quangcao.add("https://storage.googleapis.com/scrabbl-com.appspot.com/5719776026427392");
        quangcao.add("https://media-cdn.tripadvisor.com/media/photo-s/16/1b/ca/36/yard-sale-pizza-clapton.jpg");
        for(int i=0; i<quangcao.size();i++){
            ImageView imageView= new ImageView(getContext());
            Glide.with(getContext()).load(quangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation view_in= AnimationUtils.loadAnimation(getContext(),R.anim.test);
        Animation view_out= AnimationUtils.loadAnimation(getContext(),R.anim.test_out);
        viewFlipper.setInAnimation(view_in);
        viewFlipper.setOutAnimation(view_out);
    }
    // loai
    private  void ListAdd(){
      loai_pizzas.add(new loai_pizza("Pizza","https://cdn-icons-png.flaticon.com/128/7592/7592171.png"));
      loai_pizzas.add(new loai_pizza("Burger","https://cdn-icons-png.flaticon.com/128/877/877951.png"));
      loai_pizzas.add(new loai_pizza("Drink","https://cdn-icons-png.flaticon.com/512/2405/2405479.png"));
      loai_pizzas.add(new loai_pizza("Hotdog","https://cdn-icons-png.flaticon.com/128/2102/2102354.png"));
      loai_pizzas.add(new loai_pizza("Potato","https://cdn-icons-png.flaticon.com/128/1046/1046786.png"));
      loai_pizzas.add(new loai_pizza("Chicken","https://cdn-icons-png.flaticon.com/128/2884/2884642.png"));
      rcv_loai.setData(loai_pizzas);
    }
    //san pham
    private  void ListAdd_sp(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                                String donvi=object.getString("donvi");
                                String hinhanh=object.getString("hinhanh");
                                String maloai=object.getString("maloai");
                                String masp=object.getString("masp");
                                String mota=object.getString("mota");
                                String tensp=object.getString("tensp");
                                // Xử lý thông tin đối tượng
                                sanphamList.add(new sanpham(masp,tensp,maloai,donvi,mota,hinhanh));
                                rcv_sp.notifyDataSetChanged();
                                adapterSanpham= new adapterSanpham(getContext(),R.layout.item_sanpham1,sanphamList);
                                autoCompleteTextView.setAdapter(adapterSanpham);
                                autoCompleteTextView.setThreshold(1);
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