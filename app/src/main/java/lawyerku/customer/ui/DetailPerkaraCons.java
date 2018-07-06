package lawyerku.customer.ui;

import android.Manifest.permission;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import lawyerku.customer.R;

public class DetailPerkaraCons extends AppCompatActivity implements OnCameraIdleListener,
    OnMapReadyCallback {

  @BindView(R.id.img_msg)
  ImageView imgMsg;
  @BindView(R.id.btn_bayar)
  Button btnBayar;
  @BindView(R.id.img_maps)
  ImageView imgMaps;
  private GoogleMap mMap;
  private LocationManager lm;
  private Location mlocation;
  private double latitude = 0;
  private double longitude = 0;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_perkara_cons);
    ButterKnife.bind(this);

    LatLng indo = new LatLng(-7.803249, 110.3398253);
    initMap(indo);
    transparentStatusBar();
  }

  private void transparentStatusBar(){
    if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
      setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
    }
    if (Build.VERSION.SDK_INT >= 19) {
      getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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
  public void onCameraIdle() {

  }

  @Override
  public void onMapReady(GoogleMap googleMap) {

    mMap = googleMap;

    LatLng indonesia = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
    Log.e("map", "initMap: " + indonesia);
    initMap(indonesia);
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 16));
    mMap.setOnCameraIdleListener(this);
    if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    mMap.setMyLocationEnabled(true);

    mMap.addMarker(new MarkerOptions()
        .position(mMap.getCameraPosition().target)
        .title("Marker"));


  }

  private void initMap(LatLng latLng) {
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
        .into(imgMaps);
  }

  @Override
  public void onPointerCaptureChanged(boolean hasCapture) {

  }

  @OnClick(R.id.img_msg)
  public void onImgMsgClicked() {
    Intent intent = new Intent(DetailPerkaraCons.this, MessageActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.btn_bayar)
  public void onBtnBayarClicked() {
    LayoutInflater li = LayoutInflater.from(this);
    View promptsView = li.inflate(R.layout.activity_purchase_cons, null);

    Builder alertDialogBuilder = new Builder(
        this, R.style.MyAlertDialogStyle);

    // set prompts.xml to alertdialog builder
    alertDialogBuilder.setView(promptsView);
//        final Button btnAccept = (Button) promptsView
//                .findViewById(R.id.btnAccept);

//        userInput.setText(String.valueOf(barang.getStokBarang()));
    // set dialog message
    alertDialogBuilder
        .setCancelable(true);

    // create alert dialog
    AlertDialog alertDialog = alertDialogBuilder.create();

//        btnDecline.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View arg0) {
//
//                alertDialog.dismiss();
//
//            }
//        });

    // show it
    alertDialog.show();
  }

  @OnClick(R.id.img_maps)
  public void onViewClicked() {
  }
}
