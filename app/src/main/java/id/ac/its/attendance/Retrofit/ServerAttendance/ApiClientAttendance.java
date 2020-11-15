package id.ac.its.attendance.Retrofit.ServerAttendance;

import id.ac.its.attendance.Response.Attendance.ResponseAgendaAttendance;
import id.ac.its.attendance.Response.Attendance.ResponseApi;
import id.ac.its.attendance.Response.Attendance.ResponseLoginAttendance;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiClientAttendance {
    @FormUrlEncoded
    @POST("/sendImg/")
    Call<ResponseApi> kirim(@Field("idUser") String nrp,
                            @Field("password") String password,
                            @Field("image") String image);
    @FormUrlEncoded
    @POST("/sendSignature/")
    Call<ResponseApi> sendSignature(@Field("idUser") String nrp,
                                    @Field("password") String password,
                                    @Field("image") String image);
    @FormUrlEncoded
    @POST("/doTrain/")
    Call<ResponseApi> train(@Field("idUser") String nrp,
                            @Field("password") String password);
    @FormUrlEncoded
    @POST("/doTrain_TTD/")
    Call<ResponseApi> trainTTD(@Field("idUser") String nrp,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("/doPredict_TTD/")
    Call<ResponseApi> predictTTD(@Field("idUser") String nrp,
                                 @Field("password") String password,
                                 @Field("image") String image);
    @FormUrlEncoded
    @POST("/doPredict/")
    Call<ResponseApi> predict(@Field("idUser") String nrp,
                              @Field("password") String password,
                              @Field("image") String image);


    @FormUrlEncoded
    @POST("/signin/")
    Call<ResponseApi> signin(@Field("idUser") String user,
                             @Field("password") String password,
                             @Field("Lat") String Lat,
                             @Field("Lon") String Lon,
                             @Field("idAgenda") String idAgenda,
                             @Field("image") String image);
    @FormUrlEncoded
    @POST("/signin_TTD/")
    Call<ResponseApi> signinTTD(@Field("idUser") String user,
                                @Field("password") String password,
                                @Field("Lat") String Lat,
                                @Field("Lon") String Lon,
                                @Field("idAgenda") String idAgenda,
                                @Field("image") String image);

    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<ResponseLoginAttendance> login(@Field("nip") String nip,
                                        @Field("password") String password);

    @GET("/api/v1/agenda")
    Call<ResponseAgendaAttendance> getAgenda();

    @FormUrlEncoded
    @POST("/api/v1/kirimgambar")
    Call<ResponseApi> kirimgambar(@Field("nip") String nip,
                                  @Field("images") String image,
                                  @Header("Authorization") String token);
}
