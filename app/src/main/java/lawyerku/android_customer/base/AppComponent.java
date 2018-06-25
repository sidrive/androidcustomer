package lawyerku.android_customer.base;

import javax.inject.Singleton;

import dagger.Component;
import lawyerku.android_customer.ui.login.LoginActivityComponent;
import lawyerku.android_customer.ui.login.LoginActivityModule;

@Singleton
@Component(
        modules = {
                AppModule.class
//                FirebaseModule.class,
//                NetworkModule.class
        }
)
public interface AppComponent {

        LoginActivityComponent plus(LoginActivityModule activityModule);
}
