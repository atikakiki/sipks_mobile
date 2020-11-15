package id.ac.its.attendance.Response.KepalaBagian;

import com.google.gson.annotations.SerializedName;

public class DataKepalabagian {
    @SerializedName("rek_bos")
    String rek_bos;

    @SerializedName("rek_bopda")
    String rek_bopda;

    public String getRek_bos() {
        return rek_bos;
    }

    public String getRek_bopda() {
        return rek_bopda;
    }
}
