package lawyerku.android_customer.ui;

import static lawyerku.android_customer.ui.LoginActivity.setWindowFlag;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.android_customer.R;

public class CreatePerkaraActivityCons extends AppCompatActivity {

  @BindView(R.id.img_logo)
  ImageView imgLogo;
  @BindView(R.id.spinner_bahasa)
  TextView spinnerBahasa;
  @BindView(R.id.spinner_bidang)
  TextView spinnerBidang;
  @BindView(R.id.et_deskripsi)
  TextInputEditText etDeskripsi;
  @BindView(R.id.til_description)
  TextInputLayout tilDescription;
  @BindView(R.id.btn_maps)
  Button btnMaps;
  @BindView(R.id.img_maps)
  ImageView imgMaps;
  @BindView(R.id.cv_container_create)
  CardView cvContainerCreate;
  @BindView(R.id.btn_cari_lawyer)
  Button btnCariLawyer;


  CharSequence[] bahasa = {"Indonesia","Inggris"};
  CharSequence[] bidang = {"Perkara","Perdata"};

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_create_perkara_cons);
    ButterKnife.bind(this);
    transparentStatusBar();
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

  @OnClick(R.id.btn_cari_lawyer)
  public void showSerach() {
    Intent i = new Intent(CreatePerkaraActivityCons.this, SearchActivity.class);
    startActivity(i);
  }

  @OnClick({R.id.spinner_bahasa, R.id.spinner_bidang})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.spinner_bahasa:
        showLanguage();
        break;
      case R.id.spinner_bidang:
        showBidang();
        break;
    }
  }

  private void showLanguage(){
    final AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
    alert.setTitle("Pilih Bahasa");
    alert.setSingleChoiceItems(bahasa, 0, (dialog, which) -> {
      handleSelectCategoryBahasa(which);
//            changeLogo();
      dialog.dismiss();
    });
//        alert.setIcon(R.drawable.ic_2000px_motorbike_svg);
    alert.show();
  }

  private void handleSelectCategoryBahasa(int pos){
//        kategoriVal = pos;
    String kategoris = bahasa[pos].toString();
    spinnerBahasa.setText(kategoris);
    Log.e("Bahasa", "handleSelectCategoryKategori: "+pos );
  }

  private void showBidang(){
    final AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
    alert.setTitle("Pilih Bidang");
    alert.setSingleChoiceItems(bidang, 0, (dialog, which) -> {
      handleSelectCategoryBidang(which);
//            changeLogo();
      dialog.dismiss();
    });
//        alert.setIcon(R.drawable.ic_2000px_motorbike_svg);
    alert.show();
  }

  private void handleSelectCategoryBidang(int pos){
//        kategoriVal = pos;
    String kategoris = bidang[pos].toString();
    spinnerBidang.setText(kategoris);
    Log.e("Bahasa", "handleSelectCategoryKategori: "+pos );
  }
}
