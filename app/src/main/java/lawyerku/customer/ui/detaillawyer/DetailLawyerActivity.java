package lawyerku.customer.ui.detaillawyer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.R;
import lawyerku.customer.api.model.CreatePerkaraModel;
import lawyerku.customer.api.model.LawyerModel;
import lawyerku.customer.base.BaseActivity;
import lawyerku.customer.base.BaseApplication;
import lawyerku.customer.preference.GlobalPreference;
import lawyerku.customer.preference.PrefKey;

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

    @BindView(R.id.btn_start_date)
    Button btnStartDate;

    @BindView(R.id.btn_end_date)
    Button btnEndDate;

    public String accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);
    public static String idlawyer = null;
    public static String starDate = null;
    public static String endDate = null;
    public static String latitudeProject = null;
    public static String longitudeProjeect = null;

    Calendar myCalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_lawyer);
        ButterKnife.bind(this);
        transparentStatusBar();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        myCalender = Calendar.getInstance();

        String accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);

        Bundle bundle = getIntent().getExtras();
        idlawyer = bundle.get("idlawyer").toString();
        latitudeProject = bundle.getString("latitude").toString();
        longitudeProjeect = bundle.getString("longitude").toString();

        presenter.getLawyer(Integer.valueOf(bundle.get("idlawyer").toString()),false);

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

    @OnClick(R.id.btn_start_date)
    public void onClickStartDate(View v) {
        new DatePickerDialog(DetailLawyerActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalender.set(Calendar.YEAR,year);

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM y");
                btnStartDate.setText(sdf.format(myCalender.getTime()));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("y-M-dd");
                starDate = simpleDateFormat.format(myCalender.getTime());
            }
        },
                myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.btn_end_date)
    public void onClickEndDate(View v) {
        new DatePickerDialog(DetailLawyerActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalender.set(Calendar.YEAR,year);

                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM y");
                btnEndDate.setText(sdf.format(myCalender.getTime()));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("y-M-dd");
                endDate = simpleDateFormat.format(myCalender.getTime());
            }
        },
                myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH),
                myCalender.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.button_submit)
    public void OnClickSubmit(){

        boolean cancel = false;
        View focusView = null;
        Log.e("btn", "OnClickSubmit: "+starDate+" "+endDate);
        if(TextUtils.isEmpty(btnStartDate.getText().toString()) || btnStartDate.getText().toString().equals("Tanggal Mulai")){
            focusView = btnStartDate;
            cancel = true;
            Toast.makeText(this, "Silahkan Pilih Tanggal Mulai Project", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(btnEndDate.getText().toString()) || btnEndDate.getText().toString().equals("Tanggal Akhir")){
            focusView = btnEndDate;
            cancel = true;
            Toast.makeText(this, "Silahkan Pilih Tanggal Akhir Project", Toast.LENGTH_SHORT).show();
        }
        if(cancel){
            focusView.requestFocus();
        }
        else {
            presenter.getLawyer(Integer.valueOf(idlawyer), true);
        }
    }

    public void initProject(LawyerModel.DataProfile customer, LawyerModel.Data lawyer) {
        CreatePerkaraModel.Response.Data createPerkaraBody = new CreatePerkaraModel.Response.Data();

        createPerkaraBody.customerId = customer.id;
        createPerkaraBody.lawyerId = lawyer.id;
        createPerkaraBody.jobskill = lawyer.jobskills.get(0).id;
        createPerkaraBody.description = "Pencurian Uang";
        createPerkaraBody.latitude = Double.valueOf(latitudeProject);
        createPerkaraBody.longitude = Double.valueOf(longitudeProjeect);
        createPerkaraBody.startDate = starDate;
        createPerkaraBody.endDate = endDate;

        presenter.createPerkara(createPerkaraBody);


    }
}
