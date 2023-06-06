package com.example.pizza_app.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizza_app.R;
import com.example.pizza_app.database.TaikhoanDatabase;
import com.example.pizza_app.doi_tuong.taikhoan;
import com.example.pizza_app.doi_tuong.tinnhan;

import java.util.ArrayList;
import java.util.List;

public class rcv_tinnhan extends RecyclerView.Adapter<rcv_tinnhan.TinnhanViewholder> {
    List<tinnhan> thongbaoList;
    List<taikhoan> daco=new ArrayList<taikhoan>();
    public rcv_tinnhan(Context context) {
        this.context = context;
    }

    Context context;
    public void setData(List<tinnhan> list){
        this.thongbaoList=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TinnhanViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);
        return  new TinnhanViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TinnhanViewholder holder, int position) {
        tinnhan thongbao= thongbaoList.get(position);
        daco= TaikhoanDatabase.getInstance(context).data().getList();
        if(thongbao==null){
            return;
        }
        holder.nhan.setText(thongbao.getNoidung());
        if(thongbao.getManhan().contains(""+daco.get(0).getMakh())){
            holder.linearLayout.setGravity(Gravity.LEFT);
            holder.nhan.setBackgroundResource(R.drawable.bongchatden);
        }
        else {
            holder.linearLayout.setGravity(Gravity.RIGHT);
            holder.nhan.setBackgroundResource(R.drawable.bongchat);
        }
    }

    @Override
    public int getItemCount() {
        if(thongbaoList!=null){
            return thongbaoList.size();
        }
        return 0;
    }

    public class TinnhanViewholder extends RecyclerView.ViewHolder{
        private TextView nhan;
        private LinearLayout linearLayout;
        public TinnhanViewholder(@NonNull View itemView) {
            super(itemView);
            nhan = itemView.findViewById(R.id.nhantin);
            linearLayout=itemView.findViewById(R.id.linearchat);
        }
    }
}
