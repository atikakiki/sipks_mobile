package id.ac.its.attendance.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.Attendance.ResponseApi;
import id.ac.its.attendance.Retrofit.ServerAttendance.ApiClientAttendance;
import id.ac.its.attendance.Retrofit.ServerAttendance.ServerAttendance;
import id.ac.its.attendance.Utility.Constans;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UploadActivity extends AppCompatActivity {
    private CameraView camerad;
    private CameraKitEventListener cameradListener;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upload Foto");
        cameradListener = new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                byte[] picture = cameraKitImage.getJpeg();
                Bitmap result = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                result = Bitmap.createScaledBitmap(result, 96,96, true);

/*
                String myBase64Image = Constans.encodeToBase64(result, Bitmap.CompressFormat.JPEG, 100);
                ApiClientSIPKS api = ServerSIPKS.builder(UploadActivity.this).create(ApiClientSIPKS.class);
                Call<ResponseAll> fill = api.kirimgambar(Constans.getNip(),"data:image/jpeg;base64,"+myBase64Image,"Bearer "+Constans.getToken());
*/

                String myBase64Image = Constans.encodeToBase64(result, Bitmap.CompressFormat.JPEG, 100);
                ApiClientAttendance api = ServerAttendance.builder().create(ApiClientAttendance.class);
                Call<ResponseApi> upload = api.kirim(Constans.getNip(), Constans.getPassword(), "data:image/jpeg;base64,"+myBase64Image);

                final SweetAlertDialog pDialog = new SweetAlertDialog(UploadActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.show();

                upload.enqueue(new Callback<ResponseApi>() {
                    @Override
                    public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                        if (response.code() == 200 && !response.body().getMsg().equals("token_absent"))
                        {
                            pDialog.dismiss();
                            new SweetAlertDialog(UploadActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Hasil")
                                    .setContentText(response.body().getMsg())
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismiss();
                                        }
                                    }).show();
                        }
                        else
                        {
                            pDialog.dismiss();
                            new SweetAlertDialog(UploadActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("Terjadi kesalahan, mohon ulangi lagi.")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismiss();
                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseApi> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(UploadActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Hasil")
                                .setContentText("Internet Anda Bermasalah")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                }).show();
                    }
                });
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        };

        camerad = (CameraView) findViewById(R.id.camera);
        camerad.addCameraKitListener(cameradListener);

        btn = (Button) findViewById(R.id.btn_capture);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camerad.captureImage();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        camerad.start();
    }

    @Override
    protected void onPause() {
        camerad.stop();
        super.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
