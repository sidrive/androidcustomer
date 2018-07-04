package lawyerku.android_customer.ui;

import static lawyerku.android_customer.ui.LoginActivity.setWindowFlag;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

<<<<<<< HEAD
import android.view.View;
import android.view.WindowManager;
import lawyerku.android_customer.MainActivity;
=======
>>>>>>> develop
import lawyerku.android_customer.R;
import lawyerku.android_customer.ui.register.RegisterActivity;

public class SplashActivity extends AppCompatActivity {


  private static int SPLASH_TIME_OUT = 3000;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_cons);

    initMain();
    transparentStatusBar();
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

  private void initMain() {
    new Handler().postDelayed(new Runnable() {

      /*
       * Showing splash screen with a timer. This will be useful when you
       * want to show case your app logo / company
       */

      @Override
      public void run() {
        // This method will be executed once the timer is over
        // Start your app main activity
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);

        // close this activity
        finish();
      }
    }, SPLASH_TIME_OUT);

  }


}
