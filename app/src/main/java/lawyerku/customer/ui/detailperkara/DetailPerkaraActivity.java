package lawyerku.customer.ui.detailperkara;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import javax.inject.Inject;

import lawyerku.customer.R;
import lawyerku.customer.api.model.LawyerModel;
import lawyerku.customer.api.model.PerkaraModel;
import lawyerku.customer.base.BaseActivity;
import lawyerku.customer.base.BaseApplication;
import lawyerku.customer.ui.MainActivityCustomer;
import lawyerku.customer.ui.MessageActivity;
import lawyerku.customer.ui.dialog.DialogUploadOption;
import lawyerku.customer.ui.purchase.PurchaseActivity;
import lawyerku.customer.utils.DateFormatter;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class DetailPerkaraActivity extends BaseActivity implements OnCameraIdleListener,
    OnMapReadyCallback {

  @Inject
  DetailPerkaraPresenter presenter;

  @BindView(R.id.img_msg)
  ImageView imgMsg;
  @BindView(R.id.btn_bayar)
  Button btnBayar;
  @BindView(R.id.img_maps)
  ImageView imgMaps;

  @BindView(R.id.tv_nama_customer)
  TextView txtNamaCustomer;

  @BindView(R.id.tv_bidang_hukum)
  TextView txtbidangHukum;

  @BindView(R.id.tv_desc)
  TextView txtdescription;

  @BindView(R.id.tv_nama_lawyer)
  TextView txtnamaLawyer;

  @BindView(R.id.tv_telp)
  TextView txttelpLawyer;

  @BindView(R.id.tv_hp)
  TextView txthpLawyer;

  @BindView(R.id.tv_status)
  TextView txtStatus;

  @BindView(R.id.tv_tgl_mulai)
  TextView txtStartDate;

  @BindView(R.id.tv_tgl_selesai)
  TextView txtEndDate;

  @BindView(R.id.view_progress)
  LinearLayout viewProgress;

  private GoogleMap mMap;
  private LocationManager lm;
  private Location mlocation;
  private double latitude = 0;
  private double longitude = 0;
  public static int id;
  public static List<PerkaraModel.Response.Data> perkara;

  private static final String TAG = "DetailPerkaraActivity";
  public static final int REQUST_CODE_CAMERA = 1002;
  public static final int REQUST_CODE_GALLERY = 1001;
  private static final int RC_CAMERA_PERM = 205;
  public static Uri mCapturedImageURI;
  byte[] imgSmall;
  Uri imgOriginal;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_perkara_cons);
    ButterKnife.bind(this);

//    LatLng indo = new LatLng(-7.803249, 110.3398253);
//    initMap(indo);
    transparentStatusBar();

    Bundle bundle = getIntent().getExtras();
    Log.e("detailperkara", "onCreate: "+bundle );
    id = Integer.parseInt(bundle.getString("id"));

    presenter.getProject(id);
  }

  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this).getAppComponent()
            .plus(new DetailPerkaraActivityModule(this))
            .inject(this);
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
//    initMap(indonesia);
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
    Intent intent = new Intent(DetailPerkaraActivity.this, MessageActivity.class);
    startActivity(intent);
  }

  @OnClick(R.id.btn_bayar)
  public void onBtnBayarClicked() {
    Intent intent = new Intent(DetailPerkaraActivity.this, PurchaseActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString("id",String.valueOf(perkara.get(0).id));
    intent.putExtras(bundle);
    startActivity(intent);
  }



  @OnClick(R.id.img_maps)
  public void onViewClicked() {
  }

  public void initProject(List<PerkaraModel.Response.Data> data) {
    perkara = data;
    Log.e("DetailPerkara", "initProject: "+data );
    LatLng latLng = new LatLng(data.get(0).gps_latitude,data.get(0).gps_longitud);
    initMap(latLng);
    txtNamaCustomer.setText(data.get(0).customer.name);
    txtbidangHukum.setText(data.get(0).jobskill.name);
    txtdescription.setText(data.get(0).description);
    txtnamaLawyer.setText(data.get(0).lawyer.lawyername);
    txthpLawyer.setText(data.get(0).lawyer.lawyerPhone2);
    txttelpLawyer.setText(data.get(0).lawyer.lawyerPhone1);
    if(data.get(0).last_status == null){
      btnBayar.setVisibility(View.GONE);
    }
    if(data.get(0).last_status.status.toString().equals("new")){
      btnBayar.setVisibility(View.GONE);
      txthpLawyer.setVisibility(View.GONE);
      txttelpLawyer.setVisibility(View.GONE);
    }
    if(data.get(0).last_status.status.toString().equals("confirmed")){
      btnBayar.setVisibility(View.VISIBLE);
    }

    if(data.get(0).last_status.status.equals("new"))
    {
      txtStatus.setText("Status : Baru");
    }
    if(data.get(0).last_status.status.equals("on-progress"))
    {
      txtStatus.setText("Status : Sedang Berlangsung");
      btnBayar.setVisibility(View.VISIBLE);
      txthpLawyer.setVisibility(View.VISIBLE);
      txttelpLawyer.setVisibility(View.VISIBLE);
    }
    if(data.get(0).last_status.status.equals("rejected"))
    {
      txtStatus.setText("Status : Ditolak");
      btnBayar.setVisibility(View.GONE);
      txthpLawyer.setVisibility(View.GONE);
      txttelpLawyer.setVisibility(View.GONE);
    }
    if(data.get(0).last_status.status.equals("canceled"))
    {
      txtStatus.setText("Status : Dibatalkan");
      btnBayar.setVisibility(View.GONE);
      txthpLawyer.setVisibility(View.GONE);
      txttelpLawyer.setVisibility(View.GONE);
    }
    if(data.get(0).last_status.status.equals("finished"))
    {
      txtStatus.setText("Status : Selesai");
      txthpLawyer.setVisibility(View.VISIBLE);
      txttelpLawyer.setVisibility(View.VISIBLE);
    }

    String starDate = DateFormatter.getDate(data.get(0).start_date,"yyyy-MM-dd");
    String endDate = DateFormatter.getDate(data.get(0).end_date,"yyyy-MM-dd");

    txtStartDate.setText(starDate);
    txtEndDate.setText(endDate);

    showLoading(false);
  }

  public void showLoading(boolean show) {
    if (show) {
      viewProgress.setVisibility(View.VISIBLE);
    } else {
      viewProgress.setVisibility(View.GONE);
    }
  }

  public void onBackPressed() {
    startActivity(new Intent(DetailPerkaraActivity.this, MainActivityCustomer.class));
    finish();
  }

  @OnClick(R.id.btn_home)
  public void returnHome(){
    startActivity(new Intent(DetailPerkaraActivity.this, MainActivityCustomer.class));
    finish();
  }

}
