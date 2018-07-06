package lawyerku.customer.ui.splash;

import static lawyerku.customer.ui.login.LoginActivity.setWindowFlag;

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
import lawyerku.customer.ui.MainActivityCustomer;
import lawyerku.customer.ui.login.LoginActivity;
import lawyerku.customer.ui.register.RegisterActivity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.facebook.AccessToken;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    @Inject
    SplashPresenter presenter;

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AccessToken accessTokenFacebook = AccessToken.getCurrentAccessToken();

        initMain(accessTokenFacebook);
        transparentStatusBar();
  }

    /*@Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getCredentialModelComponent()
                .plus(new SplashActivityModule(this))
                .inject(this);
        BaseApplication.get(this).createSplashComponent(this);
    }*/

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new SplashActivityModule(this))
                .inject(this);
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
                Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
                startActivity(i);

                initActivity(accessTokenFacebook);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    public void initActivity(AccessToken accessTokenFacebook){
        Log.e("init", "initActivity: "+accessTokenFacebook );
        if(accessTokenFacebook == null){

            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
        }
        if(accessTokenFacebook != null){

            Intent i = new Intent(SplashActivity.this, MainActivityCustomer.class);
            startActivity(i);
        }

    }


}
