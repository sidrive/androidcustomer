package lawyerku.customer.ui.register;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import lawyerku.customer.api.LawyerkuService;
import lawyerku.customer.api.model.CredentialModel;
import lawyerku.customer.base.BasePresenter;
import lawyerku.customer.ui.login.LoginActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
;import static com.facebook.AccessTokenManager.TAG;

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
        Toast.makeText(activity, "Registrasi Berhasil, Silahkan Login menggunakan Akun Anda", Toast.LENGTH_SHORT).show();
        activity.logogin();
    }

    public void register(CredentialModel.Request request, Boolean tokenFacebook) {
//        listener.showLoading();
        Log.e(TAG, "register: "+request );

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
                    if(response.status <= 200 && !tokenFacebook){
                        login();
                    }
                    if(response.status <= 200 && tokenFacebook){
                        loginFB(request);
                        Log.e(TAG, "register: "+tokenFacebook );
                    }
                    if(response.error != null){
                        Toast.makeText(activity, ""+response.error, Toast.LENGTH_SHORT).show();
                        activity.showLoading(false);
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

    private void loginFB(CredentialModel.Request request) {
        subscription.add(LawyerkuService.Factory.create().login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(ProfilePictureView.TAG, "validateLogin: "+response.success.token );

                    if(response.success.token != null){
                        activity.logogin();
                    }

                    if (response.status >= 200 && response.status < 300) {
//                        GlobalPreferences.write(PrefKeys.loggedIn, true, Boolean.class);
//                        GlobalPreferences.write(PrefKeys.accessToken, String.format(Locale.US, "Bearer %s", response.accessToken), String.class);
//                        GlobalPreferences.write(PrefKeys.userType, String.format(Locale.US, response.userType, response.accessToken), String.class);

//                        if (response.userType.equals(UserType.userTypeWorkman))
//                            listener.onLoginWorkman();
//                            /* else listener.onLoginWorkman();*/else{
//                            //listener.onError(App.getContext().getString(R.string.error_unauthenticated));
//                            listener.onError(response.message);
//                        }
                        String token = response.accessToken;
                        subscription.add(LawyerkuService.Factory.create().getProfile(token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe());
//                        activity.loginProses();

                    }
//                    listener.hideLoading();
                    Log.e(ProfilePictureView.TAG, "validateLogin: "+response.error );
                }, throwable -> {
//                    String msg = ErrorUtils.getError(throwable);
                    Log.e("loginNow", "CredentialPresenter : Error bro");
//                    int errorCode = ((HttpException) throwable).response().code();
//                    if (errorCode > 400)
//                        listener.onError(App.getContext().getString(R.string.error_general));
//                    listener.hideLoading();
                }));
    }
}
