package id.ac.its.attendance.Retrofit.ServerBankJatim;

import id.ac.its.attendance.Response.BendaharaBarang.ResponseBendaharaBarang;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiClientBankJatim {
    @FormUrlEncoded
    @POST("transfer")
    Call<ResponseBendaharaBarang> kirim(
            @Field("tujuan") String tujuan,
            @Field("asal") String asal,
            @Field("jumlah") String jumlah
    );
}
