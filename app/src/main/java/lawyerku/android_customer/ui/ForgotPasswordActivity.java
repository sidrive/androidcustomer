package lawyerku.android_customer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.android_customer.R;
import lawyerku.android_customer.ui.login.LoginActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnReset)
    public void resetPassword(){
        Toast.makeText(this, "Password Anda telah direset, Silahkan cek Email", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.txtLoginpage)
    public void showLoginPage(){
        Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
