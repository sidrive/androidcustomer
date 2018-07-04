package lawyerku.android_customer.ui.register;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import lawyerku.android_customer.api.LawyerkuService;
import lawyerku.android_customer.api.model.CredentialModel;
import lawyerku.android_customer.base.BasePresenter;
import lawyerku.android_customer.ui.login.LoginActivity;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.android.gms.internal.zzt.TAG;

public class RegisterPresenter implements BasePresenter {

    RegisterActivity activity;
//    CredentialModel credentialModel;
    private CompositeSubscription subscription;

    public RegisterPresenter(RegisterActivity activity/*,CredentialModel credentialModel*/){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
//        this.credentialModel = credentialModel;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void login(){
        activity.logogin();
    }

    public void register(CredentialModel.Request request, String type) {
//        listener.showLoading();

//        if (!SysUtils.isOnline(App.getContext())) {
//            listener.onError(App.getContext().getString(R.string.error_lost_connection));
//            return;
//        }
        subscription.add(LawyerkuService.Factory.create().register(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
//                    listener.hideLoading();
                    Log.e(TAG, "register: "+response );
                    if(response.success != null){
                        login();
                    }
                    else {
                        Log.e(TAG, "register: "+response.error );
                    }



                }, throwable -> {
                    HttpException error = (HttpException) throwable;
                    try {
                        String errorBody = error.response().errorBody().string();
                        try {
                            JSONObject jsonObject = new JSONObject(errorBody);
                            String errorMessage = jsonObject.getString("error");
                            Toast.makeText(activity,errorMessage,Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    listener.hideLoading();


                }));
    }
}
