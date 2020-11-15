package id.ac.its.attendance.Response.Attendance;

import com.google.gson.annotations.SerializedName;

public class DataLoginAttendance {
    @SerializedName("nip")
    String nip;

    @SerializedName("nama")
    String nama;

    @SerializedName("token")
    String token;

    public String getNip() {
        return nip;
    }

    public String getNama() {
        return nama;
    }

    public String getToken() {
        return token;
    }
}
