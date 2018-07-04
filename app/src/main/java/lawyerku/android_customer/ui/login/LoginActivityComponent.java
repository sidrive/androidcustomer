package lawyerku.android_customer.ui.login;

import dagger.Subcomponent;
import lawyerku.android_customer.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                LoginActivityModule.class
        }
)
public interface LoginActivityComponent {
    LoginActivityCons inject(LoginActivityCons activity);
}
