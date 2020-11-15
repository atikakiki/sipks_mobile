package id.ac.its.attendance.TandaTangan;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import id.ac.its.attendance.Activity.AturImeiActivity;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.ResponseAll;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ApiClientSIPKS;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ServerSIPKS;
import id.ac.its.attendance.Utility.Constans;
import com.williamww.silkysignature.views.SignaturePad;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.ac.its.attendance.Utility.Constans.encodeToBase64;

/**
 * A simple {@link Fragment} subclass.
 */
public class TTDFragment extends DialogFragment {
    private Button mClearButton;
    private Button mSaveButton;
    private SignaturePad mSignaturePad;
    public static TTDFragment newInstance(String title) {
        TTDFragment frag = new TTDFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }
    public TTDFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ttd, container, false);
        mClearButton = (Button) view.findViewById(R.id.clear_button);
        mSaveButton = (Button)  view.findViewById(R.id.save_button);
        mSignaturePad = (SignaturePad)  view.findViewById(R.id.signature_pad);
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
                ApiClientSIPKS api = ServerSIPKS.builder(getContext()).create(ApiClientSIPKS.class);
                Call<ResponseAll> fill = api.kirimttd(Constans.getNip(),"data:image/jpeg;base64,"+myBase64Image,"Bearer "+Constans.getToken());
                final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.show();
                fill.enqueue(new Callback<ResponseAll>() {
                    @Override
                    public void onResponse(Call<ResponseAll> call, Response<ResponseAll> response) {
                        if(response.code()==200)
                        {
                            pDialog.dismiss();
                            ((AturImeiActivity)getActivity()).close();
                        }
                        else
                        {
                            pDialog.dismiss();
                            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
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
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
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
        return view;
    }

}
