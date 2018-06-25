package lawyerku.android_customer.ui.login;

import android.util.Log;

import lawyerku.android_customer.base.BasePresenter;

import static com.google.android.gms.internal.zzt.TAG;

public class LoginPresenter implements BasePresenter{
    LoginActivity activity;

    public LoginPresenter(LoginActivity activity){
        this.activity = activity;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void show(){
        Log.e(TAG, "show: this is presenter" );
    }
}
