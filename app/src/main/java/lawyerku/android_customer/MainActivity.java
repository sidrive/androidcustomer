package lawyerku.android_customer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.android_customer.mainfragment.HistoryFragment;
import lawyerku.android_customer.mainfragment.PerkaraNewFragment;
import lawyerku.android_customer.mainfragment.ViewPagerAdapter;
import lawyerku.android_customer.ui.DetailProfileActivity;
import lawyerku.android_customer.ui.MessageActivity;


public class MainActivity extends AppCompatActivity implements
        PerkaraNewFragment.OnFragmentInteractionListener,
        HistoryFragment.OnFragmentInteractionListener{

    @BindView(R.id.tabtest)
    TabLayout tabHistory;

    @BindView(R.id.viewPager)
    ViewPager viewPager1;



    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager1.setAdapter(viewPagerAdapter);
        tabHistory.setupWithViewPager(viewPager1);

        Log.e("aa", "onCreate: " );

    }


    @OnClick(R.id.lnProfile)
    public void showProfile(){
        Intent i = new Intent(MainActivity.this, DetailProfileActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.lnPesan)
    public void showPesan(){
        Intent i = new Intent(MainActivity.this, MessageActivity.class);
        startActivity(i);
    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
