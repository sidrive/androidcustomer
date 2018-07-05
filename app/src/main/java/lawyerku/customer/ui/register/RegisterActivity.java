package lawyerku.customer.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
import lawyerku.customer.R;
import lawyerku.customer.api.model.CredentialModel;
import lawyerku.customer.base.BaseActivity;
import lawyerku.customer.base.BaseApplication;
import lawyerku.customer.ui.login.LoginActivity;

public class RegisterActivity extends BaseActivity {
    @Inject
    RegisterPresenter presenter;


    @BindView(R.id.et_username)
    TextInputEditText txtUsername;

    @BindView(R.id.et_email)
    TextInputEditText txtEmail;

    @BindView(R.id.et_password_reg)
    TextInputEditText txtPassword;

    @BindView(R.id.et_cpassword_reg)
    TextInputEditText txtCpassword;

    @BindView(R.id.et_first_name)
    TextInputEditText txtFirstName;

    @BindView(R.id.et_last_name)
    TextInputEditText txtLastName;

    @BindView(R.id.et_address)
    TextInputEditText txtAddress;

    @BindView(R.id.et_phone_number)
    TextInputEditText txtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_cons);
        ButterKnife.bind(this);



    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new RegisterActivityModule(this))
                .inject(this);
    }

    @OnClick(R.id.btn_sign_up)
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
