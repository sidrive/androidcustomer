package lawyerku.customer;

import static lawyerku.customer.ui.login.LoginActivity.setWindowFlag;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import lawyerku.customer.mainfragment.HistoryFragment;
import lawyerku.customer.mainfragment.PerkaraNewFragment.OnFragmentInteractionListener;
import lawyerku.customer.mainfragment.ViewPagerAdapter;
import lawyerku.customer.ui.DetailProfileActivity;
import lawyerku.customer.ui.DetailProfileActivityCons;
import lawyerku.customer.ui.MessageActivity;

public class MainActivityCons extends AppCompatActivity implements OnFragmentInteractionListener,
    HistoryFragment.OnFragmentInteractionListener {

  ViewPagerAdapter viewPagerAdapter;
  @BindView(R.id.tb_main)
  Toolbar tbMain;
  @BindView(R.id.tl_lawyer)
  TabLayout tlLawyer;
  @BindView(R.id.vp_lawyer)
  ViewPager vpLawyer;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_cons);
    ButterKnife.bind(this);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.parseColor("#323133"));
    }

    viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    vpLawyer.setAdapter(viewPagerAdapter);
    tlLawyer.setupWithViewPager(vpLawyer);

    setSupportActionBar(tbMain);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("History Penanganan Hukum");
    tbMain.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
    tbMain.setNavigationOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch(item.getItemId()){
      case R.id.action_profile:
        Intent i = new Intent(MainActivityCons.this, DetailProfileActivityCons.class);
        startActivity(i);
        break;
      case R.id.action_message:
        Intent a = new Intent(MainActivityCons.this, MessageActivity.class);
        startActivity(a);
        break;
    }
    return true;
  }

  @Override
  public void onFragmentInteraction(Uri uri) {

  }
}
