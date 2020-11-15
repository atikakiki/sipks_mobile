package id.ac.its.attendance.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.Attendance.ResponseLoginAttendance;
import id.ac.its.attendance.Retrofit.ServerAttendance.ApiClientAttendance;
import id.ac.its.attendance.Retrofit.ServerAttendance.ServerAttendance;
import id.ac.its.attendance.Utility.Constans;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView title;
    private CardView cardView,cardView1,cardView2;
    private EditText email,password;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        title = (TextView)findViewById(R.id.tv_title);
        cardView1 = (CardView)findViewById(R.id.btn_login);
        cardView1.setBackgroundResource(R.drawable.boder_cardview_blue);
        cardView = findViewById(R.id.card_password);
        cardView.setBackgroundResource(R.drawable.boder_cardview_blue);
        cardView2 = findViewById(R.id.btn_register);
        cardView2.setOnClickListener(action);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/LuckiestGuy.ttf");
        title.setTypeface(custom_font);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
    }

    protected void login() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.show();
        Constans.setNip("05111740000015");
        Constans.setNama("kiki");
        Constans.setPassword("qwe123");
        Constans.setToken("12345678");

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        ApiClientAttendance api = ServerAttendance.builder().create(ApiClientAttendance.class);
        Call<ResponseLoginAttendance> login = api.login(email.getText().toString().trim(), password.getText().toString().trim());
        login.enqueue(new Callback<ResponseLoginAttendance>() {
            @Override
            public void onResponse(Call<ResponseLoginAttendance> call, Response<ResponseLoginAttendance> response) {
                pDialog.dismiss();
                if (response.code() == 200 && response.body().getMessage().equals("success")) {

                } else {
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Login Gagal")
                            .setContentText("Password Atau Username Anda Salah")
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
            public void onFailure(Call<ResponseLoginAttendance> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
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

    View.OnClickListener action = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btn_register:
                    login();
                    /*
                    final SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.show();
                    ApiClientSIPKS api = ServerSIPKS.builder(LoginActivity.this).create(ApiClientSIPKS.class);
                    Call<ResponseLogin> login = api.login(email.getText().toString().trim(),password.getText().toString().trim());
                    login.enqueue(new Callback<ResponseLogin>() {
                        @Override
                        public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                            pDialog.dismiss();
                            if(response.code()==200)
                            {
                                Constans.setNip(response.body().getData().getNip());
                                Constans.setNama(response.body().getData().getNama());
                                Constans.setToken(response.body().getData().getToken());
                               if (response.body().getData().getImei()==null||response.body().getData().getImei().isEmpty())
                               {
                                   intent = new Intent(LoginActivity.this,AturImeiActivity.class);
                                   startActivity(intent);
                                   finish();
                               }
                               else
                               {
                                   Constans.setImei(response.body().getData().getImei());
                                   String hand = Constans.getUniqueIMEIId(LoginActivity.this);
                                   if (hand!=null||!hand.equals(""))
                                   {
                                       if (hand.equals(Constans.getImei()))
                                       {
                                           if (response.body().getData().getPosisi().equals("10"))
                                           {
                                               intent = new Intent(LoginActivity.this,PosisiActivity.class);
                                               startActivity(intent);
                                               finish();
                                           }
                                           else if (response.body().getData().getPosisi().equals("11")||response.body().getData().getPosisi().equals("12"))
                                           {
                                               intent = new Intent(LoginActivity.this,BendaharaActivity.class);
                                               startActivity(intent);
                                               finish();
                                           }
                                           else
                                           {
                                               new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                                       .setTitleText("Status Akses")
                                                       .setContentText("Kamu Tidak Memiliki Akses")
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
                                           new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                                   .setTitleText("Status Akses")
                                                   .setContentText("Kamu Tidak Memiliki Akses Karena Imei Anda Berbeda")
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
                                   else
                                   {
                                       new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                            }
                            else
                            {

                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Login Gagal")
                                        .setContentText("Password Atau Username Anda Salah")
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
                        public void onFailure(Call<ResponseLogin> call, Throwable t) {
                            pDialog.dismiss();
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
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
                    */
                    break;
            }
        }
    };


}
