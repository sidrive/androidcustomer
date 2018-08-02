package lawyerku.customer.ui.splash;

import static lawyerku.customer.ui.login.LoginActivity.setWindowFlag;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import lawyerku.customer.MainActivity;
import lawyerku.customer.R;
import lawyerku.customer.base.BaseActivity;
import lawyerku.customer.base.BaseApplication;
import lawyerku.customer.preference.GlobalPreference;
import lawyerku.customer.preference.PrefKey;
import lawyerku.customer.ui.MainActivityCustomer;
import lawyerku.customer.ui.login.LoginActivity;
import lawyerku.customer.ui.register.RegisterActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    @Inject
    SplashPresenter presenter;



    private static int SPLASH_TIME_OUT = 3000;
    private static final int RC_LOC_PERM = 1001;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_cons);

        AccessToken accessTokenFacebook = AccessToken.getCurrentAccessToken();
        GlobalPreference globalPreference = new GlobalPreference();

        initMain(accessTokenFacebook);
//        init();

        transparentStatusBar();

  }



    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new SplashActivityModule(this))
                .inject(this);
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

  private void init(){
      if(GlobalPreference.read(PrefKey.loggedIn,Boolean.class)){
          Log.e("init", "init: null" );
//                boolean loggedIn = GlobalPreference.read(PrefKey.loggedIn,Boolean.class);
//                if(loggedIn){
//                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
//                    startActivity(i);
//                }
            }
            else{
          Log.e("init", "init: "+GlobalPreference.read(PrefKey.loggedIn,Boolean.class) );
      }


//
//                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
//                startActivity(i);

  }

    private void initMain(AccessToken accessTokenFacebook) {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
//                Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
//                startActivity(i);

                initActivity(accessTokenFacebook);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    public void initActivity(AccessToken accessTokenFacebook){
        Log.e("init", "initActivity: "+accessTokenFacebook );
        boolean loggedIn = GlobalPreference.read(PrefKey.loggedIn,Boolean.class);
        String accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);
        Log.e("token", "initActivity: "+accessToken );

        if(accessTokenFacebook == null && !loggedIn){
//
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            Log.e("init", "initActivity: 1" );
        }
        if(accessTokenFacebook == null && loggedIn){
//
            Intent i = new Intent(SplashActivity.this, MainActivityCustomer.class);
            startActivity(i);
            Log.e("init", "initActivity: 2" );
        }
        if(accessTokenFacebook != null && !loggedIn){

            Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
            startActivity(i);
            Log.e("init", "initActivity: 3" );
        }
        if(accessTokenFacebook != null && loggedIn){
            Intent i = new Intent(SplashActivity.this, MainActivityCustomer.class);
            startActivity(i);
            Log.e("init", "initActivity: 4");
        }

    }




}
