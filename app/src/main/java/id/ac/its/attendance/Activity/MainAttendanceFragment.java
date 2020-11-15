package id.ac.its.attendance.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.Attendance.DataAgenda;
import id.ac.its.attendance.Response.Attendance.DataRuangan;
import id.ac.its.attendance.Response.Attendance.ResponseAgendaAttendance;
import id.ac.its.attendance.Retrofit.ServerAttendance.ApiClientAttendance;
import id.ac.its.attendance.Retrofit.ServerAttendance.ServerAttendance;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainAttendanceFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x1;
    private static final int REQUEST_CHECK_SETTINGS_GPS = 0x2;
    private Spinner agendaSpinner;
    private ArrayList<String> agendaNama;
    private HashMap<String, DataAgenda> agendaMap;
    private Button btnGo;
    private GoogleApiClient googleApiClient;
    private Location mylocation;
    private Location ruangLoc;
    private String idAgenda;

    public MainAttendanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_attendance, container, false);

        agendaSpinner = view.findViewById(R.id.spinnerAgenda);
        btnGo = view.findViewById(R.id.btn_attendance_go);
        agendaNama = new ArrayList<>();
        agendaMap = new HashMap<>();

        getAgenda();

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idAgenda = agendaMap.get(agendaSpinner.getSelectedItem().toString()).getId();
                DataRuangan ruang = agendaMap.get(agendaSpinner.getSelectedItem().toString()).getRuangan();
                ruangLoc = new Location("");
                ruangLoc.setLatitude(ruang.getLatitude());
                ruangLoc.setLongitude(ruang.getLongitude());

                updateLocation(mylocation);
            }
        });

        if (checkPermissionsLocation()) {
            setUpGPSClient();
        }

        return view;
    }

    private void getAgenda() {
        ApiClientAttendance api = ServerAttendance.builder().create(ApiClientAttendance.class);
        Call<ResponseAgendaAttendance> getAgenda = api.getAgenda();
        getAgenda.enqueue(new Callback<ResponseAgendaAttendance>() {
            @Override
            public void onResponse(Call<ResponseAgendaAttendance> call, Response<ResponseAgendaAttendance> response) {
                if (response.code() == 200) {
                    ArrayList<DataAgenda> agendas = response.body().getData();
                    for (DataAgenda agenda : agendas) {
                        agendaNama.add(agenda.getNamaSingkat());
                        agendaMap.put(agenda.getNamaSingkat(), agenda);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, agendaNama);
                    agendaSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseAgendaAttendance> call, Throwable t) {
                Log.d("agenda", "Fail");
            }
        });
    }

    private void updateLocation(Location mylocation) {
        if (mylocation == null || ruangLoc == null) return;
        float distanceInMeters = 0;

        distanceInMeters = ruangLoc.distanceTo(mylocation) / 10; // dibagi 10 krn error kesalahan GPS 10 sd 13 meter
        if (distanceInMeters < 10) {
            goToPredict(mylocation);
        } else {
            Toast.makeText(getContext(), "Tidak bisa melakukan absensi, jarak anda: " + distanceInMeters + "m dari ruangan.", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToPredict(Location mylocation) {
        Intent intent = new Intent(getContext(), NewPredictActivity.class);
        intent.putExtra("cmd", "absen");
        intent.putExtra("latitude", mylocation.getLatitude());
        intent.putExtra("longitude", mylocation.getLongitude());
        intent.putExtra("idAgenda", idAgenda);
        startActivity(intent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        addCallbackGPS();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
    }

    private synchronized void setUpGPSClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private boolean checkPermissionsLocation() {
        int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionLocation = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(getActivity(),"permission camera & location granted",Toast.LENGTH_SHORT).show();
            setUpGPSClient();
        }
    }

    @SuppressLint("MissingPermission")
    private void addCallbackGPS(){
        mylocation =  LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        LocationServices.FusedLocationApi
                .requestLocationUpdates(googleApiClient, locationRequest, this);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied.
                        // You can initialize location requests here.

                        mylocation = LocationServices.FusedLocationApi
                                .getLastLocation(googleApiClient);
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied.
                        // But could be fixed by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            // Ask to turn on GPS automatically
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS_GPS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied.
                        // However, we have no way
                        // to fix the
                        // settings so we won't show the dialog.
                        // finish();
                        break;
                }
            }
        });
    }
}
