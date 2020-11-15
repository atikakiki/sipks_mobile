package id.ac.its.attendance.Rule.Kepala.Bopda.Activity;

import android.content.Intent;
import android.graphics.Color;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import id.ac.its.attendance.Konfirmasi.Kepala2Fragment;
import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.BendaharaBarang.ResponseBendaharaBarang;
import id.ac.its.attendance.Response.BendaharaGaji.BendaharaGaji;
import id.ac.its.attendance.Response.BendaharaGaji.BodyGaji;
import id.ac.its.attendance.Retrofit.ServerBankJatim.ApiClientBankJatim;
import id.ac.its.attendance.Retrofit.ServerBankJatim.ServerBank;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ApiClientSIPKS;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ServerSIPKS;
import id.ac.its.attendance.Utility.Constans;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferGajiActivity extends AppCompatActivity {
    TextView nama,uang;
    Intent i;
    String data,uang1,format;
    KAdapterActivityGajiTransfer adapterDetail;
    RecyclerView rc;
    List<BodyGaji> barangs;
    LinearLayout linearLayout ;
    Kepala2Fragment confirmFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_gaji);
        nama = findViewById(R.id.nama);
        uang = findViewById(R.id.uang);
        i = getIntent();
        data = i.getStringExtra("id");
        uang1 = i.getStringExtra("uang");
        rc = findViewById(R.id.rc_detail);
        float angka = Float.valueOf(uang1);
        Locale INDONESIA = new Locale("in", "ID");
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = (DecimalFormat)
                NumberFormat.getNumberInstance(INDONESIA);
        decimalFormat.applyPattern(pattern);
        format = decimalFormat.format(angka);
        linearLayout = findViewById(R.id.konfirmasi_bendahara);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(TransferGajiActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Apakah yakin Konfirmasi ?")
                        .setContentText("Menyutujui Transfer ?")
                        .setCancelText("Tidak")
                        .setConfirmText("Iya")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                FragmentManager fm = getSupportFragmentManager();
                                confirmFragment = Kepala2Fragment.newInstance("Some Title");
                                confirmFragment.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppDialogTheme);
                                confirmFragment.show(fm, "fragment_edit_name");
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();
            }
        });
        cari();
    }

    public void cari()
    {
        final SweetAlertDialog pDialog = new SweetAlertDialog(TransferGajiActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.show();
        ApiClientSIPKS api = ServerSIPKS.builder(TransferGajiActivity.this).create(ApiClientSIPKS.class);
        Call<BendaharaGaji> cari = api.kepala_detail_gaji(data,"Bearer "+ Constans.getToken());
        cari.enqueue(new Callback<BendaharaGaji>() {
            @Override
            public void onResponse(Call<BendaharaGaji> call, Response<BendaharaGaji> response) {
                if(response.body().getPesan().equals("success"))
                {
                    nama.setText(response.body().getDataGaji().getTitleGaji().getJudul());
                    uang.setText(format);
                    barangs = response.body().getDataGaji().getBodyGajis();
                    if (barangs!=null ||! barangs.isEmpty())
                    {
                        adapterDetail = new KAdapterActivityGajiTransfer(barangs,TransferGajiActivity.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(TransferGajiActivity.this);
                        rc.setLayoutManager(mLayoutManager);
                        rc.setItemAnimator(new DefaultItemAnimator());
                        rc.setAdapter(adapterDetail);
                        rc.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                pDialog.dismiss();
                            }
                        });
                    }
                }
                else
                {
                    pDialog.dismiss();
                    new SweetAlertDialog(TransferGajiActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Session Anda Habis")
                            .setContentText("Coba Login Ulang")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Constans.quit(TransferGajiActivity.this);
                                    Constans.deleteCache(TransferGajiActivity.this);
                                }
                            }).show();
                }

            }

            @Override
            public void onFailure(Call<BendaharaGaji> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(TransferGajiActivity.this, SweetAlertDialog.WARNING_TYPE)
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

    public  void kirimke(String tujuan, String jumlah, final String id_pekerjaan, final String kode_pekerjaan)
    {
        final SweetAlertDialog pDialog = new SweetAlertDialog(TransferGajiActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.show();
        ApiClientBankJatim api = ServerBank.builder().create(ApiClientBankJatim.class);
        Call<ResponseBendaharaBarang> barang = api.kirim(tujuan,Constans.getRekening(),jumlah);
        barang.enqueue(new Callback<ResponseBendaharaBarang>() {
            @Override
            public void onResponse(Call<ResponseBendaharaBarang> call, Response<ResponseBendaharaBarang> response) {
                if(response.body().getUpdate().getHasil()!=null)
                {
                    pDialog.dismiss();
                    kirim(id_pekerjaan,kode_pekerjaan);
                }
                else
                {
                    new SweetAlertDialog(TransferGajiActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Status")
                            .setContentText("Gagal di Approve")
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
            public void onFailure(Call<ResponseBendaharaBarang> call, Throwable t) {

            }
        });
    }

    public void kirim(String id_pekerjaan,String kode_pekerjaan)
    {
        final SweetAlertDialog pDialog = new SweetAlertDialog(TransferGajiActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.show();
        ApiClientSIPKS api = ServerSIPKS.builder(TransferGajiActivity.this).create(ApiClientSIPKS.class);
        Call<ResponseBendaharaBarang> barang = api.kepala_update_gaji(kode_pekerjaan,id_pekerjaan,"Bearer "+Constans.getToken());
        barang.enqueue(new Callback<ResponseBendaharaBarang>() {
            @Override
            public void onResponse(Call<ResponseBendaharaBarang> call, Response<ResponseBendaharaBarang> response) {
                if (response.body().getPesan().equals("success"))
                {
                    pDialog.dismiss();
                    if (response.body().getUpdate().getHasil()!=null)
                    {
                        new SweetAlertDialog(TransferGajiActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Status")
                                .setContentText("Berhasil di Approve")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        finish();
                                    }
                                }).show();
                    }
                    else
                    {
                        new SweetAlertDialog(TransferGajiActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Status")
                                .setContentText("Gagal di Approve")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                }).show();
                    }
                }
                else
                {
                    pDialog.dismiss();
                    new SweetAlertDialog(TransferGajiActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Session Anda Habis")
                            .setContentText("Coba Login Ulang")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Constans.quit(TransferGajiActivity.this);
                                    Constans.deleteCache(TransferGajiActivity.this);
                                }
                            }).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBendaharaBarang> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(TransferGajiActivity.this, SweetAlertDialog.WARNING_TYPE)
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
    public void close()
    {
        confirmFragment.dismiss();
        for(int i =0;i<adapterDetail.checked.size();i++)
        {
            kirim(adapterDetail.checked.get(i).getId_pekerjaan(),adapterDetail.checked.get(i).getKode_pekerjaan());
        }
    }
}
