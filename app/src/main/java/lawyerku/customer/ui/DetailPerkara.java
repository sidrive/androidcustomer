package lawyerku.customer.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.MainActivity;
import lawyerku.customer.R;

public class DetailPerkara extends AppCompatActivity implements GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback {


    private GoogleMap mMap;
    private LocationManager lm;
    private android.location.Location mlocation;
    private double latitude = 0;
    private double longitude = 0;

    @BindView(R.id.imgMap)
    ImageView imgMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_perkara);
        ButterKnife.bind(this);

        LatLng indonesia = new LatLng(-7.803249, 110.3398253);
        initMap(indonesia);

    }

    @OnClick(R.id.button_call_customer)
    public void showInbox(){
        Intent i = new Intent(DetailPerkara.this, MessageActivity.class);
        startActivity(i);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

//        LatLng indonesia = new LatLng(-7.803249, 110.3398253);
        LatLng indonesia = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
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

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick(R.id.btnBayar)
    public void showPayment(){
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.purchase_activity, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this,R.style.MyAlertDialogStyle);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

//        final Button btnAccept = (Button) promptsView
//                .findViewById(R.id.btnAccept);




//        userInput.setText(String.valueOf(barang.getStokBarang()));
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

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
}
