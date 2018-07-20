package lawyerku.customer.ui.detaillawyer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import lawyerku.customer.R;
import lawyerku.customer.api.model.LawyerModel;
import lawyerku.customer.base.BaseActivity;
import lawyerku.customer.base.BaseApplication;

public class DetailLawyerActivity extends BaseActivity {

    @Inject
    DetailLawyerPresenter presenter;

    @BindView(R.id.text_name)
    TextView txtname;

    @BindView(R.id.text_address)
    TextView txtAddress;

    @BindView(R.id.text_language)
    TextView txtLanguange;

    @BindView(R.id.text_skill)
    TextView txtSkill;

    @BindView(R.id.text_cellphone)
    TextView txtPhone;

    @BindView(R.id.text_level)
    TextView txtLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lawyer);
        ButterKnife.bind(this);
        transparentStatusBar();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Bundle bundle = getIntent().getExtras();
        presenter.getLawyer(Integer.valueOf(bundle.get("idlawyer").toString()));

    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this).getAppComponent()
                .plus(new DetailLawyerActivityModule(this))
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

    public void initLawyer(LawyerModel.Data lawyer) {
        txtname.setText(lawyer.name.toString());
        txtAddress.setText(lawyer.address_1.toString());
        txtLanguange.setText(lawyer.languageskills.get(0).name.toString());
        txtSkill.setText(lawyer.jobskills.get(0).name.toString());
        txtLevel.setText(String.valueOf(lawyer.level));
        txtPhone.setText(lawyer.cellphone1.toString());
    }
}
