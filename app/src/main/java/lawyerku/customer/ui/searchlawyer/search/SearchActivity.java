package lawyerku.customer.ui.searchlawyer.search;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.R;
import lawyerku.customer.api.adapter.AdapterLawyer;
import lawyerku.customer.api.model.LawyerModel;
import lawyerku.customer.base.BaseActivity;
import lawyerku.customer.base.BaseApplication;
import lawyerku.customer.ui.MainActivityCustomer;
import lawyerku.customer.ui.detaillawyer.DetailLawyerActivity;
import lawyerku.customer.ui.searchlawyer.SearchLawyerActivity;

public class SearchActivity extends BaseActivity {
  private static final String TAG = "SearchActivity";
  @Inject
  SearchPresenter presenter;

  @BindView(R.id.rcvLawyer)
  RecyclerView lsLawyer;

  @BindView(R.id.view_progress)
  LinearLayout viewProgress;

  private AdapterLawyer adapterLawyer;
  public static String latitudeProject = null;
  public static String longitudeProjeect = null;
  public static String deskripsiProject;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_cons);
    ButterKnife.bind(this);
    transparentStatusBar();

    showLoading(true);
    LawyerModel.Request requestBody = new LawyerModel.Request();
    initRecycleView();

    Bundle bundle = getIntent().getExtras();

    requestBody.language = Integer.valueOf(bundle.get("languages").toString());
    requestBody.skill = Integer.valueOf(bundle.get("jobskill").toString());
    requestBody.latitude = bundle.getString("latitude").toString();
    requestBody.longitude = bundle.getString("longitude").toString();
    presenter.searchLawyer(requestBody);

    latitudeProject = bundle.getString("latitude").toString();
    longitudeProjeect = bundle.getString("longitude").toString();
    deskripsiProject = bundle.get("deskripsi").toString();
  }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new SearchActivityModule(this))
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


  private void initRecycleView() {
    lsLawyer.setHasFixedSize(true);
    lsLawyer.addItemDecoration(
            new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    lsLawyer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    lsLawyer.setNestedScrollingEnabled(false);
  }

  public void initListLawyer(List<LawyerModel.Data> listLawyer){
    Log.e(TAG, "initListLawyer: "+listLawyer );
    adapterLawyer = new AdapterLawyer((ArrayList<LawyerModel.Data>) listLawyer,this, this);
////        adapterStatusMotor.UpdateMotor(listMotor);
    lsLawyer.setAdapter(adapterLawyer);
    showLoading(false);
  }

  public void createProject(LawyerModel.Data lawyer) {
    Log.e(TAG, "createProject: "+lawyer );
    showLoading(true);
    Intent i = new Intent(SearchActivity.this, DetailLawyerActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString("idlawyer", String.valueOf(lawyer.id));
    bundle.putString("deskripsi",deskripsiProject);
    bundle.putString("latitude",latitudeProject);
    bundle.putString("longitude",longitudeProjeect);
    i.putExtras(bundle);
    startActivity(i);
  }



  public void showLoading(boolean show) {
    if (show) {
      viewProgress.setVisibility(View.VISIBLE);
    } else {
      viewProgress.setVisibility(View.GONE);
    }
  }

  public void onBackPressed() {
    startActivity(new Intent(SearchActivity.this, SearchLawyerActivity.class));
    finish();
    showLoading(false);
  }
}
