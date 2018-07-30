package lawyerku.customer.ui.profilcustomer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.api.model.LawyerModel;
import lawyerku.customer.base.BaseActivity;
import lawyerku.customer.base.BaseApplication;
import lawyerku.customer.preference.GlobalPreference;
import lawyerku.customer.preference.PrefKey;
import lawyerku.customer.ui.listperkara.ListPerkaraActivity;
import lawyerku.customer.R;

public class DetailProfileActivity extends BaseActivity {

  @Inject
  DetailProfilePresenter presenter;

  @BindView(R.id.img_topup)
  ImageButton imgTopup;
  @BindView(R.id.btn_save)
  Button btnSave;
  @BindView(R.id.et_nama)
  TextInputEditText txtNama;
  @BindView(R.id.et_address)
  TextInputEditText txtAddress;
  @BindView(R.id.et_email)
  TextInputEditText txtEmail;
  @BindView(R.id.et_phone)
  TextInputEditText txtPhone;
  @BindView(R.id.et_idcard)
  TextInputEditText txtIdcard;

  public static String accessToken;
  public static LawyerModel.DataCustomer customer;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_cons);
    ButterKnife.bind(this);
    transparentStatusBar();

    accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);
    initProfile(accessToken);
  }



  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this).getAppComponent()
            .plus(new DetailProfileActivityModule(this))
            .inject(this);
  }

  private void transparentStatusBar() {
    if (VERSION.SDK_INT >= 19 && VERSION.SDK_INT < 21) {
      setWindowFlag(this, LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
    }
    if (VERSION.SDK_INT >= 19) {
      getWindow().getDecorView().setSystemUiVisibility(
          View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
    //make fully Android Transparent Status bar
    if (VERSION.SDK_INT >= 21) {
      setWindowFlag(this, LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
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

  @OnClick(R.id.img_topup)
  public void onImgTopupClicked() {
    LayoutInflater li = LayoutInflater.from(this);
    View promptsView = li.inflate(R.layout.redeem_saldo, null);

    Builder alertDialogBuilder = new Builder(
        this);

    // set prompts.xml to alertdialog builder
    alertDialogBuilder.setView(promptsView);

    final EditText userInput = (EditText) promptsView
        .findViewById(R.id.txtReedemSaldo);
//        userInput.setText(String.valueOf(barang.getStokBarang()));
    // set dialog message
    alertDialogBuilder
        .setCancelable(false)
        .setPositiveButton("Reedem",
            new OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {

//                                barang.setStokBarang(Integer.valueOf(userInput.getText().toString()));
//                                barang.setUpdateTerakhir(System.currentTimeMillis());
//                                showLoading(true,holder.viewProgress);
//                                updateStok(barang,holder);
                Toast.makeText(DetailProfileActivity.this,
                    "Saldo Berhasil Direedem, silahkan tunggu konfirmasi admin", Toast.LENGTH_LONG)
                    .show();

              }
            })
        .setNegativeButton("Cancel",
            new OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
              }
            });

    // create alert dialog
    AlertDialog alertDialog = alertDialogBuilder.create();

    // show it
    alertDialog.show();
  }

  @OnClick(R.id.btn_save)
  public void onBtnSaveClicked() {
      LawyerModel.DataUpdata dataUpdata = new LawyerModel.DataUpdata();
    Toast.makeText(this, "Profile Berhasil Disimpan", Toast.LENGTH_SHORT).show();
    txtNama.setError(null);
    txtAddress.setError(null);
    txtEmail.setError(null);
    txtIdcard.setError(null);
    txtPhone.setError(null);

    boolean cancel = false;
    View focusView = null;

    if(TextUtils.isEmpty(txtNama.getText().toString())){
      txtNama.setError(this.getBaseContext().getString(R.string.error_empty_name));
      cancel = true;
      focusView = txtNama;
    }
    if(TextUtils.isEmpty(txtAddress.getText().toString())){
      txtAddress.setError(this.getBaseContext().getString(R.string.error_empty_address));
      cancel = true;
      focusView = txtAddress;
    }
    if(TextUtils.isEmpty(txtEmail.getText().toString())){
      txtEmail.setError(this.getBaseContext().getString(R.string.error_empty_email));
      cancel = true;
      focusView = txtEmail;
    }
    if(isValidateEmail(txtEmail.getText())){
      txtEmail.setError(this.getBaseContext().getString(R.string.error_empty_email));
      cancel = true;
      focusView = txtEmail;
    }
    if(TextUtils.isEmpty(txtPhone.getText().toString())){
      txtEmail.setError(this.getBaseContext().getString(R.string.error_empty_phone));
      cancel = true;
      focusView = txtPhone;
    }
    if(cancel){
      focusView.requestFocus();
    }
    else {
        dataUpdata.id = customer.id;
        dataUpdata.first_name = txtNama.getText().toString();
        dataUpdata.address = txtAddress.getText().toString();
        dataUpdata.email = txtEmail.getText().toString();
        dataUpdata.phone_number_1 = txtPhone.getText().toString();
        dataUpdata.last_name = customer.user.username;

        presenter.saveUpdate(accessToken,dataUpdata);
    }
  }

  @OnClick(R.id.img_profile)
  public void onViewClicked() {
  }

  private void initProfile(String accessToken) {
    presenter.getAccount(accessToken);
  }

  public void showProfile(LawyerModel.DataCustomer data) {
    Log.e("profile", "showProfile: "+data );
    customer = data;

    txtNama.setText(data.name);
    txtAddress.setText(data.address);
    txtEmail.setText(data.user.email);
    txtIdcard.setText(data.idnumber);
    txtPhone.setText(data.phone1);
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

  public void showMainActivity() {
    Intent i = new Intent(DetailProfileActivity.this, ListPerkaraActivity.class);
    startActivity(i);
  }
}
