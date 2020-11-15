package id.ac.its.attendance.Response.DetailTransfer;

import com.google.gson.annotations.SerializedName;

public class ResponseDetail {
    @SerializedName("message")
    String pesan;

    @SerializedName("data")
    DataDetail dataDetail;

    public String getPesan() {
        return pesan;
    }

    public DataDetail getDataDetail() {
        return dataDetail;
    }
}
