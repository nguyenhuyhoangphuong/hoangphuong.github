package com.example.pizza_app.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pizza_app.MainActivity_chinh;
import com.example.pizza_app.MainActivity_giohang;
import com.example.pizza_app.MainActivity_lichsu;
import com.example.pizza_app.MainActivity_oder;
import com.example.pizza_app.MainActivity_settting;
import com.example.pizza_app.R;
import com.example.pizza_app.database.TaikhoanDatabase;
import com.example.pizza_app.doi_tuong.taikhoan;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_toi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_toi extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_toi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_toi.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_toi newInstance(String param1, String param2) {
        Fragment_toi fragment = new Fragment_toi();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    Button cart,oder,lichsu,setting;
    TextView ten,vaitro;
    List<taikhoan> daco= new ArrayList<taikhoan>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_toi,container,false);
        cart=view.findViewById(R.id.giohang_user);
        oder=view.findViewById(R.id.oder);
        lichsu=view.findViewById(R.id.lichsu);
        ten=view.findViewById(R.id.ten_uses);
        vaitro=view.findViewById(R.id.vaitro);
        setting=view.findViewById(R.id.setting);
        daco= TaikhoanDatabase.getInstance(getContext()).data().getList();
        ten.setText(daco.get(0).getTenkh());
        vaitro.setText(daco.get(0).getVaitro());
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(), MainActivity_giohang.class);
                startActivity(intent);
            }
        });
        oder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(), MainActivity_oder.class);
                startActivity(i);
            }
        });
        lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getContext(), MainActivity_lichsu.class);
                startActivity(i1);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(getContext(), MainActivity_settting.class);
                startActivity(i2);
            }
        });
        return view;
    }
}