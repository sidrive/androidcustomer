package lawyerku.customer.ui.rating;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.R;
import lawyerku.customer.api.model.LawyerModel;
import lawyerku.customer.api.model.PerkaraModel;
import lawyerku.customer.base.BaseActivity;
import lawyerku.customer.base.BaseApplication;
import lawyerku.customer.ui.MainActivityCustomer;

public class RatingActivity extends BaseActivity {

    @BindView(R.id.tv_nama_customer)
    TextView txtNama;
    @BindView(R.id.tv_telp)
    TextView txtTelp;
    @BindView(R.id.tv_hp)
    TextView txtHP;
    @BindView(R.id.rating)
    RatingBar ratingBar;
    @BindView(R.id.tv_comment)
    EditText txtComment;

    public static int id;
    public static int idlawyer;
    @Inject
    RatingPresenter presenter;

    public static PerkaraModel.Rating rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_rating);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        idlawyer = Integer.parseInt(bundle.getString("idlawyer"));
        id = Integer.parseInt(bundle.getString("idproject"));

        presenter.getLawyer(idlawyer);
        rating = new PerkaraModel.Rating();
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new RatingActivityModule(this))
                .inject(this);
    }

    public void initLawyer(LawyerModel.Data data) {
        txtNama.setText(data.name);
        txtTelp.setText(data.cellphone1);
        txtHP.setText(data.cellphone2);
    }

    public void validate(){
//        if(ratingBar.getRating()){
//
//        }
        rating.rating = ratingBar.getRating();
        rating.project_id = id;
        rating.comment = txtComment.getText().toString();
        Log.e("RatingActivity", "validate: "+rating );
        presenter.postRating(rating);
    }

    @OnClick(R.id.btn_submit)
    public void checkRating(){
        validate();
    }

    public void showHome(){
        Toast.makeText(this, "Rating berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(RatingActivity.this, MainActivityCustomer.class);
        startActivity(i);
    }
}
