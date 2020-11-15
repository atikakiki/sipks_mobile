package id.ac.its.attendance.Retrofit.ServerSIPKS;

import id.ac.its.attendance.Response.Bendahara.BendaharaResponse;
import id.ac.its.attendance.Response.BendaharaBarang.ResponseBendaharaBarang;
import id.ac.its.attendance.Response.BendaharaGaji.BendaharaGaji;
import id.ac.its.attendance.Response.DetailTransfer.ResponseDetail;
import id.ac.its.attendance.Response.KepalaBagian.ResponseKepalabagian;
import id.ac.its.attendance.Response.Response1Transfer.Response1Transfer;
import id.ac.its.attendance.Response.ResponseAll;
import id.ac.its.attendance.Response.Login.ResponseLogin;
import id.ac.its.attendance.Response.Sekolah.ResponseSekolah;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiClientSIPKS {
    @FormUrlEncoded
    @POST("sendTTD")
    Call<ResponseAll> ttd(
            @Field("admin") String admin,
            @Field("image") String gambar,
            @Field("nip") String nip,
            @Field("password") String pass
    );

    @FormUrlEncoded
    @POST("login")
    Call<ResponseLogin> login(
        @Field("nip") String nip,
        @Field("password") String password
    );


    @FormUrlEncoded
    @POST("sekolah")
    Call<ResponseSekolah> sekolah(
        @Field("nip") String nip,
        @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_kepala_1_tunai")
    Call<Response1Transfer> satutunai(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_kepala_1_transfer")
    Call<Response1Transfer> satutranfer(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_kepala_2_tunai")
    Call<Response1Transfer> duatunai(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_kepala_2_transfer")
    Call<Response1Transfer> duatransfer(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_kepala_3_tunai")
    Call<Response1Transfer> tigatunai(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_kepala_3_transfer")
    Call<Response1Transfer> tigatransfer(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_bendahara_1_tunai")
    Call<Response1Transfer> bendaharasatutunai(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_bendahara_1_transfer")
    Call<Response1Transfer> bendaharasatutransfer(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_bendahara_2_tunai")
    Call<Response1Transfer> bendaharaduatunai(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_bendahara_2_transfer")
    Call<Response1Transfer> bendaharaduatransfer(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_bendahara_3_tunai")
    Call<Response1Transfer> bendaharatigatunai(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_bendahara_3_transfer")
    Call<Response1Transfer> bendaharatigatransfer(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_detail_bendahara")
    Call<ResponseDetail> bendahara_detail(
            @Field("kode_pekerjaan") String kode_pekerjaan,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("bendahara")
    Call<BendaharaResponse> bendahara(
            @Field("nip") String nip,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("bendahara_update_barang")
    Call<ResponseBendaharaBarang> bendahara_barang(
            @Field("kode_pekerjaan") String kode_pekerjaan,
            @Field("dana") String dana,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_detail_bendahara_gaji")
    Call<BendaharaGaji> bendahara_detail_gaji(
            @Field("kode_pekerjaan") String kode_pekerjaan,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_bendahara_1_tunai1")
    Call<Response1Transfer> bendaharasatutunai1(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_bendahara_1_transfer1")
    Call<Response1Transfer> bendaharasatutransfer1(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_bendahara_2_tunai1")
    Call<Response1Transfer> bendaharaduatunai1(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_bendahara_2_transfer1")
    Call<Response1Transfer> bendaharaduatransfer1(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_bendahara_3_tunai1")
    Call<Response1Transfer> bendaharatigatunai1(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_bendahara_3_transfer1")
    Call<Response1Transfer> bendaharatigatransfer1(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("kepala_bagian")
    Call<ResponseKepalabagian> kepala_bagian(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_kepala_1_tunai1")
    Call<Response1Transfer> satutunai1(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_kepala_1_transfer1")
    Call<Response1Transfer> satutranfer1(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_kepala_2_tunai1")
    Call<Response1Transfer> duatunai1(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_kepala_2_transfer1")
    Call<Response1Transfer> duatransfer1(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_kepala_3_tunai1")
    Call<Response1Transfer> tigatunai1(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_kepala_3_transfer1")
    Call<Response1Transfer> tigatransfer1(
            @Field("unit_id") String id_sekolah,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("kepala_update_barang_tunai")
    Call<ResponseBendaharaBarang> kepala_update_barang_tunai(
            @Field("kode_pekerjaan") String kode_pekerjaan,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_detail_kepala")
    Call<ResponseDetail> kepala_detail(
            @Field("kode_pekerjaan") String kode_pekerjaan,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("pekerjaan_detail_kepala_gaji")
    Call<BendaharaGaji> kepala_detail_gaji(
            @Field("kode_pekerjaan") String kode_pekerjaan,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("kepala_update_gaji")
    Call<ResponseBendaharaBarang> kepala_update_gaji(
            @Field("kode_pekerjaan") String kode_pekerjaan,
            @Field("id_pekerjaan") String id_pekerjaan,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("kirimgambar")
    Call<ResponseAll> kirimgambar(
            @Field("nip") String nip,
            @Field("images") String image,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("aturimei")
    Call<ResponseBendaharaBarang> aturiemi(
            @Field("nip") String nip,
            @Field("imei") String imei,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("predict")
    Call<ResponseAll> predict(
            @Field("nip") String nip,
            @Field("images") String image,
            @Header("Authorization") String token
    );

    @FormUrlEncoded
    @POST("kirimttd")
    Call<ResponseAll> kirimttd(
            @Field("nip") String nip,
            @Field("images") String image,
            @Header("Authorization") String token
    );
}
