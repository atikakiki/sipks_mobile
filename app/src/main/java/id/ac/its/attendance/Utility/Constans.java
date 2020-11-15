package id.ac.its.attendance.Utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import androidx.core.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;

import id.ac.its.attendance.Activity.FirstActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Constans {
    public static String password;
    public static void setPassword(String password) {
        Constans.password = password;
    }

    public static String getPassword() {
        return password;
    }

    public static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Constans.token = token;
    }
    public static String id_sekolah;

    public static String getId_sekolah() {
        return id_sekolah;
    }

    public static void setId_sekolah(String id_sekolah) {
        Constans.id_sekolah = id_sekolah;
    }

    public static String nama;

    public static String getNama() {
        return nama;
    }

    public static void setNama(String nama) {
        Constans.nama = nama;
    }

    public static String nip;

    public static String getNip() {
        return nip;
    }

    public static void setNip(String nip) {
        Constans.nip = nip;
    }

    public static String nama_sekolah;

    public static String getNama_sekolah() {
        return nama_sekolah;
    }

    public static void setNama_sekolah(String nama_sekolah) {
        Constans.nama_sekolah = nama_sekolah;
    }

    public static String Posisi;

    public static String getPosisi() {
        return Posisi;
    }

    public static void setPosisi(String posisi) {
        Posisi = posisi;
    }

    public static String bagian;

    public static String getBagian() {
        return bagian;
    }

    public static void setBagian(String bagian) {
        Constans.bagian = bagian;
    }
    public static String rekening;

    public static String getRekening() {
        return rekening;
    }

    public static void setRekening(String rekening) {
        Constans.rekening = rekening;
    }

    public static  String imei;

    public static String getImei() {
        return imei;
    }

    public static void setImei(String imei) {
        Constans.imei = imei;
    }

    public static String getUniqueIMEIId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            String imei = telephonyManager.getDeviceId();
            Log.e("imei", "=" + imei);
            if (imei != null && !imei.isEmpty()) {
                return imei;
            } else {
                return android.os.Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "not_found";
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static void quit(Context context) {
        Intent start = new Intent(context, FirstActivity.class);
        start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        start.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(start);
        ((Activity)context).finish();
    }
}
