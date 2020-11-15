package id.ac.its.attendance.Response.BendaharaBarang;

import com.google.gson.annotations.SerializedName;

public class ResponseBendaharaBarang {
    @SerializedName("message")
    String pesan;

    @SerializedName("data")
    Update update;

    public String getPesan() {
        return pesan;
    }

    public Update getUpdate() {
        return update;
    }
}
