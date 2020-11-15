package id.ac.its.attendance.Response.Bendahara;

import com.google.gson.annotations.SerializedName;

public class BendaharaResponse {
    @SerializedName("message")
    String pesan;

    @SerializedName("data")
    DataBendahara data;

    public String getPesan() {
        return pesan;
    }

    public DataBendahara getData() {
        return data;
    }
}
