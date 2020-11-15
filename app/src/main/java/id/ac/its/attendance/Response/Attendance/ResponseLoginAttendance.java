package id.ac.its.attendance.Response.Attendance;

import com.google.gson.annotations.SerializedName;

public class ResponseLoginAttendance {
    @SerializedName("message")
    String pesan;

    @SerializedName("data")
    DataLoginAttendance data;

    public String getMessage() {
        return pesan;
    }

    public DataLoginAttendance getData() {
        return data;
    }
}
