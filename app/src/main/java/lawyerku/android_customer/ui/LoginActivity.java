package lawyerku.android_customer.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.android_customer.MainActivity;
import lawyerku.android_customer.R;

public class LoginActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnLogin)
    public void showLogin(){
        Intent i = new Intent(LoginActivity.this, MainActivityCustomer.class);
        startActivity(i);
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

}
