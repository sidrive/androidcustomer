package lawyerku.android_customer.ui.login;

import dagger.Module;
import dagger.Provides;
import lawyerku.android_customer.base.annotation.ActivityScope;

@Module
public class LoginActivityModule {
    LoginActivityCons activity;

    public LoginActivityModule(LoginActivityCons activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    LoginActivityCons provideLoginActivity(){return activity;}

    @ActivityScope
    @Provides
    LoginPresenter provideLoginPresenter(){
        return new LoginPresenter(activity);
    }

}
