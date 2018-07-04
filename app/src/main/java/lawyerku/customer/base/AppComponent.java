package lawyerku.customer.base;

import javax.inject.Singleton;

import dagger.Component;
import lawyerku.customer.ui.login.LoginActivityComponent;
import lawyerku.customer.ui.login.LoginActivityModule;
import lawyerku.customer.ui.register.RegisterActivityComponent;
import lawyerku.customer.ui.register.RegisterActivityModule;

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

        RegisterActivityComponent plus(RegisterActivityModule activityModule);
}
