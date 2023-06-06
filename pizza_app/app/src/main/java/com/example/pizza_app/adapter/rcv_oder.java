package com.example.pizza_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pizza_app.R;
import com.example.pizza_app.doi_tuong.Oder;
import com.example.pizza_app.doi_tuong.sanpham;

import java.util.List;

public class rcv_oder  extends RecyclerView.Adapter<rcv_oder.ViewHoler_oder> {
    public rcv_oder(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }
    List<Oder> oderList;
    public void setData(List<Oder> oderList){
        this.oderList=oderList;
        notifyDataSetChanged();
    }
    Context context;
    private rcv_oder.ItemClickListener itemClickListener;
    @NonNull
    @Override
    public ViewHoler_oder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oder,parent,false);
        return new rcv_oder.ViewHoler_oder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler_oder holder, int position) {
        Oder oder=oderList.get(position);
        holder.mahdb.setText(oder.getMahdb());
        holder.thoigian.setText(oder.getNgaydat());
        holder.tinhtrang.setText(oder.getTinhtrang());
        holder.diachi.setText(oder.getDiachi());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(oder);
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
    public class ViewHoler_oder extends RecyclerView.ViewHolder{
        TextView mahdb,thoigian,diachi,tinhtrang;
        public ViewHoler_oder(@NonNull View itemView) {
            super(itemView);
            mahdb=itemView.findViewById(R.id.mahdb);
            thoigian=itemView.findViewById(R.id.thoigian);
            diachi=itemView.findViewById(R.id.diachi_oder);
            tinhtrang=itemView.findViewById(R.id.tinhtrang);
        }
    }
}
