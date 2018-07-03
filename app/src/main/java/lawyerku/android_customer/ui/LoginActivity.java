package lawyerku.android_customer.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.android_customer.R;

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cons);
        ButterKnife.bind(this);
        transparentStatusBar();
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    private void transparentStatusBar(){
      if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
        setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
      }
      if (Build.VERSION.SDK_INT >= 19) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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

  @OnClick(R.id.btn_login)
    public void showLogin(){
        Intent i = new Intent(LoginActivity.this, MainActivityCustomer.class);
        startActivity(i);
    }

    @OnClick(R.id.tv_forgot)
    public void showForgotPasswordActivity(){
        Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.tv_register_ask)
    public void showRegisterActivity(){
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.btn_facebook)
    public void showLoginFB(){
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.login_facebook_cons, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final Button btnAccept = (Button) promptsView
                .findViewById(R.id.btn_accept);
        final Button btnDecline = (Button) promptsView
                .findViewById(R.id.btn_decline);

        btnAccept.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(getApplicationContext(), MainActivityCustomer.class);
                startActivity(i);

            }
        });


//        userInput.setText(String.valueOf(barang.getStokBarang()));
        // set dialog message
        alertDialogBuilder
                .setCancelable(false);

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        btnDecline.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {

                alertDialog.dismiss();

            }
        });

        // show it
        alertDialog.show();
    }

}
