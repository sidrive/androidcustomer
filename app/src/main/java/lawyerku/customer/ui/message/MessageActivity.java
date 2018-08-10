package lawyerku.customer.ui.message;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lawyerku.customer.R;
import lawyerku.customer.api.adapter.AdapterMessage;
import lawyerku.customer.api.model.MessageModel;
import lawyerku.customer.base.BaseActivity;
import lawyerku.customer.base.BaseApplication;
import lawyerku.customer.preference.GlobalPreference;
import lawyerku.customer.preference.PrefKey;
import lawyerku.customer.utils.DateFormatter;

import static android.support.constraint.Constraints.TAG;

public class MessageActivity extends BaseActivity {

  @Inject
  MessagePresenter presenter;

  @BindView(R.id.et_message)
  TextView txtMessage;
  @BindView(R.id.rv_message)
  RecyclerView rvMessage;

  public static String accessToken;
  public static int idcustomer;
  public static int idlawyer;
  public static int idproject;
  public static MessageModel.MessageToSend pesan = new MessageModel.MessageToSend();
  public static AdapterMessage adapterMessage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inbox_cons);
    ButterKnife.bind(this);

    transparentStatusBar();

    initRecycleView(rvMessage);

    Bundle bundle = getIntent().getExtras();
    idcustomer = Integer.parseInt(bundle.getString("idcustomer"));
    idlawyer = Integer.parseInt(bundle.getString("idlawyer"));
    idproject = Integer.parseInt(bundle.getString("projectid"));

    accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);
    presenter.getMessage(accessToken,rvMessage,idproject);
  }

  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this).getAppComponent()
            .plus(new MessageActivityModule(this))
            .inject(this);
  }

  private void transparentStatusBar() {
    if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
      setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
    }
    if (Build.VERSION.SDK_INT >= 19) {
      getWindow().getDecorView().setSystemUiVisibility(
          View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
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

  @OnClick(R.id.btn_send)
  public void validateMessage(){
    if(!TextUtils.isEmpty(txtMessage.getText().toString())){
      pesan.body = txtMessage.getText().toString();
      pesan.receiver_id = idcustomer;
      pesan.project_id = idproject;
      pesan.date = DateFormatter.getDate(String.valueOf(System.currentTimeMillis()),"yyyy-MM-dd");

      presenter.send(accessToken,pesan);
      txtMessage.setText("");
    }
  }

  public void initRecycleView(RecyclerView rvMessage) {

    rvMessage.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
    rvMessage.addItemDecoration(
            new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    rvMessage.setLayoutManager(new LinearLayoutManager(this));
//        lsbarang.setLayoutManager(layoutManager);
    rvMessage.setNestedScrollingEnabled(false);


  }

  public void initListMessage(List<MessageModel.Data> listMessage, RecyclerView rvMessage){
    Log.e(TAG, "initListService: "+listMessage );
    adapterMessage = new AdapterMessage((ArrayList<MessageModel.Data>) listMessage,this, this,idcustomer,idlawyer);
    adapterMessage.UpdateMessage(listMessage);
    rvMessage.setAdapter(adapterMessage);
    Log.e(TAG, "initListService: "+adapterMessage );
    rvMessage.smoothScrollToPosition(rvMessage.getAdapter().getItemCount()-1);
    adapterMessage.notifyDataSetChanged();
  }
}
