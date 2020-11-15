package id.ac.its.attendance.Response.DetailTransfer;

import com.google.gson.annotations.SerializedName;

public class Title {
    @SerializedName("judul_pekerjaan")
    String nama_kegiatan;

    @SerializedName("no_bku")
    String no_bku;

    @SerializedName("kode_pekerjaan")
    String kode_pekerjaan;

    public String getNama_kegiatan() {
        return nama_kegiatan;
    }

    public String getNo_bku() {
        return no_bku;
    }

    public String getKode_pekerjaan() {
        return kode_pekerjaan;
    }
}
