package lawyerku.android_customer.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.android_customer.R;
import lawyerku.android_customer.api.model.CredentialModel;
import lawyerku.android_customer.base.BaseActivity;
import lawyerku.android_customer.base.BaseApplication;
import lawyerku.android_customer.ui.login.LoginActivity;

public class RegisterActivity extends BaseActivity {
    @Inject
    RegisterPresenter presenter;


    @BindView(R.id.input_username)
    TextView txtUsername;

    @BindView(R.id.input_email)
    TextView txtEmail;

    @BindView(R.id.input_password)
    TextView txtPassword;

    @BindView(R.id.input_cpassword)
    TextView txtCpassword;

    @BindView(R.id.input_firstName)
    TextView txtFirstName;

    @BindView(R.id.input_lastName)
    TextView txtLastName;

    @BindView(R.id.input_Address)
    TextView txtAddress;

    @BindView(R.id.input_phone)
    TextView txtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);



    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new RegisterActivityModule(this))
                .inject(this);
    }

    @OnClick(R.id.btnSingup)
    public void register(){
        validate();
//        presenter.login();
    }

    public void logogin(){
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
    }

    private void validate(){
        CredentialModel.Request credential = new CredentialModel.Request();

        txtUsername.setError(null);
        txtEmail.setError(null);
        txtPassword.setError(null);
        txtCpassword.setError(null);
        txtFirstName.setError(null);
        txtLastName.setError(null);
        txtAddress.setError(null);
        txtPhone.setError(null);

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(txtUsername.getText().toString())){
            txtUsername.setError(this.getBaseContext().getString(R.string.error_empty_username));
            focusView = txtUsername;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtEmail.getText().toString())){
            txtEmail.setError(this.getBaseContext().getString(R.string.error_empty_email));
            focusView = txtEmail;
            cancel = true;
        }
        if(isValidateEmail(txtEmail.getText())){
            txtEmail.setError(this.getBaseContext().getString(R.string.error_empty_email));
            focusView = txtEmail;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtPassword.getText().toString())){
            txtPassword.setError(this.getBaseContext().getString(R.string.error_empty_password));
            focusView = txtPassword;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtCpassword.getText().toString())){
            txtCpassword.setError(this.getBaseContext().getString(R.string.error_empty_cpassword));
            focusView = txtCpassword;
            cancel = true;
        }
        if(!txtPassword.getText().toString().equals(txtCpassword.getText().toString())){
            txtCpassword.setError(this.getBaseContext().getString(R.string.error_empty_cpassword));
            focusView = txtCpassword;
            cancel = true;
            Log.e("password", "validate: "+txtPassword.getText()+" & "+txtCpassword.getText() );
        }
        if(TextUtils.isEmpty(txtFirstName.getText().toString())){
            txtFirstName.setError(this.getBaseContext().getString(R.string.error_empty_name));
            focusView = txtFirstName;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtLastName.getText().toString())){
            txtLastName.setError(this.getBaseContext().getString(R.string.error_empty_username));
            focusView = txtLastName;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtAddress.getText().toString())){
            txtAddress.setError(this.getBaseContext().getString(R.string.error_empty_address));
            focusView = txtAddress;
            cancel = true;
        }
        if(TextUtils.isEmpty(txtPhone.getText().toString())){
            txtPhone.setError(this.getBaseContext().getString(R.string.error_empty_phone));
            focusView = txtPhone;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }else{
            credential.username = txtUsername.getText().toString();
            credential.email = txtEmail.getText().toString();
            credential.password = txtPassword.getText().toString();
            credential.c_password = txtCpassword.getText().toString();
            credential.first_name = txtFirstName.getText().toString();
            credential.last_name = txtLastName.getText().toString();
            credential.address = txtAddress.getText().toString();
            credential.phoneNumber = txtPhone.getText().toString();
            credential.role_id = "2";
            credential.cellphone_number_1 = "0";

            presenter.register(credential,"customer");

        }

    }

    public boolean isValidateEmail(CharSequence email){
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        if (!TextUtils.isEmpty(email)){
            if (matcher.matches()){
                return false;
            }

        }
        return true;
    }
}