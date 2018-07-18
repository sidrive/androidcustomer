package lawyerku.customer.ui.searchlawyer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.R;
import lawyerku.customer.api.model.LawyerModel;
import lawyerku.customer.base.BaseActivity;
import lawyerku.customer.base.BaseApplication;
import lawyerku.customer.ui.searchlawyer.search.SearchActivity;

public class SearchLawyerActivity extends BaseActivity{

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

  @Inject SearchLawyerPresenter presenter;


  CharSequence[] bahasa = {"Indonesia", "Inggris"};
  CharSequence[] bidang = {"Pidana", "Perdata"};

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_lawyers);
    ButterKnife.bind(this);
    transparentStatusBar();

  }

  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this).getAppComponent()
            .plus(new SearchLawyerActivityModule(this))
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

  @OnClick(R.id.btn_cari_lawyer)
  public void showSerach() {

    spinnerBahasa.setError(null);
    spinnerBidang.setError(null);

    boolean cancel = false;
    View focusView = null;
    if(TextUtils.equals(spinnerBahasa.getText().toString(),"Bahasa")){
      spinnerBahasa.setError("Silahkan Pilih Bahasa");
      focusView = spinnerBahasa;
      cancel = true;
    }
    if(TextUtils.equals(spinnerBidang.getText().toString(),"Bidang")){
      spinnerBidang.setError("Silahkan Pilih Bidang Keahlian");
      focusView = spinnerBidang;
      cancel = true;
    }
    if(cancel){
      focusView.requestFocus();
      if(focusView == spinnerBidang){
        Toast.makeText(this, "Silahkan Pilih Bidang Keahlian", Toast.LENGTH_SHORT).show();
      }
      if(focusView == spinnerBahasa){
        Toast.makeText(this, "Silahkan Pilih Bahasa", Toast.LENGTH_SHORT).show();
      }

    }
    else {
      String languages = spinnerBahasa.getText().toString();
      String jobskill = spinnerBidang.getText().toString();

      int lang = 0;
      int skill = 0;

      if(languages.equals("Indonesia")){
        lang = 1;
      }
      if(languages.equals("Inggris")){
        lang = 2;
      }
      if(jobskill.equals("Pidana")){
        skill = 1;
      }
      if(jobskill.equals("Perdata")){
        skill = 2;
      }

      Log.e("skill", "showSerach: "+lang+" "+skill );
      Intent i = new Intent(SearchLawyerActivity.this, SearchActivity.class);
      Bundle bundle = new Bundle();
      bundle.putString("languages", String.valueOf(lang));
      bundle.putString("jobskill", String.valueOf(skill));
      i.putExtras(bundle);
      startActivity(i);
    }
//    validateSearch();
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

  private void showLanguage() {
    final AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
    alert.setTitle("Pilih Bahasa");
    alert.setSingleChoiceItems(bahasa, 0, (dialog, which) -> {
      handleSelectCategoryBahasa(which);
//            changeLogo();
      dialog.dismiss();
    });
//        alert.setIcon(R.drawable.ic_2000px_motorbike_svg);
    alert.show();
  }

  private void handleSelectCategoryBahasa(int pos) {
//        kategoriVal = pos;
    String kategoris = bahasa[pos].toString();
    spinnerBahasa.setText(kategoris);
    Log.e("Bahasa", "handleSelectCategoryKategori: " + pos);
  }

  private void showBidang() {
    final AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
    alert.setTitle("Pilih Bidang");
    alert.setSingleChoiceItems(bidang, 0, (dialog, which) -> {
      handleSelectCategoryBidang(which);
//            changeLogo();
      dialog.dismiss();
    });
//        alert.setIcon(R.drawable.ic_2000px_motorbike_svg);
    alert.show();
  }

  private void handleSelectCategoryBidang(int pos) {
//        kategoriVal = pos;
    String kategoris = bidang[pos].toString();
    spinnerBidang.setText(kategoris);
    Log.e("Bahasa", "handleSelectCategoryKategori: " + pos);
  }

  private void validateSearch(){
    LawyerModel.Request lawyerBody = new LawyerModel.Request();

    lawyerBody.skill = 1;
    lawyerBody.language = 1;
//    createPerkaraModel.description = etDeskripsi.getText().toString();
    presenter.searchLawyer(lawyerBody);
  }


  public void listDataLawyer(List<LawyerModel.Data> listLawyer) {
    Intent i = new Intent(SearchLawyerActivity.this, SearchActivity.class);
    Bundle bundle = new Bundle();
    i.putExtras(bundle);
    startActivity(i);

  }
}
