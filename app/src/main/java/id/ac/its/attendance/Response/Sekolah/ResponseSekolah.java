package id.ac.its.attendance.Response.Sekolah;

import com.google.gson.annotations.SerializedName;

public class ResponseSekolah {
    @SerializedName("message")
    String Pesan;

    @SerializedName("data")
    DataSekolah dataSekolah;

    public String getPesan() {
        return Pesan;
    }

    public DataSekolah getDataSekolah() {
        return dataSekolah;
    }
}
