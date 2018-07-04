package lawyerku.android_customer.ui.login;

import android.util.Log;
import android.widget.Toast;

import lawyerku.android_customer.R;
import lawyerku.android_customer.api.LawyerkuService;
import lawyerku.android_customer.api.model.CredentialModel;
import lawyerku.android_customer.base.BaseInterface;
import lawyerku.android_customer.base.BasePresenter;
import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.android.gms.internal.zzt.TAG;

public class LoginPresenter implements BasePresenter{
    LoginActivityCons activity;
    private CompositeSubscription subscription;

    public LoginPresenter(LoginActivityCons activity){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void validateLogin(CredentialModel.Request request){

        subscription.add(LawyerkuService.Factory.create().login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "validateLogin: "+response.success.token );

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

                    } else {

                        Toast.makeText(activity, "User/Password Salah", Toast.LENGTH_SHORT).show();
//                        listener.onError(response.message);
                    }
//                    listener.hideLoading();
                    Log.e(TAG, "validateLogin: "+response.message );
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
