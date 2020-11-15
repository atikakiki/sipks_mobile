package id.ac.its.attendance.Response.Response1Transfer;

import com.google.gson.annotations.SerializedName;

public class Data1Transfer {
    @SerializedName("sum")
    String total;

    @SerializedName("no_bku")
    String bku;

    @SerializedName("judul_pekerjaan")
    String judul;

    @SerializedName("nama_penyedia")
    String penyedia;

    @SerializedName("kode_pekerjaan")
    String kode;

    @SerializedName("status_approve")
    String status_approve;

    public String getStatus_approve() {
        return status_approve;
    }

    public String getKode() {
        return kode;
    }

    public String getTotal() {
        return total;
    }

    public String getBku() {
        return bku;
    }

    public String getJudul() {
        return judul;
    }

    public String getPenyedia() {
        return penyedia;
    }
}
