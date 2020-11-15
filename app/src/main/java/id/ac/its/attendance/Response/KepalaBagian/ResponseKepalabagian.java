package id.ac.its.attendance.Response.KepalaBagian;

import com.google.gson.annotations.SerializedName;

public class ResponseKepalabagian {
    @SerializedName("message")
    String pesan;

    @SerializedName("data")
    DataKepalabagian dataKepalabagian;

    public String getPesan() {
        return pesan;
    }

    public DataKepalabagian getDataKepalabagian() {
        return dataKepalabagian;
    }
}
