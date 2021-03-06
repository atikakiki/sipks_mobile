package id.ac.its.attendance.Rule.Kepala.Bos;


import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.Response1Transfer.Data1Transfer;
import id.ac.its.attendance.Response.Response1Transfer.Response1Transfer;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ApiClientSIPKS;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ServerSIPKS;
import id.ac.its.attendance.Rule.Kepala.Bopda.Fragment.Gaji.KAdapaterGajiTransfer;
import id.ac.its.attendance.Rule.Kepala.Bopda.Fragment.Gaji.KAdapaterGajiTunai;
import id.ac.its.attendance.Utility.Constans;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class KLainFragment extends Fragment {

    private List<Data1Transfer> barangs,barang1;
    private RecyclerView rc,rc2;
    private TextView kosong,kosong2;
    private KAdapaterGajiTunai adapterBarang;
    private KAdapaterGajiTransfer adapaterGajiTransfer;
    SwipeRefreshLayout swipeRefreshLayout;
    public KLainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        cari();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_klain, container, false);
        rc = view.findViewById(R.id.rc_lain_tunai);
        kosong = view.findViewById(R.id.kosong_lain_tunai);
        rc2 = view.findViewById(R.id.rc_lain_transfer);
        kosong2 = view.findViewById(R.id.kosong_lain_transfer);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cari();
            }
        });
        cari();
        return view;
    }

    public void cari()
    {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.show();
        final SweetAlertDialog pDialog1 = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog1.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog1.setTitleText("Loading");
        pDialog1.show();
        ApiClientSIPKS api = ServerSIPKS.builder(getActivity()).create(ApiClientSIPKS.class);
        Call<Response1Transfer> cari = api.tigatunai1(Constans.getId_sekolah(),"Bearer "+Constans.getToken());
        cari.enqueue(new Callback<Response1Transfer>() {
            @Override
            public void onResponse(Call<Response1Transfer> call, Response<Response1Transfer> response) {
                if(response.body().getPesan().equals("success"))
                {
                    barangs = response.body().getData1Transfers();
//                Log.d(TAG, "onResponse: "+barangs);
                    if (barangs==null || barangs.isEmpty())
                    {
                        kosong.setVisibility(View.VISIBLE);
                        rc.setVisibility(View.INVISIBLE);
                        pDialog.dismiss();

                    }
                    else
                    {
                        rc.setVisibility(View.VISIBLE);
                        adapterBarang = new KAdapaterGajiTunai(barangs,getActivity());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        rc.setLayoutManager(mLayoutManager);
                        rc.setItemAnimator(new DefaultItemAnimator());
                        rc.setAdapter(adapterBarang);
                        rc.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                pDialog.dismiss();
                            }
                        });
                        kosong.setVisibility(View.GONE);
                    }
                }
                else
                {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Session Anda Habis")
                            .setContentText("Coba login kembali")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Constans.quit(getContext());
                                    Constans.deleteCache(getContext());
                                }
                            }).show();
                }

            }

            @Override
            public void onFailure(Call<Response1Transfer> call, Throwable t) {
                pDialog.dismiss();
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
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

        Call<Response1Transfer> dua = api.tigatransfer1(Constans.getId_sekolah(),"Bearer "+Constans.getToken());
        dua.enqueue(new Callback<Response1Transfer>() {
            @Override
            public void onResponse(Call<Response1Transfer> call, Response<Response1Transfer> response) {
                if (response.body().getPesan().equals("success"))
                {
                    barang1 = response.body().getData1Transfers();
//                Log.d(TAG, "onResponse: "+barangs);
                    if (barang1==null || barang1.isEmpty())
                    {
                        kosong2.setVisibility(View.VISIBLE);
                        rc2.setVisibility(View.INVISIBLE);
                        pDialog1.dismiss();
                    }
                    else
                    {
                        rc2.setVisibility(View.VISIBLE);
                        adapaterGajiTransfer = new KAdapaterGajiTransfer(barang1,getActivity());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        rc2.setLayoutManager(mLayoutManager);
                        rc2.setItemAnimator(new DefaultItemAnimator());
                        rc2.setAdapter(adapaterGajiTransfer);
                        rc2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                pDialog1.dismiss();
                            }
                        });
                        kosong2.setVisibility(View.GONE);
                    }
                }
                else {
                    new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Session Anda Habis")
                            .setContentText("Coba login kembali")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                    Constans.quit(getContext());
                                    Constans.deleteCache(getContext());
                                }
                            }).show();
                }

            }

            @Override
            public void onFailure(Call<Response1Transfer> call, Throwable t) {
                pDialog1.dismiss();
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
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
        if (swipeRefreshLayout.isRefreshing())
        {
            swipeRefreshLayout.setRefreshing(false);
        }
    }


}
