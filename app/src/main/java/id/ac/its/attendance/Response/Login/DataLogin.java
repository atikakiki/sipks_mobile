package id.ac.its.attendance.Response.Login;

import com.google.gson.annotations.SerializedName;

public class DataLogin {
    @SerializedName("nip")
    String nip;

    @SerializedName("nama")
    String nama;

    @SerializedName("token")
    String token;

    @SerializedName("posisi")
    String posisi;

    @SerializedName("imei")
    String imei;

    public String getImei() {
        return imei;
    }

    public String getNip() {
        return nip;
    }

    public String getNama() {
        return nama;
    }

    public String getToken() {
        return token;
    }

    public String getPosisi() {
        return posisi;
    }
}
