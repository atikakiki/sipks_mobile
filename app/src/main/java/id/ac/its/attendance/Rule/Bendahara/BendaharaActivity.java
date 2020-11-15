package id.ac.its.attendance.Rule.Bendahara;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.Bendahara.BendaharaResponse;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ApiClientSIPKS;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ServerSIPKS;
import id.ac.its.attendance.Activity.MainActivity;
import id.ac.its.attendance.Utility.Constans;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BendaharaActivity extends AppCompatActivity {
    private LinearLayout unit,bos;
    boolean benunit = false;
    boolean benbos = false;
    private TextView belum;
    private LinearLayout sudah;
    SweetAlertDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bendahara);
        pDialog = new SweetAlertDialog(BendaharaActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.show();
        unit = findViewById(R.id.bendaharaunit);
        bos = findViewById(R.id.bendaharabos);
        belum = findViewById(R.id.verifikasi);
        sudah = findViewById(R.id.sudah);
        ApiClientSIPKS api = ServerSIPKS.builder(BendaharaActivity.this).create(ApiClientSIPKS.class);
        Call<BendaharaResponse> cari = api.bendahara(Constans.getNip(),"Bearer "+Constans.getToken());
        cari.enqueue(new Callback<BendaharaResponse>() {
            @Override
            public void onResponse(Call<BendaharaResponse> call, final Response<BendaharaResponse> response) {
                if (response.body().getPesan().equals("success"))
                {
                    unit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Constans.setPosisi("11");
                            Constans.setId_sekolah(response.body().getData().getBopda_id());
                            Constans.setNama_sekolah(response.body().getData().getBopda_sekolah());
                            Constans.setBagian("BOPDA");
                            Constans.setRekening(response.body().getData().getRekening_bopda());
                            startActivity(new Intent(BendaharaActivity.this, MainActivity.class));
                            finish();
                        }
                    });
                    bos.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Constans.setPosisi("12");
                            Constans.setId_sekolah(response.body().getData().getBos_id());
                            Constans.setNama_sekolah(response.body().getData().getBos_sekolah());
                            Constans.setBagian("BOS");
                            Constans.setRekening(response.body().getData().getRekening_bos());
                            startActivity(new Intent(BendaharaActivity.this,MainActivity.class));
                            finish();
                        }
                    });
                    if (response.body().getData().getBos_id()==null)
                    {
                        bos.setVisibility(View.GONE);
                        benbos = true;
                    }
                    if (response.body().getData().getBopda_id()==null)
                    {
                        unit.setVisibility(View.GONE);
                        benunit = true;
                    }
                    if (benbos||benunit)
                    {
                        sudah.setVisibility(View.VISIBLE);
                        belum.setVisibility(View.GONE);
                    }
                    pDialog.dismiss();
                }
                else
                {
                    pDialog.dismiss();
                    new SweetAlertDialog(BendaharaActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Session Anda Habis")
                            .setContentText("Coba login kembali")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Constans.quit(BendaharaActivity.this);
                                    Constans.deleteCache(BendaharaActivity.this);
                                }
                            }).show();
                }

            }

            @Override
            public void onFailure(Call<BendaharaResponse> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(BendaharaActivity.this, SweetAlertDialog.WARNING_TYPE)
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
