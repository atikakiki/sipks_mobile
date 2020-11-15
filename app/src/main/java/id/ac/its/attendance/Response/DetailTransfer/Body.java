package id.ac.its.attendance.Response.DetailTransfer;

import com.google.gson.annotations.SerializedName;

public class Body {
    @SerializedName("id")
    String kode_pekerjaan;

    @SerializedName("satuan")
    String satuan;

    @SerializedName("volume")
    String volume;

    @SerializedName("komponen_hasil_kali_non_pajak")
    String bayarpajak;

    @SerializedName("komponen_hasil_kali")
    String bayar;

    @SerializedName("komponen_name")
    String nama_komponen;

    @SerializedName("detail_komponen")
    String detail_komponen;

    @SerializedName("status_approve")
    String status_approve;

    public String getStatus_approve() {
        return status_approve;
    }

    public String getDetail_komponen() {
        return detail_komponen;
    }

    public String getNama_komponen() {
        return nama_komponen;
    }

    public String getKode_pekerjaan() {
        return kode_pekerjaan;
    }

    public String getSatuan() {
        return satuan;
    }

    public String getVolume() {
        return volume;
    }

    public String getBayarpajak() {
        return bayarpajak;
    }

    public String getBayar() {
        return bayar;
    }
}
