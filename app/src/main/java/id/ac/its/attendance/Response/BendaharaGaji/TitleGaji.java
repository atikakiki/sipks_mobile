package id.ac.its.attendance.Response.BendaharaGaji;

import com.google.gson.annotations.SerializedName;

public class TitleGaji {
    @SerializedName("kode_pekerjaan")
    String kode_pekerjaan;

    @SerializedName("judul_pekerjaan")
    String judul;

    @SerializedName("no_bku")
    String no_bku;

    public String getKode_pekerjaan() {
        return kode_pekerjaan;
    }

    public String getJudul() {
        return judul;
    }

    public String getNo_bku() {
        return no_bku;
    }
}
