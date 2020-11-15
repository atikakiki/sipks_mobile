package id.ac.its.attendance.Activity;

import android.graphics.Color;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.TextView;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.BendaharaBarang.ResponseBendaharaBarang;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ApiClientSIPKS;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ServerSIPKS;
import id.ac.its.attendance.TandaTangan.TTDFragment;
import id.ac.its.attendance.Utility.Constans;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AturImeiActivity extends AppCompatActivity {
    private CardView cardView,cardView1;
    private TextView textView;
    private String imeidevice;
    TTDFragment confirmFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atur_imei);
        textView = findViewById(R.id.Imei);
        cardView1 = findViewById(R.id.btn_register);
        cardView = findViewById(R.id.card_imei);
        cardView.setBackgroundResource(R.drawable.boder_cardview_blue);
        imeidevice = Constans.getUniqueIMEIId(AturImeiActivity.this);
        textView.setText(imeidevice);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imeidevice==null||!imeidevice.equals(""))
                {
                    final SweetAlertDialog pDialog = new SweetAlertDialog(AturImeiActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.show();
                    ApiClientSIPKS api = ServerSIPKS.builder(AturImeiActivity.this).create(ApiClientSIPKS.class);
                    Call<ResponseBendaharaBarang> login = api.aturiemi(Constans.getNip(),imeidevice,"Bearer "+Constans.getToken());
                    login.enqueue(new Callback<ResponseBendaharaBarang>() {
                        @Override
                        public void onResponse(Call<ResponseBendaharaBarang> call, Response<ResponseBendaharaBarang> response) {
                            pDialog.dismiss();
                            if (response.body().getPesan().equals("success"))
                            {
                                if (response.body().getUpdate().getHasil().equals("Berhasil"))
                                {
                                    new SweetAlertDialog(AturImeiActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Berhasil Atur Imei")
                                            .setContentText("Selanutnya Tanda Tangan")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.dismiss();
//                                                    Constans.quit(AturImeiActivity.this);
//                                                    Constans.deleteCache(AturImeiActivity.this);
                                                    FragmentManager fm = getSupportFragmentManager();
                                                    confirmFragment = TTDFragment.newInstance("Some Title");
                                                    confirmFragment.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppDialogTheme);
                                                    confirmFragment.show(fm, "fragment_edit_name");
                                                }
                                            }).show();
                                }
                                else
                                {
                                    new SweetAlertDialog(AturImeiActivity.this, SweetAlertDialog.ERROR_TYPE)
                                            .setTitleText("Hubungi Dinas Pendidikan")
                                            .setContentText("Gagal Mengatur Imei")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.dismiss();
                                                    Constans.quit(AturImeiActivity.this);
                                                    Constans.deleteCache(AturImeiActivity.this);


                                                }
                                            }).show();
                                }
                            }
                            else
                            {
                                new SweetAlertDialog(AturImeiActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Session Anda Habis")
                                        .setContentText("Coba login kembali")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismiss();
                                                finish();
                                            }
                                        }).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBendaharaBarang> call, Throwable t) {
                            pDialog.dismiss();
                            new SweetAlertDialog(AturImeiActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                else
                {
                    new SweetAlertDialog(AturImeiActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Hubungi Dinas Pendidikan")
                            .setContentText("Imei anda tidak terbaca")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    finish();
                                }
                            }).show();
                }
            }
        });
    }

    public void close()
    {
        confirmFragment.dismiss();
        new SweetAlertDialog(AturImeiActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Berhasil Upload Tanda Tangan")
                .setContentText("Silahkan Login Ulang")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        Constans.quit(AturImeiActivity.this);
                        Constans.deleteCache(AturImeiActivity.this);
                    }
                }).show();
    }
}
