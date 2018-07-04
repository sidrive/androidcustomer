package lawyerku.customer.ui.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.R;
import lawyerku.customer.api.model.CredentialModel;
import lawyerku.customer.base.BaseActivity;
import lawyerku.customer.base.BaseApplication;
import lawyerku.customer.ui.ForgotPasswordActivity;
import lawyerku.customer.ui.MainActivityCustomer;
import lawyerku.customer.ui.register.RegisterActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginActivity extends BaseActivity{

    @Inject
    LoginPresenter presenter;

    @BindView(R.id.txtEmail)
    TextView txtEmail;

    @BindView(R.id.txtPassword)
    TextView txtPassword;

    private LoginButton loginButton;
    private static final String EMAIL = "email";

    CallbackManager callbackManager = CallbackManager.Factory.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cons);
        ButterKnife.bind(this);
        transparentStatusBar();
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null){
            Log.e("Tag", "onCreate: "+accessToken.getPermissions());
        }


        initFBbutton();


    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new LoginActivityModule(this))
                .inject(this);
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

        CredentialModel.Request credential = new CredentialModel.Request();
        credential.email = txtEmail.getText().toString();
        credential.password = txtPassword.getText().toString();

        presenter.validateLogin(credential);

//        Intent i = new Intent(LoginActivity.this, MainActivityCustomer.class);
//        startActivity(i);
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

    public void loginProses(){
        Intent i = new Intent(LoginActivity.this, MainActivityCustomer.class);
        startActivity(i);
    }

    private void initFBbutton() {

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                loginProses();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}