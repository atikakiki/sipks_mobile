package id.ac.its.attendance.Response.BendaharaGaji;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataGaji {
    @SerializedName("title")
    TitleGaji titleGaji;

    @SerializedName("body")
    List<BodyGaji> bodyGajis;

    public TitleGaji getTitleGaji() {
        return titleGaji;
    }

    public List<BodyGaji> getBodyGajis() {
        return bodyGajis;
    }
}
