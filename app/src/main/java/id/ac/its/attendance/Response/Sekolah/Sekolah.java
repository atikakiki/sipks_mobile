package id.ac.its.attendance.Response.Sekolah;

import com.google.gson.annotations.SerializedName;

public class Sekolah {
    @SerializedName("unit_id")
    String id_sekolah;

    @SerializedName("unit_name")
    String nama_sekolah;

    public String getId_sekolah() {
        return id_sekolah;
    }

    public String getNama_sekolah() {
        return nama_sekolah;
    }
}
