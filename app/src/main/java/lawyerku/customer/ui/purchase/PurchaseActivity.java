package lawyerku.customer.ui.purchase;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.google.android.gms.maps.model.LatLng;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.R;
import lawyerku.customer.api.model.PerkaraModel;
import lawyerku.customer.base.BaseActivity;
import lawyerku.customer.base.BaseApplication;
import lawyerku.customer.ui.dialog.DialogUploadOption;
import lawyerku.customer.utils.ImageUtils;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class PurchaseActivity extends BaseActivity implements DialogUploadOption.OnDialogUploadOptionClickListener, EasyPermissions.PermissionCallbacks {

    @Inject
    PurchasePresenter presenter;

    @BindView(R.id.tv_biaya)
    TextView tvBiaya;
    @BindView(R.id.et_bankname)
    TextView tvBankName;
    @BindView(R.id.et_reknumber)
    TextView tvRekNumber;
    @BindView(R.id.et_nominal)
    TextView tvNominal;
    @BindView(R.id.btnaddNota)
    FloatingActionButton btnAdd;
    @BindView(R.id.imgNota)
    ImageView imgNota;
    @BindView(R.id.btn_input)
    Button btnBayar;

    public static int id;
    public static List<PerkaraModel.Response.Data> perkara;

    private static final String TAG = "PurchaseActivity";
    public static final int REQUST_CODE_CAMERA = 1002;
    public static final int REQUST_CODE_GALLERY = 1001;
    private static final int RC_CAMERA_PERM = 205;
    public static Uri mCapturedImageURI;
    public static String realpath;
    byte[] imgSmall;
    Uri imgOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_cons);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        id = Integer.parseInt(bundle.getString("id"));

        presenter.getProject(id);

    }


    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new PurchaseActivityModule(this))
                .inject(this);
    }

    public void initPayment(List<PerkaraModel.Response.Data> data) {
        perkara = data;
    }

    @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    // Save the user's current game state
    if (mCapturedImageURI != null) {
      savedInstanceState.putString("mUriKey", mCapturedImageURI.toString());
    }

    // Always call the superclass so it can save the view hierarchy state
    super.onSaveInstanceState(savedInstanceState);
  }

  public void onRestoreInstanceState(Bundle savedInstanceState) {
    // Always call the superclass so it can restore the view hierarchy
    super.onRestoreInstanceState(savedInstanceState);

    // Restore state members from saved instance
    mCapturedImageURI = Uri.parse(savedInstanceState.getString("mUriKey"));
    Log.d("Restore state", "mCapturedImageURI = " + mCapturedImageURI);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUST_CODE_CAMERA) {
      if (resultCode == Activity.RESULT_OK) {
        Uri uri = mCapturedImageURI;
        handleCrop(uri);

//          String realpath;
//          if(mCapturedImageURI.getScheme().equals("content")){
//              realpath = ImageUtils.getRealPathFromURI(PurchaseActivity.this,data.getData());
//          }
//          else {
//              realpath = data.getData().getPath();
//          }
//          if(realpath != null){
//              File file = new File(realpath);
//
//          }
      }
    }

    if (requestCode == REQUST_CODE_GALLERY) {
      if (resultCode == Activity.RESULT_OK) {
        Uri uri = data.getData();
//        handleCrop(uri);

      }
    }

    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
      CropImage.ActivityResult result = CropImage.getActivityResult(data);
      if (resultCode == Activity.RESULT_OK) {
        Uri uri = result.getUri();
        imgOriginal = uri;
          RequestBody image = RequestBody.create(MediaType.parse("image/*"), String.valueOf(uri));
          Log.e(TAG, "onActivityResult: "+imgOriginal);
          Log.e(TAG, "onActivityResult: "+mCapturedImageURI);

        try {
          Bitmap bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
          imgNota.setImageBitmap(bitmap2);
          encodeBitmapAndSaveToFirebase(bitmap2);

        } catch (IOException e) {
          e.printStackTrace();
        }


          if(mCapturedImageURI.getScheme().equals("content")){
              realpath = ImageUtils.getRealPathFromURI(PurchaseActivity.this,mCapturedImageURI);
          }
          else {
              realpath = data.getData().getPath();
          }
          if(realpath != null){
              File file = new File(realpath);

          }


      } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        Exception error = result.getError();
        error.printStackTrace();
      }
    }
  }

    @OnClick(R.id.btnaddNota)
    public void ShowImageOption(){
        DialogUploadOption dialogUploadOption = new DialogUploadOption(this);
        dialogUploadOption.show();
    }

    @Override
  public void onGalleryClicked(Dialog dialog) {
    galeryTask();
    dialog.dismiss();
  }

  @Override
  public void onCameraClicked(Dialog dialog) {
    cameraTask();
    dialog.dismiss();
  }

  private void handleCrop(Uri uri) {
    CropImage.activity(uri)
            .setCropShape(CropImageView.CropShape.RECTANGLE)
            .setAspectRatio(900, 1280)
            .start(this);
  }

  public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
    imgSmall = baos.toByteArray();

  }

  private SecureRandom random = new SecureRandom();

  public String nextSessionId() {
    return new BigInteger(130, random).toString(32);
  }

  public void onLaunchCamera() {
    Intent intent;
    String fileName = nextSessionId();
    ContentValues values = new ContentValues();
    values.put(MediaStore.Images.Media.TITLE, fileName);
    mCapturedImageURI = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
    if (intent.resolveActivity(this.getPackageManager()) != null) {
      startActivityForResult(intent, REQUST_CODE_CAMERA);
    }
  }

  public void onLaunchGallery() {
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(
            "content://media/internal/images/media"));
    intent.setAction(Intent.ACTION_GET_CONTENT);

    intent.setType("image/*");
    startActivityForResult(intent, REQUST_CODE_GALLERY);
  }

  @AfterPermissionGranted(RC_CAMERA_PERM)
  public void cameraTask() {
    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
      // Have permission, do the thing!
      onLaunchCamera();
    } else {
      // Ask for one permission
      EasyPermissions.requestPermissions(this, getString(R.string.ijin_camera),
              RC_CAMERA_PERM, perms);
    }
  }

  @AfterPermissionGranted(RC_CAMERA_PERM)
  public void galeryTask() {
    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
      // Have permission, do the thing!
      onLaunchGallery();
    } else {
      // Ask for one permission
      EasyPermissions.requestPermissions(this, getString(R.string.ijin_camera),
              RC_CAMERA_PERM, perms);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    // EasyPermissions handles the request result.
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  @Override
  public void onPermissionsGranted(int requestCode, List<String> perms) {
    Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
  }

  @Override
  public void onPermissionsDenied(int requestCode, List<String> perms) {
    Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

    // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
    // This will display a dialog directing them to enable the permission in app settings.
    if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
      new AppSettingsDialog.Builder(this).build().show();
    }
  }

  @OnClick(R.id.btn_input)
    public void savePurchase(){
      presenter.savePurchase(perkara,imgOriginal);
  }
}
