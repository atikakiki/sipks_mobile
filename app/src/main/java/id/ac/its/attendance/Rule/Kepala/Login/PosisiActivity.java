package id.ac.its.attendance.Rule.Kepala.Login;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.Sekolah.ResponseSekolah;
import id.ac.its.attendance.Response.Sekolah.Sekolah;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ApiClientSIPKS;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ServerSIPKS;
import id.ac.its.attendance.Utility.Constans;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PosisiActivity extends AppCompatActivity {
    private List<Sekolah> sekolah;
    private AdapterLogin adapterLogin;
    private RecyclerView rc;
    private TextView belum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posisi);
        rc = findViewById(R.id.rc_posisi);
        belum = findViewById(R.id.verifikasi);
        cari();

    }

    public void cari()
    {
        final SweetAlertDialog pDialog = new SweetAlertDialog(PosisiActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.show();
        ApiClientSIPKS api = ServerSIPKS.builder(PosisiActivity.this).create(ApiClientSIPKS.class);
        Call<ResponseSekolah> cari = api.sekolah(Constans.getNip(),"Bearer "+Constans.getToken());
        cari.enqueue(new Callback<ResponseSekolah>() {
            @Override
            public void onResponse(Call<ResponseSekolah> call, Response<ResponseSekolah> response) {
                if (response.body().getPesan().equals("success"))
                {
                    sekolah = response.body().getDataSekolah().getDaftar_sekolah();
                    if(!sekolah.isEmpty())
                    {
                        adapterLogin = new AdapterLogin(sekolah,getApplicationContext());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        rc.setLayoutManager(mLayoutManager);
                        rc.setItemAnimator(new DefaultItemAnimator());
                        rc.setAdapter(adapterLogin);
                        rc.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                pDialog.dismiss();
                                belum.setVisibility(View.GONE);

                            }
                        });
                    }
                    else
                    {
                        pDialog.dismiss();
                    }

                }
                else
                {
                    new SweetAlertDialog(PosisiActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Session Anda Habis")
                            .setContentText("Coba login kembali")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Constans.quit(PosisiActivity.this);
                                    Constans.deleteCache(PosisiActivity.this);
                                }
                            }).show();
                }

            }
            @Override
            public void onFailure(Call<ResponseSekolah> call, Throwable t) {
                new SweetAlertDialog(PosisiActivity.this, SweetAlertDialog.WARNING_TYPE)
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
