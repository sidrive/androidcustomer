package lawyerku.customer;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.mainfragment.HistoryFragment;
import lawyerku.customer.mainfragment.PerkaraNewFragment;
import lawyerku.customer.mainfragment.ViewPagerAdapter;
import lawyerku.customer.ui.DetailProfile;
import lawyerku.customer.ui.message.MessageActivity;


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
        Intent i = new Intent(MainActivity.this, DetailProfile.class);
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
