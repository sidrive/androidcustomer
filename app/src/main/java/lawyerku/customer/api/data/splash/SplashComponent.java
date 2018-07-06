package lawyerku.customer.api.data.splash;

import dagger.Subcomponent;
import lawyerku.customer.base.annotation.MainScope;

@MainScope
@Subcomponent(
        modules = {
                SplashModule.class
        }
)
public interface SplashComponent {
}
