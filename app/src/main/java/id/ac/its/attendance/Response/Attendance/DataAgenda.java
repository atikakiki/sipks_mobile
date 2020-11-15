package id.ac.its.attendance.Response.Attendance;

import com.google.gson.annotations.SerializedName;

public class DataAgenda {
    @SerializedName("idAgenda")
    String id;

    @SerializedName("namaAgenda")
    String nama;

    @SerializedName("singkatAgenda")
    String namaSingkat;

    @SerializedName("ruangan")
    DataRuangan ruangan;

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public DataRuangan getRuangan() {
        return ruangan;
    }

    public String getNamaSingkat() {
        return namaSingkat;
    }

}
