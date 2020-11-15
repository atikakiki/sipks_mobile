package id.ac.its.attendance.Response.BendaharaGaji;

import com.google.gson.annotations.SerializedName;

public class BendaharaGaji {
    @SerializedName("message")
    String pesan;

    @SerializedName("data")
    DataGaji dataGaji;

    public String getPesan() {
        return pesan;
    }

    public DataGaji getDataGaji() {
        return dataGaji;
    }
}
