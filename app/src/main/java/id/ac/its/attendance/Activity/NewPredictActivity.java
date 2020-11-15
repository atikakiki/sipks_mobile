package id.ac.its.attendance.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.frame.FrameProcessor;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Response.Attendance.ResponseApi;
import id.ac.its.attendance.Retrofit.ServerAttendance.ApiClientAttendance;
import id.ac.its.attendance.Retrofit.ServerAttendance.ServerAttendance;
import id.ac.its.attendance.Utility.Constans;
import id.ac.its.attendance.Utility.PermissionsDelegate;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewPredictActivity extends AppCompatActivity implements FrameProcessor {
    private static final String LOGGING_TAG = "New Upload Activity";
    private static final String TAG = "PredictActivity";

    private final PermissionsDelegate permissionsDelegate = new PermissionsDelegate(this);
    private boolean hasCameraPermission;
    private CameraView cameraView;
    private Button capture;
    private TextView txtDetected;

    private String cmd, latitude, longitude, idAgenda;

    private int STATE_NOW = 0;
    private int STATE_BLINK_PERTAMA = 1;
    private int STATE_BLINK_KEDUA = 2;

    private float LEFT_EYE_CLOSE_PROB = 0.15F;
    private float RIGHT_EYE_CLOSE_PROB = 0.15F;
    private float SMILING_PROB = 0.80F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predicty);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cameraView = findViewById(R.id.camera_view);
        capture = findViewById(R.id.btn_capture);
        hasCameraPermission = permissionsDelegate.hasCameraPermission();

        txtDetected = findViewById(R.id.txtDetected);

        cameraView.setFacing(Facing.FRONT);
        cameraView.setLifecycleOwner(this);
        cameraView.addFrameProcessor(this);
        cameraView.addCameraListener(new CamListener());

        if (hasCameraPermission) {
            cameraView.setVisibility(View.VISIBLE);
        } else {
            permissionsDelegate.requestCameraPermission();
        }
    }

    @Override
    public void process(@NonNull Frame frame) {
        int height = frame.getSize().getHeight();
        int width = frame.getSize().getWidth();

        FirebaseVisionImageMetadata metadata = new FirebaseVisionImageMetadata.Builder()
                .setHeight(height)
                .setWidth(width)
                .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_YV12)
                .setRotation((cameraView.getFacing() == Facing.FRONT) ? FirebaseVisionImageMetadata.ROTATION_270 : FirebaseVisionImageMetadata.ROTATION_90)
                .build();
        byte[] frameData = frame.getData();
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromByteArray(frameData, metadata);
        FirebaseVisionFaceDetectorOptions options = new FirebaseVisionFaceDetectorOptions.Builder()
                .setPerformanceMode(FirebaseVisionFaceDetectorOptions.FAST)
                .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
                .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .build();

        FirebaseVisionFaceDetector faceDetector = FirebaseVision.getInstance().getVisionFaceDetector(options);
        try {
            Tasks.await(faceDetector.detectInImage(firebaseVisionImage)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                            if(firebaseVisionFaces.size() == 1){
                                txtDetected.setText("Wajah terdeteksi");
                                for (FirebaseVisionFace face : firebaseVisionFaces){
                                    Log.w(TAG, "left : " + face.getLeftEyeOpenProbability() + " right : " + face.getRightEyeOpenProbability());
                                    if(face.getLeftEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY &&
                                            face.getRightEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY &&
                                            face.getLeftEyeOpenProbability() <= LEFT_EYE_CLOSE_PROB &&
                                            face.getRightEyeOpenProbability() <= RIGHT_EYE_CLOSE_PROB &&
                                            face.getSmilingProbability() > SMILING_PROB){

                                        Log.w(TAG, "TUTUP");
                                        if(STATE_NOW == 0){
                                            STATE_NOW = STATE_BLINK_PERTAMA;
                                        }
                                    }
                                    else if(face.getLeftEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY &&
                                            face.getRightEyeOpenProbability() != FirebaseVisionFace.UNCOMPUTED_PROBABILITY &&
                                            face.getSmilingProbability() > SMILING_PROB){
                                        Log.w(TAG, "Buka");
                                        if(STATE_NOW == STATE_BLINK_PERTAMA){
                                            STATE_NOW = STATE_BLINK_KEDUA;
                                            Thread thread = new Thread();
                                            try{
                                                thread.sleep(100);
                                            }catch (Exception e){
                                                Log.w(TAG, e.getMessage());
                                            }
                                            cameraView.takePicture();
                                        }
                                    }
                                }
                            }
                            else if(firebaseVisionFaces.size() > 1){
                                txtDetected.setText("Wajah lebih dari 1");
                                STATE_NOW = 0;
                            }
                            else{
                                txtDetected.setText("Wajah tidak terdeteksi");
                                STATE_NOW = 0;
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            txtDetected.setText("Face not Detected");
                            Log.w(TAG,e.getMessage());
                        }
                    }));
        }catch (Exception e){
            Log.w(TAG, e.getMessage());
        }

    }

    class CamListener extends CameraListener{
        @Override
        public void onPictureTaken(@NonNull PictureResult result) {
            super.onPictureTaken(result);
            result.toBitmap(96, 96, new BitmapCallback() {
                @Override
                public void onBitmapReady(@Nullable Bitmap bitmap) {
                    send(bitmap);
                }
            });
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionsDelegate.resultGranted(requestCode, permissions, grantResults)) {
            hasCameraPermission = true;
            cameraView.setVisibility(View.VISIBLE);
        }
    }

    public void send(Bitmap bitmap) {
        Bitmap result = Bitmap.createScaledBitmap(bitmap, 96,96, true);
        String myBase64Image = Constans.encodeToBase64(result, Bitmap.CompressFormat.JPEG, 100);

//                ApiClientSIPKS api = ServerSIPKS.builder(PredictyActivity.this).create(ApiClientSIPKS.class);
//                Call<ResponseAll> fill = api.predict(Constans.getNip(),"data:image/jpeg;base64,"+myBase64Image,"Bearer "+Constans.getToken());
//                Log.d("test", "onImage: "+myBase64Image);

        Call<ResponseApi> predict;

        ApiClientAttendance api = ServerAttendance.builder().create(ApiClientAttendance.class);
        cmd = getIntent().getStringExtra("cmd");
        if (cmd != null && !cmd.isEmpty() && cmd.equals("absen")) {
            latitude = String.valueOf(getIntent().getDoubleExtra("latitude", 0));
            longitude = String.valueOf(getIntent().getDoubleExtra("longitude", 0));
            idAgenda = getIntent().getStringExtra("idAgenda");

            Toast.makeText(NewPredictActivity.this, latitude + ", " + longitude + ", " + idAgenda, Toast.LENGTH_SHORT).show();

            predict = api.signin(Constans.getNip(), Constans.getPassword(), latitude, longitude, idAgenda, "data:image/jpeg;base64,"+myBase64Image);
        } else {
            predict = api.predict(Constans.getNip(), Constans.getPassword(), "data:image/jpeg;base64,"+myBase64Image);
        }

        final SweetAlertDialog pDialog = new SweetAlertDialog(NewPredictActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        predict.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                if (response.code() == 200)
                {
                    pDialog.dismiss();

                    String r = response.body().getMsg();
                    String[] result = r.trim().toString().split(",");

                    if (result[0].equals("ACCEPTED")) {
                        new SweetAlertDialog(NewPredictActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Hasil")
                                .setContentText(response.body().getMsg())
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismiss();
                                        if (cmd != null && !cmd.isEmpty() && cmd.equals("absen")) {
                                            gotoSignature();
                                        }
                                    }
                                }).show();
                        STATE_NOW = 0;
                    } else {
                        new SweetAlertDialog(NewPredictActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Error")
                                .setContentText(response.body().getMsg())
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismiss();
                                    }
                                }).show();
                        STATE_NOW = 0;
                    }
                }
                else
                {
                    pDialog.dismiss();
                    new SweetAlertDialog(NewPredictActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Error")
                            .setContentText("Terjadi kesalahan, mohon ulangi kembali.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            }).show();
                    STATE_NOW = 0;
                }
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                STATE_NOW = 0;
                pDialog.dismiss();
                new SweetAlertDialog(NewPredictActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText("Terdapat masalah dengan koneksi anda, mohon ulangi kembali.")
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

    private void gotoSignature() {
        Intent intent = new Intent(NewPredictActivity.this, PredictSignatureActivity.class);
        intent.putExtra("cmd", "absen");
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("idAgenda", idAgenda);
        startActivity(intent);
        finish();
    }
}
