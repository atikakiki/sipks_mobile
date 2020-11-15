package id.ac.its.attendance.Response.Response1Transfer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response1Transfer {
    @SerializedName("message")
    String pesan;

    @SerializedName("data")
    List<Data1Transfer> data1Transfers;

    public String getPesan() {
        return pesan;
    }

    public List<Data1Transfer> getData1Transfers() {
        return data1Transfers;
    }
}
