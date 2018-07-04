package lawyerku.android_customer.ui;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.android_customer.MainActivityCons;
import lawyerku.android_customer.R;

public class DetailProfileActivityCons extends AppCompatActivity {


  @BindView(R.id.img_topup)
  ImageButton imgTopup;
  @BindView(R.id.btn_save)
  Button btnSave;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile_cons);
    ButterKnife.bind(this);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.setStatusBarColor(Color.parseColor("#323133"));
    }
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
                Toast.makeText(DetailProfileActivityCons.this,
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
    Toast.makeText(this, "Profile Berhasil Disimpan", Toast.LENGTH_SHORT).show();
    Intent i = new Intent(DetailProfileActivityCons.this, MainActivityCons.class);
    startActivity(i);
  }

  @OnClick(R.id.img_profile)
  public void onViewClicked() {
  }
}
