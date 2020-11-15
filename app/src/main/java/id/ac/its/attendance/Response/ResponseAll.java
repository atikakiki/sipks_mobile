package id.ac.its.attendance.Response;

import com.google.gson.annotations.SerializedName;

public class ResponseAll {
    @SerializedName("message")
    String pesan;

    public String getPesan() {
        return pesan;
    }
}
