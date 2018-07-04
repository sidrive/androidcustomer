package lawyerku.customer.ui;

import static lawyerku.customer.ui.login.LoginActivity.setWindowFlag;

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
import lawyerku.customer.MainActivity;
import lawyerku.customer.R;
import lawyerku.customer.MainActivityCons;

public class MainActivityCustomer extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customer_cons);
        ButterKnife.bind(this);
        transparentStatusBar();
    }

    @OnClick(R.id.cv_find)
    public void showCreatePerkara(){
        Intent i = new Intent(MainActivityCustomer.this, CreatePerkaraActivityCons.class);
        startActivity(i);
    }

    @OnClick(R.id.cv_history)
    public void showProject(){
        Intent i = new Intent(MainActivityCustomer.this, MainActivityCons.class);
        startActivity(i);
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
}
