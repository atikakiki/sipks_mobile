package id.ac.its.attendance.Response.DetailTransfer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataDetail {
    @SerializedName("title")
    Title title;

    @SerializedName("body")
    List<Body> body;

    public Title getTitle() {
        return title;
    }

    public List<Body> getBody() {
        return body;
    }

    @SerializedName("rekening")
    Rekening rekening;

    public Rekening getRekening() {
        return rekening;
    }
}
