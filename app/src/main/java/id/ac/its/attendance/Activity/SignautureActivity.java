package id.ac.its.attendance.Activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.ResponseAll;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ApiClientSIPKS;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ServerSIPKS;
import id.ac.its.attendance.Utility.Constans;
import com.williamww.silkysignature.views.SignaturePad;

import java.io.ByteArrayOutputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignautureActivity extends AppCompatActivity {
    private SignaturePad mSignaturePad;
    private Button mClearButton;
    private Button mSaveButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signauture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tanda Tangan");
        mClearButton = (Button) findViewById(R.id.clear_button);
        mSaveButton = (Button) findViewById(R.id.save_button);
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        imageView = findViewById(R.id.hai);
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
            public void onClick(View view) {
                Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
                String myBase64Image = encodeToBase64(signatureBitmap, Bitmap.CompressFormat.JPEG, 100);
                ApiClientSIPKS api = ServerSIPKS.builder(SignautureActivity.this).create(ApiClientSIPKS.class);
                Call<ResponseAll> fill = api.ttd("dwi.syn@gmail.com","data:image/jpeg;base64,"+myBase64Image, Constans.getNip(),"mis12345");
                final SweetAlertDialog pDialog = new SweetAlertDialog(SignautureActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.show();
                fill.enqueue(new Callback<ResponseAll>() {
                    @Override
                    public void onResponse(Call<ResponseAll> call, Response<ResponseAll> response) {
                        if(response.code()==200)
                        {
                            pDialog.dismiss();
                            new SweetAlertDialog(SignautureActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Hasil")
                                    .setContentText(response.body().getPesan())
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                        }
                                    }).show();
                        }
                        else
                        {
                            pDialog.dismiss();
                            new SweetAlertDialog(SignautureActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Hasil")
                                    .setContentText("Hubungi Coba Lagi")
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
                    public void onFailure(Call<ResponseAll> call, Throwable t) {
                        pDialog.dismiss();
                        new SweetAlertDialog(SignautureActivity.this, SweetAlertDialog.ERROR_TYPE)
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


    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
