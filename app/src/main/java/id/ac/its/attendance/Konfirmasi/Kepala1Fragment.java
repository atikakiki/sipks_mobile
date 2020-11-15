package id.ac.its.attendance.Konfirmasi;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.ResponseAll;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ApiClientSIPKS;
import id.ac.its.attendance.Retrofit.ServerSIPKS.ServerSIPKS;
import id.ac.its.attendance.Rule.Kepala.Bopda.Activity.TransferBarangActivity;
import id.ac.its.attendance.Utility.Constans;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Kepala1Fragment extends DialogFragment {

    private Button button;
    private CameraView camerad;
    private CameraKitEventListener cameradListener;
    public Kepala1Fragment() {
        // Required empty public constructor
    }

    public static Kepala1Fragment newInstance(String title) {
        Kepala1Fragment frag = new Kepala1Fragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        button = view.findViewById(R.id.btn_capture);
        cameradListener = new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                byte[] picture = cameraKitImage.getJpeg();
                Bitmap result = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                result = Bitmap.createScaledBitmap(result, 96,96, true);
                String myBase64Image = Constans.encodeToBase64(result, Bitmap.CompressFormat.JPEG, 100);
                ApiClientSIPKS api = ServerSIPKS.builder(getActivity()).create(ApiClientSIPKS.class);
                Call<ResponseAll> fill = api.kirimgambar(Constans.getNip(),"data:image/jpeg;base64,"+myBase64Image,"Bearer "+Constans.getToken());
                Log.d("test", "onImage: "+myBase64Image);
                final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loading");
                pDialog.show();
                fill.enqueue(new Callback<ResponseAll>() {
                    @Override
                    public void onResponse(Call<ResponseAll> call, Response<ResponseAll> response) {
                        if (!response.body().getPesan().equals("token_absent"))
                        {
                            pDialog.dismiss();
                            String[] hasil = response.body().getPesan().split(",");
                            if (hasil[0].equals("ACCEPTED"))
                            {
                                ((TransferBarangActivity)getActivity()).close();
                            }
                            else
                            {
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Wajah Tidak Terdeteksi")
                                        .setContentText("Coba Foto Ulang")
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
                            pDialog.dismiss();
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Session Anda Habis")
                                    .setContentText("Coba Login Ulang")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismiss();
                                            Constans.quit(getActivity());
                                            Constans.deleteCache(getActivity());
                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAll> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        };
        camerad = (CameraView) view.findViewById(R.id.camera);
        camerad.addCameraKitListener(cameradListener);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camerad.captureImage();
//                ((PredictyActivity)getActivity()).close();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        camerad.start();
    }

    @Override
    public void onPause() {
        camerad.stop();
        super.onPause();
    }

}
