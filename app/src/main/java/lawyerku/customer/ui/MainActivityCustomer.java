package lawyerku.customer.ui;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.R;
import lawyerku.customer.ui.listperkara.ListPerkaraActivity;
import lawyerku.customer.api.facebook.GetUserCallback;
import lawyerku.customer.api.facebook.User;
import lawyerku.customer.api.facebook.UserRequest;
import lawyerku.customer.ui.searchlawyer.SearchLawyerActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivityCustomer extends AppCompatActivity implements GetUserCallback.IGetUserResponse {

    private static final int RC_LOC_PERM = 1001;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_customer_cons);
    ButterKnife.bind(this);
    transparentStatusBar();


    checkCon(true);
    locationTask();
    checkPermission();


  }

  @AfterPermissionGranted(RC_LOC_PERM)
  public void locationTask() {
    String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION};
    if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
      // Have permission, do the thing!
//            onLaunchCamera();
    } else {
      // Ask for one permission
      EasyPermissions.requestPermissions(this, getString(R.string.ijin_lokasi),
              RC_LOC_PERM, perms);
    }
  }

  public void checkPermission(){
    String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION};
    if (!EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
      EasyPermissions.requestPermissions(this, getString(R.string.ijin_lokasi),
              RC_LOC_PERM, perms);
    }
    Log.e("MainAct", "onCreate: "+EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION) );
  }

  public void checkCon(boolean enabled){


    try {
      final ConnectivityManager conman = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
      final Class<?> conmanClass = Class.forName(conman.getClass().getName());
      final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
      iConnectivityManagerField.setAccessible(true);
      final Object iConnectivityManager = iConnectivityManagerField.get(conman);
      final Class<?> iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
      final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
      setMobileDataEnabledMethod.setAccessible(true);
      setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    checkStat();
  }

  public void checkStat(){
    ConnectivityManager connect = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    Log.e("cyes", "checkCon: "+connect.getNetworkInfo(0).getState() );

  }

  @OnClick(R.id.cv_find)
  public void showCreatePerkara() {
    Intent i = new Intent(MainActivityCustomer.this, SearchLawyerActivity.class);
    startActivity(i);
  }

  @OnClick(R.id.cv_history)
  public void showProject() {
    Intent i = new Intent(MainActivityCustomer.this, ListPerkaraActivity.class);
    startActivity(i);
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
  protected void onResume() {
    super.onResume();
    UserRequest.makeUserRequest(new GetUserCallback(MainActivityCustomer.this).getCallback());
  }

  @Override
  public void onCompleted(User user) {

  }
}
