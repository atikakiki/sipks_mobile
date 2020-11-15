package id.ac.its.attendance.Activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.Attendance.ResponseApi;
import id.ac.its.attendance.Retrofit.ServerAttendance.ApiClientAttendance;
import id.ac.its.attendance.Retrofit.ServerAttendance.ServerAttendance;
import id.ac.its.attendance.Utility.Constans;
import com.williamww.silkysignature.views.SignaturePad;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadSignatureActivity extends AppCompatActivity {

    private SignaturePad mSignaturePad;
    private Button mClearButton, mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_signature);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Upload Signature");

        mClearButton = (Button) findViewById(R.id.clear_button);
        mSaveButton = (Button) findViewById(R.id.save_button);
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);

        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
            }

            @Override
            public void onSigned() {
                mSaveButton.setEnabled(true);
                mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                mSaveButton.setEnabled(false);
                mClearButton.setEnabled(false);
            }
        });

        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                String myBase64Image = Constans.encodeToBase64(signatureBitmap, Bitmap.CompressFormat.JPEG, 100);

                // ApiClientSIPKS api = ServerSIPKS.builder(SignautureActivity.this).create(ApiClientSIPKS.class);
                // Call<ResponseAll> fill = api.ttd("dwi.syn@gmail.com","data:image/jpeg;base64,"+myBase64Image, Constans.getNip(),"mis12345");

                ApiClientAttendance api = ServerAttendance.builder().create(ApiClientAttendance.class);
                Call<ResponseApi> upload = api.sendSignature(Constans.getNip(), Constans.getPassword(), "data:image/jpeg;base64,"+myBase64Image);

                final SweetAlertDialog pDialog = new SweetAlertDialog(UploadSignatureActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setCancelable(false);
                pDialog.show();

                upload.enqueue(new Callback<ResponseApi>() {
                    @Override
                    public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                        if(response.code() == 200) {
                            pDialog.dismiss();
                            new SweetAlertDialog(UploadSignatureActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Hasil")
                                    .setContentText(response.body().getMsg())
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    }).show();

                            mSignaturePad.clear();
                        } else {
                            pDialog.dismiss();
                            new SweetAlertDialog(UploadSignatureActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("Terjadi kesalahan, mohon ulangi lagi.")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseApi> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(UploadSignatureActivity.this, SweetAlertDialog.ERROR_TYPE)
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
        });
    }
}
