package lawyerku.android_customer;

import android.app.Activity;
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
import lawyerku.android_customer.mainfragment.HistoryFragment;
import lawyerku.android_customer.mainfragment.PerkaraNewFragment;
import lawyerku.android_customer.mainfragment.PerkaraNewFragment.OnFragmentInteractionListener;
import lawyerku.android_customer.mainfragment.ViewPagerAdapter;
import lawyerku.android_customer.ui.DetailProfileActivity;
import lawyerku.android_customer.ui.DetailProfileActivityCons;
import lawyerku.android_customer.ui.MessageActivity;

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
    transparentStatusBar();

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


  private void transparentStatusBar() {
    if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
      setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
    }
    if (Build.VERSION.SDK_INT >= 19) {
      getWindow().getDecorView().setSystemUiVisibility(
          View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    //make fully Android Transparent Status bar
    if (Build.VERSION.SDK_INT >= 21) {
      setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
      getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
  }

  public static void setWindowFlag(Activity activity, final int bits, boolean on) {
    Window win = activity.getWindow();
    WindowManager.LayoutParams winParams = win.getAttributes();
    if (on) {
      winParams.flags |= bits;
    } else {
      winParams.flags &= ~bits;
    }
    win.setAttributes(winParams);
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
