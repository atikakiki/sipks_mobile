package id.ac.its.attendance.Activity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import id.ac.its.attendance.R;
import id.ac.its.attendance.Utility.Constans;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Intent intent;
    private TextView nama,sekolah,namabar,sekolahbar,bagian;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        nama = findViewById(R.id.nama);
        namabar = headerView.findViewById(R.id.nama_nav);
        sekolahbar = headerView.findViewById(R.id.textView);
        sekolah = findViewById(R.id.jabatan);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        bagian = findViewById(R.id.bagian);


        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);

        nama.setText(Constans.getNama());
        namabar.setText(Constans.getNama());
        sekolahbar.setText(Constans.getNip());
        sekolah.setText(Constans.getNip());
//        bagian.setText(Constans.getBagian());
//        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainAttendanceFragment(), "Attendance");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Keluar Aplikasi")
                    .setContentText("Apakah ingin keluar Aplikasi")
                    .setCancelText("Tidak")
                    .setConfirmText("Iya")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                            Constans.quit(MainActivity.this);
                            Constans.deleteCache(MainActivity.this);
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .show();
//            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//         Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            intent = new Intent(MainActivity.this, NewUploadActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_predict) {
            intent = new Intent(MainActivity.this, NewPredictActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_training) {
            intent = new Intent(MainActivity.this,TrainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_upload_signature) {
            intent = new Intent(MainActivity.this, UploadSignatureActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_training_signature) {
            intent = new Intent(MainActivity.this, TrainSignatureActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_predict_signature) {
            intent = new Intent(MainActivity.this, PredictSignatureActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onResume(){
        super.onResume();
    }
}
