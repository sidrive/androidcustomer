package lawyerku.android_customer.ui;

import static lawyerku.android_customer.ui.LoginActivity.setWindowFlag;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.android_customer.R;

public class SearchActivity extends AppCompatActivity {

  @BindView(R.id.fl_container)
  FrameLayout flContainer;
  @BindView(R.id.fl_container2)
  FrameLayout flContainer2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search_cons);
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

  @OnClick(R.id.fl_container)
  public void showProject() {
    Intent i = new Intent(SearchActivity.this, DetailPerkaraCons.class);
    startActivity(i);
  }

  @OnClick(R.id.fl_container2)
  public void onViewClicked() {
    Intent i = new Intent(SearchActivity.this, DetailPerkaraCons.class);
    startActivity(i);
  }
}
