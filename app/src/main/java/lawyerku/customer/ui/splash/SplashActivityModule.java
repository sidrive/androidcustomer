package lawyerku.customer.ui.splash;

import dagger.Module;
import dagger.Provides;
import lawyerku.customer.api.model.CredentialModel;
import lawyerku.customer.base.annotation.ActivityScope;

@Module
public class SplashActivityModule {
    SplashActivity activity;

    public SplashActivityModule(SplashActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    SplashActivity providesSplashActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    SplashPresenter provideSplashPresenter(/*CredentialModel credentialModel*/){
        return new SplashPresenter(activity/*, credentialModel*/);
    }
}
