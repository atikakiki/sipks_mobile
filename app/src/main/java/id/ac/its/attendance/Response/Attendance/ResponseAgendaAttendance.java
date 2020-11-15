package id.ac.its.attendance.Response.Attendance;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseAgendaAttendance {
    @SerializedName("data")
    ArrayList<DataAgenda> data;

    public ArrayList<DataAgenda> getData() {
        return data;
    }
}
