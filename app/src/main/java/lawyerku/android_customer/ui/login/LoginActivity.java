package lawyerku.android_customer.ui.login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.android_customer.R;
import lawyerku.android_customer.api.model.CredentialModel;
import lawyerku.android_customer.base.BaseActivity;
import lawyerku.android_customer.base.BaseApplication;
import lawyerku.android_customer.ui.ForgotPasswordActivity;
import lawyerku.android_customer.ui.MainActivityCustomer;
import lawyerku.android_customer.ui.RegisterActivity;

public class LoginActivity extends BaseActivity{

    @Inject
    LoginPresenter presenter;

    @BindView(R.id.txtEmail)
    TextView txtEmail;

    @BindView(R.id.txtPassword)
    TextView txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new LoginActivityModule(this))
                .inject(this);
    }

    @OnClick(R.id.btnLogin)
    public void showLogin(){

        CredentialModel.Request credential = new CredentialModel.Request();
        credential.email = txtEmail.getText().toString();
        credential.password = txtPassword.getText().toString();

        presenter.validateLogin(credential);

//        Intent i = new Intent(LoginActivity.this, MainActivityCustomer.class);
//        startActivity(i);
    }

    @OnClick(R.id.txtForgotPassword)
    public void showForgotPasswordActivity(){
        Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.txtRegister)
    public void showRegisterActivity(){
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.btnLoginFb)
    public void showLoginFB(){
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.loginfacebook, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final Button btnAccept = (Button) promptsView
                .findViewById(R.id.btnAccept);
        final Button btnDecline = (Button) promptsView
                .findViewById(R.id.btnDecline);

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

}
