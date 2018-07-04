package lawyerku.android_customer.ui;

import static lawyerku.android_customer.ui.LoginActivity.setWindowFlag;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.android_customer.R;
import lawyerku.android_customer.ui.login.LoginActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_cons);
        ButterKnife.bind(this);
        transparentStatusBar();
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

    @OnClick(R.id.btn_reset)
    public void resetPassword(){
        Toast.makeText(this, "Password Anda telah direset, Silahkan cek Email", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.tv_back)
    public void showLoginPage(){
        Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
