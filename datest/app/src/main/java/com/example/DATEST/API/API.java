package com.example.DATEST.API;

import com.example.DATEST.models.chon;
import com.example.DATEST.models.danh_muc;
import com.example.DATEST.models.donhang;
import com.example.DATEST.models.giohang;
import com.example.DATEST.models.san_pham;
import com.example.DATEST.models.taikhoan;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    API API = new Retrofit.Builder().baseUrl("https://newtealgrape19.conveyor.cloud/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build().create(API.class);
    //Mục tài khoản
    @POST("api/tk/Posttk")//Thêm tài khoản
    Call<List<taikhoan>> post_tk(@Body taikhoan taikhoan);
    @GET("api/tk/gettt/{id}")// lấy dữ liệu từ id tài khoản
    Call<taikhoan> gettt(@Path("id") int id);

    @GET("api/tk/kiemtra/{taikhoan}/{mk}")//Hàm kiểm tra tài khoản
    Call<List<taikhoan>> kiemtra(@Path("taikhoan") String taikhoan, @Path("mk") String mk);

    @PUT("api/tk/Puttk/{id}") //Hàm sửa tài khoản
    Call<taikhoan> Puttk(@Path("id") int id, @Body taikhoan taikhoan);

    @GET("api/danhmuc/getall")// Lấy tất cả dữ liệu danh mục
    Call<List<danh_muc>> Get_Danh_muc();

    @GET("api/san_pham/getall") //Lấy tất cả dữ liệu sản phẩm
    Call<List<san_pham>> Get_san_pham();

    @GET("api/san_pham/tksanpham")//thống kê tổng sản phẩm
    Call<Integer>gettk();
    @GET("api/ThongKeTaiKhoanTheoSoLuong/{Account}")// thống kê tổng đơn hàng
    Call<String>gettkdh(@Path("Account") int Account);
    @GET("api/san_pham/getall_dm/{id}")//Lấy tất cả dữ liệu sản phẩm từ danh mục
    Call<List<san_pham>> Get_all_dm(@Path("id") int id);

    @GET("api/san_pham/getchitiet/{id}") //Lấy dữ liệu chi tiết sản phẩm
    Call<san_pham> getchitiet(@Path("id") int id);
    @GET("api/giohang/Getgiohang/{id}") // Lấy dữ liệu giỏ hàng
    Call<giohang> Getgiohang(@Path("id") int id);

    @GET("api/san_pham/timkiem/{id}") // Tìm kiếm sản phẩm
    Call<List<san_pham>> gettimkiem(@Path("id") String id);

    @GET("api/san_pham/timkiemgia") // Tìm kiếm sản phẩm theo giá
    Call<List<san_pham>> gettimkiemgia(@Path("id") int id);
    @GET("api/chon/Getall_chon/{id}") // Lấy hết dữ liệu phẩn chọn tất cả sản phẩm
    Call<List<chon>> Getall_chon(@Path("id") String id);


    @GET("api/giohang/getallsp/{id}/{ghichu}")//Lấy tất cả dữ liệu sản phẩm trong giỏ hàng
    Call<List<giohang>> getallsp(@Path("id") int id,@Path("ghichu") String ghichu);

    @GET("api/giohang/tongtien/{id}/{ghichu}")// Lấy dữ liệu tổng tiền
    Call<String> tongtien(@Path("id") int id,@Path("ghichu") String ghichu);

    @GET("api/giohang/tongsl/{id}/{ghichu}")// Lấy dữ liệu tổng số lượng sản phẩm
    Call<String> tongsl(@Path("id") int id,@Path("ghichu") String ghichu);

    @DELETE("api/giohang/Deletegiohang/{id}") // Xóa sản phẩm
    Call<giohang> Deletegiohang(@Path("id") int id);

    @POST("api/giohang/addgiohang")// Thêm giỏ hàng
    Call<giohang> addgiohang(@Body giohang giohang);

    @PUT("api/giohang/Putgiohang/{id}")// Sửa giỏ hàng
    Call<giohang> Putgiohang(@Path("id") int id, @Body giohang giohang);

    @POST("api/donhang/Postdonhang")// Thêm đơn hàng
    Call<donhang> Postdonhang( @Body donhang donhang);
    @PUT("api/donhang/Putdonhang/{id}") // Sửa đơn hàng
    Call<donhang> Putdonhang(@Path("id") int id , @Body donhang donhang);
    @GET("api/donhang/getadhllsp/{id}/{hanhchinh}")// Lấy dữ liệu đơn hàng
    Call<List<donhang>> getadhllsp(@Path("id") int id , @Path("hanhchinh") String hanhchinh);
}
