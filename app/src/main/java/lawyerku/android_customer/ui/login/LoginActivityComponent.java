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
    LoginActivity inject(LoginActivity activity);
}
