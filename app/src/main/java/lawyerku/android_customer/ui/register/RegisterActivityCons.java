package lawyerku.android_customer.ui.register;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import lawyerku.android_customer.R;
import lawyerku.android_customer.api.model.CredentialModel;
import lawyerku.android_customer.api.model.CredentialModel.Request;
import lawyerku.android_customer.base.BaseActivity;
import lawyerku.android_customer.base.BaseApplication;
import lawyerku.android_customer.ui.login.LoginActivity;
import lawyerku.android_customer.ui.login.LoginActivityCons;

public class RegisterActivityCons extends BaseActivity {

  @Inject
  RegisterPresenter registerPresenter;
  @BindView(R.id.et_username)
  TextInputEditText etUsername;
  @BindView(R.id.et_first_name)
  TextInputEditText etFirstName;
  @BindView(R.id.et_last_name)
  TextInputEditText etLastName;
  @BindView(R.id.et_phone_number)
  TextInputEditText etPhoneNumber;
  @BindView(R.id.et_email)
  TextInputEditText etEmail;
  @BindView(R.id.et_password_reg)
  TextInputEditText etPasswordReg;
  @BindView(R.id.et_password_confirm)
  TextInputEditText etPasswordConfirm;
  @BindView(R.id.et_address)
  TextInputEditText etAddress;
  @BindView(R.id.btn_sign_up)
  Button btnSignUp;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_registration_cons);
    ButterKnife.bind(this);
    transparentStatusBar();

  }

  private void transparentStatusBar() {
    if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
      setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
    }
    if (Build.VERSION.SDK_INT >= 19) {
      getWindow().getDecorView().setSystemUiVisibility(
          View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    //make fully Android Transparent Status bar
    if (Build.VERSION.SDK_INT >= 21) {
      setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
      getWindow().setStatusBarColor(Color.TRANSPARENT);
    }
  }

  public static void setWindowFlag(Activity activity, final int bits, boolean on) {
    Window win = activity.getWindow();
    WindowManager.LayoutParams winParams = win.getAttributes();
    if (on) {
      winParams.flags |= bits;
    } else {
      winParams.flags &= ~bits;
    }
    win.setAttributes(winParams);
  }

  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this).getAppComponent()
        .plus(new RegisterActivityModule(this))
        .inject(this);

  }

  public void logogin(){
    Intent i = new Intent(RegisterActivityCons.this, LoginActivityCons.class);
    startActivity(i);
  }

  @OnClick(R.id.btn_sign_up)
  public void onViewClicked() {
    validate();
  }

  private void validate() {
    CredentialModel.Request request = new Request();

    etUsername.setError(null);
    etEmail.setError(null);
    etPasswordReg.setError(null);
    etPasswordConfirm.setError(null);
    etFirstName.setError(null);
    etLastName.setError(null);
    etAddress.setError(null);
    etPhoneNumber.setError(null);

    boolean cancel = false;
    View focusView = null;

    if (TextUtils.isEmpty(etUsername.getText().toString())) {
      etUsername.setError(this.getBaseContext().getString(R.string.error_empty_username));
      focusView = etUsername;
      cancel = true;
    } else if (TextUtils.isEmpty(etFirstName.getText().toString())) {
      etFirstName.setError(this.getBaseContext().getString(R.string.error_empty_name));
      focusView = etFirstName;
      cancel = true;
    } else if (TextUtils.isEmpty(etLastName.getText().toString())) {
      etLastName.setError(this.getBaseContext().getString(R.string.error_empty_name));
      focusView = etLastName;
      cancel = true;
    } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
      etEmail.setError(this.getBaseContext().getString(R.string.error_empty_email));
      focusView = etEmail;
      cancel = true;
    } else if (isValidateEmail(etEmail.getText())) {
      etEmail.setError(this.getBaseContext().getString(R.string.error_empty_email));
      focusView = etEmail;
      cancel = true;
    } else if (TextUtils.isEmpty(etPasswordReg.getText().toString())) {
      etPasswordReg.setError(this.getBaseContext().getString(R.string.error_empty_password));
      focusView = etPasswordReg;
      cancel = true;
    } else if (TextUtils.isEmpty(etPasswordConfirm.getText().toString())) {
      etPasswordConfirm.setError(this.getBaseContext().getString(R.string.error_empty_password));
      focusView = etPasswordConfirm;
      cancel = true;
    } else if (!etPasswordReg.getText().toString().equals(etPasswordConfirm.getText().toString())) {
      etPasswordConfirm.setError(this.getBaseContext().getString(R.string.error_empty_cpassword));
      focusView = etPasswordConfirm;
      cancel = true;
      Log.e("password",
          "validate: " + etPasswordReg.getText() + " & " + etPasswordConfirm.getText());
    } else if (TextUtils.isEmpty(etPhoneNumber.getText().toString())) {
      etPhoneNumber.setError(this.getBaseContext().getString(R.string.error_empty_phone));
      focusView = etPhoneNumber;
      cancel = true;
    } else if (TextUtils.isEmpty(etAddress.getText().toString())) {
      etAddress.setError(this.getBaseContext().getString(R.string.error_empty_password));
      focusView = etAddress;
      cancel = true;
    }
  }

  public boolean isValidateEmail (CharSequence email){
    String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher = pattern.matcher(email);
    if (!TextUtils.isEmpty(email)) {
      if (matcher.matches()) {
        return false;
      }

    }
    return true;

  }
}