package id.ac.its.attendance.Rule.Kepala;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import id.ac.its.attendance.Activity.MainActivity;
import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.KepalaBagian.ResponseKepalabagian;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ApiClientSIPKS;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ServerSIPKS;
import id.ac.its.attendance.Utility.Constans;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KepalaBagianActivity extends AppCompatActivity {
    private LinearLayout unit,bos;
    SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kepala_bagian);
        unit = findViewById(R.id.bendaharaunit);
        bos = findViewById(R.id.bendaharabos);
        pDialog = new SweetAlertDialog(KepalaBagianActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.show();
        ApiClientSIPKS api = ServerSIPKS.builder(KepalaBagianActivity.this).create(ApiClientSIPKS.class);
        Call<ResponseKepalabagian> cari = api.kepala_bagian(Constans.getId_sekolah(),"Bearer "+Constans.getToken());
        cari.enqueue(new Callback<ResponseKepalabagian>() {
            @Override
            public void onResponse(Call<ResponseKepalabagian> call, final Response<ResponseKepalabagian> response) {
                if(response.body().getPesan().equals("success"))
                {
                    unit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Constans.setPosisi("14");
                            Constans.setBagian("BOPDA");
                            Constans.setRekening(response.body().getDataKepalabagian().getRek_bopda());
                            startActivity(new Intent(KepalaBagianActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                    bos.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Constans.setPosisi("15");
                            Constans.setBagian("BOS");
                            Constans.setRekening(response.body().getDataKepalabagian().getRek_bos());
                            startActivity(new Intent(KepalaBagianActivity.this,MainActivity.class));
                            finish();
                        }
                    });
                    pDialog.dismiss();
                }
                else
                {
                    pDialog.dismiss();
                    new SweetAlertDialog(KepalaBagianActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Session Anda Habis")
                            .setContentText("Coba login kembali")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Constans.quit(KepalaBagianActivity.this);
                                    Constans.deleteCache(KepalaBagianActivity.this);
                                }
                            }).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseKepalabagian> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(KepalaBagianActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Internet")
                        .setContentText("Internet Anda bermasalah")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                            }
                        }).show();
            }
        });
    }
}
