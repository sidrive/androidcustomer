package lawyerku.customer.ui.login;

import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import lawyerku.customer.R;
import lawyerku.customer.api.LawyerkuService;
import lawyerku.customer.api.model.CredentialModel;
import lawyerku.customer.base.BaseInterface;
import lawyerku.customer.base.BasePresenter;
import lawyerku.customer.preference.GlobalPreference;
import lawyerku.customer.preference.PrefKey;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
;import static com.facebook.login.widget.ProfilePictureView.TAG;

public class LoginPresenter implements BasePresenter{
  LoginActivity activity;
    private CompositeSubscription subscription;

    public LoginPresenter(LoginActivity activity){
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

                    if(response.success.token != null){
                        GlobalPreference.write(PrefKey.loggedIn, true, Boolean.class);
                        GlobalPreference.write(PrefKey.accessToken, String.format(Locale.US, "Bearer %s", response.success.token), String.class);
                        activity.loginProses();
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
