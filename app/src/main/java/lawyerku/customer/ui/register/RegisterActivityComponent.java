package lawyerku.customer.ui.register;

import dagger.Subcomponent;
import lawyerku.customer.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                RegisterActivityModule.class
        }
)
public interface RegisterActivityComponent {
    RegisterActivity inject(RegisterActivity activity);
}
