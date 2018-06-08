package lawyerku.android_customer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.android_customer.MainActivity;
import lawyerku.android_customer.R;

public class MainActivityCustomer extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customer);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.button_search)
    public void showCreatePerkara(){
        Intent i = new Intent(MainActivityCustomer.this, CreatePerkaraActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.button_history)
    public void showProject(){
        Intent i = new Intent(MainActivityCustomer.this, MainActivity.class);
        startActivity(i);
    }
}
