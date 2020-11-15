package id.ac.its.attendance.Rule.Bendahara.Bos.Activity;

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

import id.ac.its.attendance.Konfirmasi.KonfirmasiBendahara1Fragment;
import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.BendaharaBarang.ResponseBendaharaBarang;
import id.ac.its.attendance.Response.DetailTransfer.Body;
import id.ac.its.attendance.Response.DetailTransfer.ResponseDetail;
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

public class DetailTransaksiBarangActivity extends AppCompatActivity {
    TextView nama,uang,rekening;
    Intent i;
    String data,uang1,format;
    AdapterDetail adapterDetail;
    RecyclerView rc;
    private List<Body> barangs;
    LinearLayout linearLayout ;
    KonfirmasiBendahara1Fragment confirmFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi_barang);
        nama = findViewById(R.id.nama);
        uang = findViewById(R.id.uang);
        i = getIntent();
        data = i.getStringExtra("id");
        uang1 = i.getStringExtra("uang");
        linearLayout = findViewById(R.id.konfirmasi_bendahara);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DetailTransaksiBarangActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Apakah yakin Konfirmasi ?")
                        .setContentText("Menyutujui dari Bendahara")
                        .setCancelText("Tidak")
                        .setConfirmText("Iya")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                FragmentManager fm = getSupportFragmentManager();
                                confirmFragment = KonfirmasiBendahara1Fragment.newInstance("Some Title");
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
        rc = findViewById(R.id.rc_detail);
        rekening = findViewById(R.id.no_rekening);
        float angka = Float.valueOf(uang1);
        Locale INDONESIA = new Locale("in", "ID");
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = (DecimalFormat)
                NumberFormat.getNumberInstance(INDONESIA);
        decimalFormat.applyPattern(pattern);
        format = decimalFormat.format(angka);
        cari();
    }

    public void cari()
    {
        final SweetAlertDialog pDialog = new SweetAlertDialog(DetailTransaksiBarangActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.show();
        ApiClientSIPKS api = ServerSIPKS.builder(DetailTransaksiBarangActivity.this).create(ApiClientSIPKS.class);
        Call<ResponseDetail> cari = api.bendahara_detail(data,"Bearer "+ Constans.getToken());
        cari.enqueue(new Callback<ResponseDetail>() {
            @Override
            public void onResponse(Call<ResponseDetail> call, Response<ResponseDetail> response) {
                if (response.body().getPesan().equals("success"))
                {
                    nama.setText(response.body().getDataDetail().getTitle().getNama_kegiatan());
                    uang.setText(format);
                    if (response.body().getDataDetail().getRekening().getNo_rekenig()!=null)
                    {
                        rekening.setText(response.body().getDataDetail().getRekening().getNo_rekenig());
                    }
                    barangs = response.body().getDataDetail().getBody();
                    if (barangs!=null ||! barangs.isEmpty()){
                        adapterDetail = new AdapterDetail(barangs,DetailTransaksiBarangActivity.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DetailTransaksiBarangActivity.this);
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
                    new SweetAlertDialog(DetailTransaksiBarangActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Session Anda Habis")
                            .setContentText("Coba login kembali")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Constans.quit(DetailTransaksiBarangActivity.this);
                                    Constans.deleteCache(DetailTransaksiBarangActivity.this);
                                }
                            }).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseDetail> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(DetailTransaksiBarangActivity.this, SweetAlertDialog.WARNING_TYPE)
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

    public void kirim()
    {
        final SweetAlertDialog pDialog = new SweetAlertDialog(DetailTransaksiBarangActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.show();
        ApiClientSIPKS api = ServerSIPKS.builder(DetailTransaksiBarangActivity.this).create(ApiClientSIPKS.class);
        Call<ResponseBendaharaBarang> barang = api.bendahara_barang(data,uang1,"Bearer "+Constans.getToken());
        barang.enqueue(new Callback<ResponseBendaharaBarang>() {
            @Override
            public void onResponse(Call<ResponseBendaharaBarang> call, Response<ResponseBendaharaBarang> response) {
                pDialog.dismiss();
                if(response.body().getPesan().equals("success"))
                {
                    if (response.body().getUpdate().getHasil()!=null)
                    {
                        new SweetAlertDialog(DetailTransaksiBarangActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Status")
                                .setContentText("Berhasil di Approve")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismiss();
                                        finish();
                                    }
                                }).show();
                    }
                    else
                    {
                        new SweetAlertDialog(DetailTransaksiBarangActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Status")
                                .setContentText("Gagal di Approve")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismiss();
                                    }
                                }).show();
                    }
                }
                else
                {
                    new SweetAlertDialog(DetailTransaksiBarangActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Session Anda Habis")
                            .setContentText("Coba login kembali")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Constans.quit(DetailTransaksiBarangActivity.this);
                                    Constans.deleteCache(DetailTransaksiBarangActivity.this);
                                }
                            }).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBendaharaBarang> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(DetailTransaksiBarangActivity.this, SweetAlertDialog.WARNING_TYPE)
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
        kirim();
    }
}
