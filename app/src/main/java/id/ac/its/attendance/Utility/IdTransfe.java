package id.ac.its.attendance.Utility;

public class IdTransfe {

    String kode_pekerjaan;
    String id_pekerjaan;
    String tujuan;
    String jumlah;

    public IdTransfe(String kode_pekerjaan, String id_pekerjaan, String tujuan, String jumlah) {
        this.kode_pekerjaan = kode_pekerjaan;
        this.id_pekerjaan = id_pekerjaan;
        this.tujuan = tujuan;
        this.jumlah = jumlah;
    }

    public void setKode_pekerjaan(String kode_pekerjaan) {
        this.kode_pekerjaan = kode_pekerjaan;
    }

    public void setId_pekerjaan(String id_pekerjaan) {
        this.id_pekerjaan = id_pekerjaan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getKode_pekerjaan() {
        return kode_pekerjaan;
    }

    public String getId_pekerjaan() {
        return id_pekerjaan;
    }

    public String getTujuan() {
        return tujuan;
    }

    public String getJumlah() {
        return jumlah;
    }
}
