package lawyerku.customer.ui.login;

import dagger.Subcomponent;
import lawyerku.customer.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                LoginActivityModule.class
        }
)
public interface LoginActivityComponent {
    LoginActivity inject(LoginActivity activity);
}
