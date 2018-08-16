package lawyerku.customer.ui;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.R;
import lawyerku.customer.preference.GlobalPreference;
import lawyerku.customer.ui.listperkara.ListPerkaraActivity;
import lawyerku.customer.api.facebook.GetUserCallback;
import lawyerku.customer.api.facebook.User;
import lawyerku.customer.api.facebook.UserRequest;
import lawyerku.customer.ui.profilcustomer.DetailProfileActivity;
import lawyerku.customer.ui.searchlawyer.SearchLawyerActivity;
import lawyerku.customer.ui.splash.SplashActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivityCustomer extends AppCompatActivity implements GetUserCallback.IGetUserResponse {

    private static final int RC_LOC_PERM = 1001;
  private static final String TAG = "MainActivity";

  @BindView(R.id.view_progress)
  LinearLayout viewProgress;

  protected static final int REQUEST_CHECK_SETTINGS = 0x1;
  private android.location.Location mlocation;
  private LocationManager lm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_customer_cons);
    ButterKnife.bind(this);
    transparentStatusBar();
    getCurrentLocationUser();
    displayLocationSettingsRequest(this);

    checkCon(true);
    locationTask();
    checkPermission();

    showLoading(false);

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

  @OnClick(R.id.cv_profile)
  public void showProfile(){
    Intent i = new Intent(MainActivityCustomer.this, DetailProfileActivity.class);
    startActivity(i);
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

  private void displayLocationSettingsRequest(Context context) {
    GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build();
    googleApiClient.connect();

    LocationRequest locationRequest = LocationRequest.create();
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    locationRequest.setInterval(10000);
    locationRequest.setFastestInterval(10000 / 2);

    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
    builder.setAlwaysShow(true);

    PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
      @Override
      public void onResult(LocationSettingsResult result) {
        final Status status = result.getStatus();
        switch (status.getStatusCode()) {
          case LocationSettingsStatusCodes.SUCCESS:
            Log.i(TAG, "All location settings are satisfied.");
            break;
          case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
            Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

            try {
              // Show the dialog by calling startResolutionForResult(), and check the result
              // in onActivityResult().
              status.startResolutionForResult(MainActivityCustomer.this, REQUEST_CHECK_SETTINGS);
              getCurrentLocationUser();
            } catch (IntentSender.SendIntentException e) {
              Log.i(TAG, "PendingIntent unable to execute request.");
            }
            break;
          case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
            Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
            break;
        }
      }
    });
  }

  private void getCurrentLocationUser() {
    lm = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
    boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    Log.e(TAG, "getCurrentLocationUser: GPS "+isGPSEnabled );
    Log.e(TAG, "getCurrentLocationUser: Network "+isNetworkEnabled );
    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      if (isNetworkEnabled) {
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
        mlocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.e(TAG, "getCurrentLocationUser: net"+mlocation );
      } if (isGPSEnabled) {
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        mlocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.e(TAG, "getCurrentLocationUser: gps"+mlocation );
      }
    }
    Log.e(TAG, "getCurrentLocationUser: "+mlocation );
  }

  private android.location.LocationListener locationListener = new android.location.LocationListener() {
    @Override
    public void onLocationChanged(android.location.Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
  };

  @OnClick(R.id.cv_logout)
  public void logout(){
    GlobalPreference.clear();
    Intent intent = new Intent(MainActivityCustomer.this, SplashActivity.class);
    startActivity(intent);
  }

  boolean doubleBackToExitPressedOnce = false;

  @Override
  public void onBackPressed() {
//    startActivity(new Intent(MainActivityCustomer.this, MainActivityCustomer.class));
//    finish();
    if (doubleBackToExitPressedOnce) {
      //super.onBackPressed();

      Intent intent = new Intent(Intent.ACTION_MAIN);
      intent.addCategory(Intent.CATEGORY_HOME);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
      startActivity(intent);
      finish();
      System.exit(0);
      return;
    }

    this.doubleBackToExitPressedOnce = true;
    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

    new Handler().postDelayed(new Runnable() {

      @Override
      public void run() {
        doubleBackToExitPressedOnce = false;
      }
    }, 2000);
  }

  public void showLoading(boolean show) {
    if (show) {
      viewProgress.setVisibility(View.VISIBLE);
    } else {
      viewProgress.setVisibility(View.GONE);
    }
  }
}
