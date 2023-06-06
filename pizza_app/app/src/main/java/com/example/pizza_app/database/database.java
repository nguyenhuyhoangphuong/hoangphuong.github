package com.example.pizza_app.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pizza_app.doi_tuong.taikhoan;

import java.util.List;

@Dao
public interface database {
    @Insert
    void insert(taikhoan taikhoan);
    @Query("DELETE FROM taikhoan WHERE makh= :ma")
    Void deleteTK(String ma);
    @Query("SELECT*FROM taikhoan")
    List<taikhoan> getList();
}
