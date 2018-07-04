package lawyerku.customer.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.R;

public class CreatePerkaraActivity extends AppCompatActivity implements
 OnCameraIdleListener, OnMapReadyCallback{

    @BindView(R.id.txtBahasa)
    TextView txtBahasa;

    @BindView(R.id.txtBidang)
    TextView txtBidang;

    @BindView(R.id.rel_map)
    RelativeLayout relMap;

    @BindView(R.id.img_marker)
    ImageView imgMap;


    CharSequence[] bahasa = {"Indonesia","Inggris"};
    CharSequence[] bidang = {"Perkara","Perdata"};

    private boolean mapMode = false;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_perkara);
        ButterKnife.bind(this);

        LatLng indonesia = new LatLng(-7.7832754, 110.3664704);
        initMap(indonesia);


    }

    @OnClick(R.id.txtBahasa)
    void showBahasa(){
        showLanguage();
    }

    @OnClick(R.id.txtBidang)
    void showBida(){
        showBidang();
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
        txtBahasa.setText(kategoris);
        Log.e("Bahasa", "handleSelectCategoryKategori: "+pos );
    }

    private void showBidang(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
        alert.setTitle("Pilih Bahasa");
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
        txtBidang.setText(kategoris);
        Log.e("Bahasa", "handleSelectCategoryKategori: "+pos );
    }

    @OnClick(R.id.btnSearchLawyer)
    public void showSearch(){
        Intent i = new Intent(CreatePerkaraActivity.this, SearchActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.txtLocation)
    void showPeta(){
        relMap.setVisibility(View.VISIBLE);
        mapMode = true;
//        menuDone.setVisible(false);
    }

    @OnClick(R.id.btnSimpanMap)
    void simpanMap(){
        relMap.setVisibility(View.GONE);
        mapMode = false;
    }

    @Override
    public void onCameraIdle() {
        LatLng latLng = mMap.getCameraPosition().target;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        LatLng indonesia = new LatLng(-7.7832754, 110.3664704);
//        LatLng indonesia = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
        Log.e("map", "initMap: "+indonesia );
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

            double latitude = latLng.latitude;
            double longitude = latLng.longitude;

        String url =
                "http://maps.googleapis.com/maps/api/staticmap?zoom=15&size=800x400&maptype=roadmap%20&markers=color:red%7Clabel:S%7C"
                        + latitude + "," + longitude + "+&sensor=false";
        Log.d("initmap", "url = " + url);
        Glide.with(this)
                .load(url)
                .placeholder(R.color.colorShadow2)
                .centerCrop()
                .dontAnimate()
                .into(imgMap);
    }


}
