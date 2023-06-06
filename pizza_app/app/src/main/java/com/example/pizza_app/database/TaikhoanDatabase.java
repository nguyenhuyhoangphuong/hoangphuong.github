package com.example.pizza_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pizza_app.doi_tuong.taikhoan;

@Database(entities = {taikhoan.class},version = 1)
public  abstract class TaikhoanDatabase  extends RoomDatabase {
    private static final String data_name="taikhoan.db";
    private  static TaikhoanDatabase instance;
    public static synchronized TaikhoanDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),TaikhoanDatabase.class,data_name)
                    .allowMainThreadQueries()
                    .build();
        }
        return  instance;
    }
    public  abstract database data();
}
