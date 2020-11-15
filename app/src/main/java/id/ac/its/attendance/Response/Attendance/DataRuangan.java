package id.ac.its.attendance.Response.Attendance;

import com.google.gson.annotations.SerializedName;

public class DataRuangan {
    @SerializedName("nama")
    String nama;

    @SerializedName("latitude")
    Double latitude;

    @SerializedName("longitude")
    Double longitude;

    public String getNama() {
        return nama;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
