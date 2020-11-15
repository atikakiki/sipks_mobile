package id.ac.its.attendance.Response.DetailTransfer;

import com.google.gson.annotations.SerializedName;

public class Rekening {
    @SerializedName("no_rekening")
    String no_rekenig;

    public String getNo_rekenig() {
        return no_rekenig;
    }
}
