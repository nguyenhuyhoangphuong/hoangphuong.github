package com.example.pizza_app.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pizza_app.MainActivity_lichsu;
import com.example.pizza_app.MainActivity_oder;
import com.example.pizza_app.R;
import com.example.pizza_app.doi_tuong.Oder;
import com.example.pizza_app.doi_tuong.Oder_ct;
import com.example.pizza_app.doi_tuong.sanpham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class rcv_lichsu extends RecyclerView.Adapter<rcv_lichsu.lichsuViewholer> {
    public rcv_lichsu(Context context,rcv_lichsu.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.context = context;
    }

    String url1="https://greatashbook27.conveyor.cloud/api/sanphams";
    String d;
    private rcv_lichsu.ItemClickListener itemClickListener;
    List<sanpham> sp=new ArrayList<sanpham>();
    int t=0;
    List<sanpham> sanphamList=new ArrayList<sanpham>();

    List<Oder> oderList;
    public void setData(List<Oder> oderList){
        this.oderList=oderList;
        ListAdd_sp();
        notifyDataSetChanged();
    }
    Context context;
    @NonNull
    @Override
    public lichsuViewholer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lichsu,parent,false);
        return new rcv_lichsu.lichsuViewholer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull lichsuViewholer holder, int position) {
        Oder oder= oderList.get(position);
        holder.mahdb.setText(oder.getMahdb());
        holder.ngaydat.setText(oder.getNgaydat());
        holder.thanhtoan.setText(oder.getGhichu());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(oder);
            }
        });
        d="";
        t=0;
        sp.clear();
        List<Oder_ct> list=new ArrayList<Oder_ct>();
        // lay thong tin chi tiet don hang
        String u="https://greatashbook27.conveyor.cloud/chitiethdb/"+oder.getMahdb();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, u, null,
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
                                String dongia=object.getString("dongia");
                                String mahdb=object.getString("mahdb");
                                String masp=object.getString("masp");
                                int soluong=object.getInt("soluong");
                                // Xử lý thông tin đối tượng
                                list.add(new Oder_ct(masp,mahdb,dongia,""+soluong));
                                String[] array= dongia.split("k");
                                t=t+(Integer.parseInt(array[0])*soluong);
                                // ...
                                for(int l=0;l<sanphamList.size();l++){
                                    if(masp.equals(sanphamList.get(l).getMasp())){
                                        sp.add(sanphamList.get(l));
                                        d=d+"- "+sanphamList.get(l).getTensp()+"("+sanphamList.get(l).getGiasp()+")-Số lượng: "+soluong+"\n";
                                        holder.tong.setText(t+"K");
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        holder.thucdon.setText(d);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi khi gửi yêu cầu đến server
            }
        });
        requestQueue.add(jsonArrayRequest);
        holder.danhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DANHGIA(sp);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(oderList!=null){
            return oderList.size();
        }
        return 0;
    }
    public  interface  ItemClickListener{
        void onItemClick(Oder oder);
    }
    public class lichsuViewholer extends RecyclerView.ViewHolder{
        TextView mahdb,ten,sdt,diachi,thucdon,ngaydat,tong,thanhtoan;
        Button danhgia;
        public lichsuViewholer(@NonNull View itemView) {
            super(itemView);
            mahdb=itemView.findViewById(R.id.lsmahb);
            ten=itemView.findViewById(R.id.lsten);
            sdt=itemView.findViewById(R.id.lssdt);
            diachi=itemView.findViewById(R.id.lsdiachi);
            thucdon=itemView.findViewById(R.id.lsthucdon);
            ngaydat=itemView.findViewById(R.id.lsthoigian);
            tong=itemView.findViewById(R.id.lstong);
            thanhtoan=itemView.findViewById(R.id.lsthanhtoan);
            danhgia=itemView.findViewById(R.id.danhgia);
        }
    }
    private  void ListAdd_sp(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url1, null,
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
    //dialog danh gia
    private  void DANHGIA(List<sanpham> sanpham){
        rcv_danhgia rcv_danhgia;
        final Dialog dialog= new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_danhgia);
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
        //
        TextView noidung= dialog.findViewById(R.id.nhanxet);
        Button xong=dialog.findViewById(R.id.xong);
        RecyclerView recyclerView=dialog.findViewById(R.id.listdanggia);
        rcv_danhgia= new rcv_danhgia(context);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        rcv_danhgia.setData(sanpham);
        recyclerView.setAdapter(rcv_danhgia);
        xong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
