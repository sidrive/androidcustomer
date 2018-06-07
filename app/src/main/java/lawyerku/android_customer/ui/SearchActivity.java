package lawyerku.android_customer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.android_customer.R;

public class SearchActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.lnLawyer)
    public void showProject(){
        Intent i = new Intent(SearchActivity.this,DetailPerkara.class);
        startActivity(i);
    }
}
