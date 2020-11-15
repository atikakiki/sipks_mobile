package id.ac.its.attendance.Response.Login;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
    @SerializedName("message")
    String pesan;

    @SerializedName("data")
    DataLogin data;

    public String getPesan() {
        return pesan;
    }

    public DataLogin getData() {
        return data;
    }
}
