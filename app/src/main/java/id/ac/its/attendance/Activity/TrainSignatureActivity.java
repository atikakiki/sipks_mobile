package id.ac.its.attendance.Activity;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.Attendance.ResponseApi;
import id.ac.its.attendance.Retrofit.ServerAttendance.ApiClientAttendance;
import id.ac.its.attendance.Retrofit.ServerAttendance.ServerAttendance;
import id.ac.its.attendance.Utility.Constans;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainSignatureActivity extends AppCompatActivity {

    private Button trainBtn;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_signature);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Training Signature");

        cardView = findViewById(R.id.card_imei);
        cardView.setBackgroundResource(R.drawable.boder_cardview_blue);

        trainBtn = findViewById(R.id.train);
        trainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiClientAttendance api = ServerAttendance.builder().create(ApiClientAttendance.class);
                Call<ResponseApi> train = api.trainTTD(Constans.getNip(), Constans.getPassword());

                final SweetAlertDialog pDialog = new SweetAlertDialog(TrainSignatureActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.setContentText("Training Signature: " + Constans.getNip());
                pDialog.setCancelable(false);
                pDialog.show();

                train.enqueue(new Callback<ResponseApi>() {
                    @Override
                    public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                        if (response.code()==200)
                        {
                            pDialog.dismiss();
                            new SweetAlertDialog(TrainSignatureActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Hasil")
                                    .setContentText(response.body().getMsg())
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
                            new SweetAlertDialog(TrainSignatureActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("Terjadi kesalahan, mohon ulangi kembali.")
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
                        new SweetAlertDialog(TrainSignatureActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Error")
                                .setContentText("Terdapat masalah dengan koneksi anda, mohon ulangi kembali.")
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
