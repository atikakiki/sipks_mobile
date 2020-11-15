package id.ac.its.attendance.Response.Bendahara;

import com.google.gson.annotations.SerializedName;

public class DataBendahara {
    @SerializedName("bos_id")
    String bos_id;

    @SerializedName("bos_sekolah")
    String bos_sekolah;

    @SerializedName("bopda_id")
    String bopda_id;

    @SerializedName("bopda_sekolah")
    String bopda_sekolah;

    @SerializedName("bos_rek")
    String rekening_bos;

    @SerializedName("bopda_rek")
    String rekening_bopda;

    public String getRekening_bos() {
        return rekening_bos;
    }

    public String getRekening_bopda() {
        return rekening_bopda;
    }

    public String getBos_id() {
        return bos_id;
    }

    public String getBos_sekolah() {
        return bos_sekolah;
    }

    public String getBopda_id() {
        return bopda_id;
    }

    public String getBopda_sekolah() {
        return bopda_sekolah;
    }
}
