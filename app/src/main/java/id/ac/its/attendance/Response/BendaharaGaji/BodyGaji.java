package id.ac.its.attendance.Response.BendaharaGaji;

import com.google.gson.annotations.SerializedName;

public class BodyGaji {
    @SerializedName("kode_pekerjaan")
    String kode_pekerjaan;

    @SerializedName("id")
    String id;

    @SerializedName("detail_komponen")
    String detail_komponen;

    @SerializedName("satuan")
    String satuan;

    @SerializedName("volume")
    String volume;

    @SerializedName("no_rekening")
    String no_rekening;

    @SerializedName("komponen_hasil_kali")
    String jumlah;

    @SerializedName("status_approve")
    String status_approve;

    public String getStatus_approve() {
        return status_approve;
    }

    public String getKode_pekerjaan() {
        return kode_pekerjaan;
    }

    public String getId() {
        return id;
    }

    public String getDetail_komponen() {
        return detail_komponen;
    }

    public String getSatuan() {
        return satuan;
    }

    public String getVolume() {
        return volume;
    }

    public String getNo_rekening() {
        return no_rekening;
    }

    public String getJumlah() {
        return jumlah;
    }
}
