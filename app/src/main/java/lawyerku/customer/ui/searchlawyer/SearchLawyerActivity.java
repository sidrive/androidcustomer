package lawyerku.customer.ui.searchlawyer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.R;
import lawyerku.customer.api.model.LawyerModel;
import lawyerku.customer.base.BaseActivity;
import lawyerku.customer.base.BaseApplication;
import lawyerku.customer.ui.MainActivityCustomer;
import lawyerku.customer.ui.searchlawyer.search.SearchActivity;

public class SearchLawyerActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

  private static final String TAG = "SearcLawyer";
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
  @BindView(R.id.cv_container_create)
  CardView cvContainerCreate;
  @BindView(R.id.btn_cari_lawyer)
  Button btnCariLawyer;
  @BindView(R.id.imgMap)
  ImageView imgMap;
  @BindView(R.id.rel_map)
  RelativeLayout relMap;
  @BindView(R.id.view_progress)
  LinearLayout viewProgress;

  @Inject SearchLawyerPresenter presenter;

  private LocationManager lm;
  private android.location.Location mlocation;
  private GoogleMap mMap;

  private boolean mapMode = false;

  private double latitude = 0;
  private double longitude = 0;
  SupportMapFragment mapFragment;

  MenuItem menuDone;


  CharSequence[] bahasa = {"Indonesia", "Inggris"};
  CharSequence[] bidang = {"Pidana", "Perdata"};

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_lawyers);
    ButterKnife.bind(this);
    transparentStatusBar();

    getCurrentLocationUser();

    mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    showLoading(false);
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
    etDeskripsi.setError(null);

    LatLng latLng = mMap.getCameraPosition().target;
    Log.e(TAG, "showSerach: "+latLng );

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
    if(TextUtils.isEmpty(etDeskripsi.getText().toString())){
      etDeskripsi.setError("");
      focusView = etDeskripsi;
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
      if(focusView == etDeskripsi){
        Toast.makeText(this, "Silahkan Masukan Deskripsi Perkara", Toast.LENGTH_SHORT).show();
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

      showLoading(true);

      Log.e("skill", "showSerach: "+lang+" "+skill );
      Intent i = new Intent(SearchLawyerActivity.this, SearchActivity.class);
      Bundle bundle = new Bundle();
      bundle.putString("deskripsi",etDeskripsi.getText().toString());
      bundle.putString("languages", String.valueOf(lang));
      bundle.putString("jobskill", String.valueOf(skill));
      bundle.putString("latitude",String.valueOf(latLng.latitude));
      bundle.putString("longitude",String.valueOf(latLng.longitude));
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




  private void getCurrentLocationUser() {
    lm = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
    boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      if (isNetworkEnabled) {
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);
        mlocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.e(TAG, "getCurrentLocationUser: "+mlocation );
      } else if (isGPSEnabled) {
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
        mlocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.e(TAG, "getCurrentLocationUser: "+mlocation );
      }
    }
    Log.e(TAG, "getCurrentLocationUser: "+mlocation );
  }

  private android.location.LocationListener locationListener = new android.location.LocationListener() {
    @Override
    public void onLocationChanged(android.location.Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
  };


  @Override
  public void onMapReady(GoogleMap googleMap) {

    mMap = googleMap;

//        LatLng indonesia = new LatLng(-7.803249, 110.3398253);
    LatLng indonesia = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
    Log.e(TAG, "initMap: "+indonesia );
    initMap(indonesia);
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 16));
    mMap.setOnCameraIdleListener(this);
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    mMap.setMyLocationEnabled(true);

    mMap.addMarker(new MarkerOptions()
            .position(mMap.getCameraPosition().target)
            .title("Marker"));
  }

  public void initMap(LatLng latLng) {

      latitude = latLng.latitude;
      longitude = latLng.longitude;

    String url =
            "http://maps.googleapis.com/maps/api/staticmap?zoom=17&size=800x400&maptype=roadmap%20&markers=color:red%7Clabel:S%7C"
                    + latitude + "," + longitude + "+&sensor=false";
    Log.d("initmap", "url = " + url);
    Glide.with(this)
            .load(url)
            .placeholder(R.color.colorShadow2)
            .centerCrop()
            .dontAnimate()
            .into(imgMap);

  }

  @Override
  public void onCameraIdle() {
    LatLng latLng = mMap.getCameraPosition().target;
  }

  @Override
  public void onPointerCaptureChanged(boolean hasCapture) {

  }

//  @OnClick(R.id.imgMap)
//  void showMap() {
//    relMap.setVisibility(View.VISIBLE);
//    mapMode = true;
//    btnCariLawyer.setVisibility(View.GONE);
////        menuDone.setVisible(true);
//  }

  @OnClick(R.id.btn_maps)
  void showPeta(){
    relMap.setVisibility(View.VISIBLE);
    btnCariLawyer.setVisibility(View.GONE);
    mapMode = true;
//    menuDone.setVisible(false);
  }

  @OnClick(R.id.btnSimpanMap)
  void hideMap(){
    relMap.setVisibility(View.GONE);
    mapMode = false;
//    menuDone.setVisible(true);
    LatLng latLng = mMap.getCameraPosition().target;
    Log.e(TAG, "hideMap: "+latLng );
    btnCariLawyer.setVisibility(View.VISIBLE);
    getAddress(latLng);
    initMap(latLng);
  }



  private void getAddress(LatLng latLng) {

    Geocoder geocoder;
    List<Address> addresses = null;
    geocoder = new Geocoder(this, Locale.getDefault());

    try {
      addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

    } catch (IOException e) {
      e.printStackTrace();
    }
    Log.e(TAG, "getAddress: "+addresses.get(0));
    String fulladdress = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    String city = addresses.get(0).getSubAdminArea();
    String state = addresses.get(0).getAdminArea();
    String country = addresses.get(0).getCountryName();
    String postalCode = addresses.get(0).getPostalCode();
    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

    String [] add = fulladdress.split(",");

  }
  public void onBackPressed() {
    if(mapMode){
      relMap.setVisibility(View.GONE);
      mapMode = false;
      btnCariLawyer.setVisibility(View.VISIBLE);
    }
    else {
      startActivity(new Intent(SearchLawyerActivity.this, MainActivityCustomer.class));
      finish();
    }

  }
  public void showLoading(boolean show) {
    if (show) {
      viewProgress.setVisibility(View.VISIBLE);
    } else {
      viewProgress.setVisibility(View.GONE);
    }
  }
}
