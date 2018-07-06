package lawyerku.customer.ui.splash;

import dagger.Subcomponent;
import lawyerku.customer.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                SplashActivityModule.class
        }
)
public interface SplashActivityComponent {
    SplashActivity inject(SplashActivity activity);
}
