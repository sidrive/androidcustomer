package lawyerku.customer.api.data.remote;

import dagger.Subcomponent;
import lawyerku.customer.api.data.splash.SplashComponent;
import lawyerku.customer.api.data.splash.SplashModule;
import lawyerku.customer.base.annotation.UserScope;
import lawyerku.customer.ui.splash.SplashActivityComponent;
import lawyerku.customer.ui.splash.SplashActivityModule;

@UserScope
@Subcomponent(
        modules = {
                CredentialModelModule.class
        }
)

public interface CredentialModelComponent {
    SplashActivityComponent plus(SplashActivityModule activityModule);

    SplashComponent plus(SplashModule splashModule);
}
