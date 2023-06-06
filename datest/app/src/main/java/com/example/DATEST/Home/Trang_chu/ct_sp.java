package com.example.DATEST.Home.Trang_chu;

import static com.example.DATEST.Home.Home.chipNavigationBar;
import static com.example.DATEST.Home.Home.currencyVN;
import static com.example.DATEST.Home.Home.idtk;
import static com.example.DATEST.xuly.dia;
import static com.example.DATEST.xuly.on_back1;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.DATEST.API.API;
import com.example.DATEST.API.quantilist;
import com.example.DATEST.API.gettien;
import com.example.DATEST.Home.Home;
import com.example.DATEST.R;
import com.example.DATEST.models.chon;
import com.example.DATEST.models.giohang;
import com.example.DATEST.models.san_pham;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ct_sp extends Fragment {
    RecyclerView recy_3, recy_4, recy_7;
    chon_adpater chon_adpater;
    ArrayList<chon> chons;
    ImageView image, img_back, cong, tru;
    TextView tensp, giatiensp, tongtien, sl;
    gettien gettien;
    quantilist quantilist;
    int tong1;
    int tong2 = 0;
    int tong3 = 1;
    int tong4 = 0;
    int tongloaichon = 0;
    int tongsize = 0;
    int idsp;
    String loai = "", vanchuyen = "";
    LinearLayout layout1;
    ArrayList<String> arrayLis1 = new ArrayList<>();
    Dialog dialog;

    public ct_sp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ct_sp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhxa(view);
        chipNavigationBar.setVisibility(View.GONE);
        getdata();
        Home home = (Home) getActivity();
        int vitri = getArguments().getInt("vitri");
        switch (vitri) {
            case 0:
                on_back1(home, this, R.id.action_ct_sp_to_trang_chu);
                break;
            case 1:

                break;

        }
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (vitri) {
                    case 0:
                        chipNavigationBar.setVisibility(View.VISIBLE);
                        chipNavigationBar.setItemSelected(R.id.home, true);
                        Navigation.findNavController(view).navigate(R.id.action_ct_sp_to_trang_chu);
                        break;
                    case 1:

                }
            }
        });
        kiemtra();
        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tong3 = tong3 + 1;
                sl.setText(tong3 + "");
                tong2 = tong1 + tongsize + tongloaichon;
                tong4 = tong2 * tong3;
                tongtien.setText("Tổng tiền: " + currencyVN.format(tong4));
            }
        });
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tong3 != 1) {
                    tong3 = tong3 - 1;
                    tong2 = tong1 + tongsize + tongloaichon;
                    tong4 = tong2 * tong3;
                    sl.setText(tong3 + "");
                    tongtien.setText("Tổng tiền: " + currencyVN.format(tong4));
                }
            }
        });

    }

    private void kiemtra() {
        quantilist = new quantilist() {
            @Override
            public void onlist(ArrayList<String> arrayList) {
                Log.e("Chọn loại chọn", arrayList.toString());
                arrayLis1 = arrayList;
            }

            @Override
            public void onlist1(int tong) {
                tongloaichon = tong;
                tong2 = tong1 + tongsize + tongloaichon;
                tong4 = tong2 * tong3;
                tongtien.setText("Tổng tiền: " + currencyVN.format(tong4));
            }
        };
    }

    private void getdata() {
        idsp = getArguments().getInt("idsp");

        API.API.getchitiet(idsp).enqueue(new Callback<san_pham>() {
            @Override
            public void onResponse(Call<san_pham> call, Response<san_pham> response) {
                if (response.isSuccessful()) {
                    san_pham san_pham = response.body();
                    Glide.with(getContext()).load(san_pham.getHinhanh()).error(R.drawable.img1).into(image);
                    tensp.setText(san_pham.getTensp());
                    giatiensp.setText(currencyVN.format(Integer.parseInt(san_pham.getGiatien())));
                    tong1 = Integer.parseInt(san_pham.getGiatien());
                    tong2 = tong1;
                    tongtien.setText("Tổng tiền: " + currencyVN.format(tong1));
                }
            }

            @Override
            public void onFailure(Call<san_pham> call, Throwable t) {

            }
        });
        API.API.Getall_chon("Chọn bản").enqueue(new Callback<List<chon>>() {
            @Override
            public void onResponse(Call<List<chon>> call, Response<List<chon>> response) {
                if (response.isSuccessful()) {

                    gettien = new gettien() {
                        @Override
                        public void test(String tong) {
                            loai = tong;
                            ongiohang();
                            Log.e("Chọn bản", loai);
                        }

                        @Override
                        public void test1(String tien) {

                        }
                    };
                    chons = (ArrayList<chon>) response.body();
                    recy_3.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    chon_adpater = new chon_adpater(chons, "Chọn bản", recy_3, quantilist, gettien);
                    recy_3.setAdapter(chon_adpater);


                }

            }

            @Override
            public void onFailure(Call<List<chon>> call, Throwable t) {
                Log.e("loi", "" + t);
            }
        });
        API.API.Getall_chon("Chọn vận chuyển").enqueue(new Callback<List<chon>>() {
            @Override
            public void onResponse(Call<List<chon>> call, Response<List<chon>> response) {
                if (response.isSuccessful()) {
                    gettien = new gettien() {
                        @Override
                        public void test(String tong) {
                            vanchuyen = tong;
                            ongiohang();
                            Log.e("Chọn vận chuyển", vanchuyen);
                        }

                        @Override
                        public void test1(String tien) {
                            tongsize = Integer.parseInt(tien);
                            tong2 = tong1 + tongsize + tongloaichon;
                            tong4 = tong2 * tong3;
                            tongtien.setText("Tổng tiền: " + currencyVN.format(tong4));
                        }
                    };
                    chons = (ArrayList<chon>) response.body();
                    recy_4.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    chon_adpater = new chon_adpater(chons, "Chọn vận chuyển", recy_4, quantilist, gettien);
                    recy_4.setAdapter(chon_adpater);


                }

            }

            @Override
            public void onFailure(Call<List<chon>> call, Throwable t) {
                Log.e("loi", "" + t);
            }
        });
        API.API.Getall_chon("Chọn màu bản in").enqueue(new Callback<List<chon>>() {
            @Override
            public void onResponse(Call<List<chon>> call, Response<List<chon>> response) {
                if (response.isSuccessful()) {
                    chons = (ArrayList<chon>) response.body();
                    Log.e("Chọn màu bản in", "" + chons.toString());
                    recy_7.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    chon_adpater = new chon_adpater(chons, "Chọn màu bản in", recy_7, quantilist, gettien);
                    recy_7.setAdapter(chon_adpater);

                }

            }

            @Override
            public void onFailure(Call<List<chon>> call, Throwable t) {
                Log.e("loi", "" + t);
            }
        });

    }

    public void ongiohang() {

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                if (loai.isEmpty() || vanchuyen.isEmpty()) {
                    check(loai, "Vui lòng chọn loại");
                    check(vanchuyen, "Vui lòng chọn vận chuyển");
                } else {
                    String a = arrayLis1.toString().replaceAll("[\\[\\]]", "");
                    giohang giohang = new giohang(0,idtk, idsp, tong3 , loai + ", " + vanchuyen + ", " + a, "" + tong2, tong4, "không");
                    API.API.addgiohang(giohang).enqueue(new Callback<com.example.DATEST.models.giohang>() {
                        @Override
                        public void onResponse(Call<com.example.DATEST.models.giohang> call, Response<com.example.DATEST.models.giohang> response) {
                            if (response.isSuccessful()) {
                                dialog.dismiss();
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                chipNavigationBar.setVisibility(View.VISIBLE);
                                Navigation.findNavController(view).navigate(R.id.action_ct_sp_to_trang_chu);
                            } else {
                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<com.example.DATEST.models.giohang> call, Throwable t) {
                            Log.e("lỗi thêm giỏ hang", t.getMessage());
                        }
                    });
                }
            }
        });


    }

    private void check(String loai, String s) {
        if (loai.isEmpty()) {
            Toast.makeText(getContext(), "" + s, Toast.LENGTH_SHORT).show();
        }
    }

    private void anhxa(View view) {
        recy_3 = view.findViewById(R.id.recy_3);
        recy_4 = view.findViewById(R.id.recy_4);
        recy_7 = view.findViewById(R.id.recy_7);
        image = view.findViewById(R.id.image);
        layout1 = view.findViewById(R.id.layout1);
        tensp = view.findViewById(R.id.tensp);
        tongtien = view.findViewById(R.id.tongtien);
        giatiensp = view.findViewById(R.id.giatiensp);
        img_back = view.findViewById(R.id.img_back);
        cong = view.findViewById(R.id.cong);
        tru = view.findViewById(R.id.tru);
        sl = view.findViewById(R.id.sl);
        dialog = new Dialog(getContext());
        dia(dialog);
    }

}