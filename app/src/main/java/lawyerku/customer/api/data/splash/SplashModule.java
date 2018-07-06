package lawyerku.customer.api.data.splash;

import dagger.Module;
import dagger.Provides;
import lawyerku.customer.base.annotation.MainScope;
import lawyerku.customer.ui.splash.SplashActivity;

@Module
public class SplashModule {
    SplashActivity activity;

    public SplashModule(SplashActivity activity){
        this.activity = activity;
    }

    @Provides
    @MainScope
    SplashActivity provideSplashActivity(){
        return activity;
    }
}
