package id.ac.its.attendance.Response.Sekolah;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataSekolah {
    @SerializedName("sekolah")
    List<Sekolah> daftar_sekolah;

    public List<Sekolah> getDaftar_sekolah() {
        return daftar_sekolah;
    }
}
