package lawyerku.customer.base;

import javax.inject.Singleton;

import dagger.Component;
import lawyerku.customer.api.data.remote.CredentialModelModule;
import lawyerku.customer.ui.login.LoginActivityComponent;
import lawyerku.customer.ui.login.LoginActivityModule;
import lawyerku.customer.ui.register.RegisterActivityComponent;
import lawyerku.customer.ui.register.RegisterActivityModule;
import lawyerku.customer.ui.splash.SplashActivity;
import lawyerku.customer.ui.splash.SplashActivityComponent;
import lawyerku.customer.ui.splash.SplashActivityModule;

@Singleton
@Component(
        modules = {
                AppModule.class,
//                CredentialModelModule.class
//                FirebaseModule.class,
//                NetworkModule.class
        }
)
public interface AppComponent {

        LoginActivityComponent plus(LoginActivityModule activityModule);

        RegisterActivityComponent plus(RegisterActivityModule activityModule);

        SplashActivityComponent plus(SplashActivityModule activityModule);
}
