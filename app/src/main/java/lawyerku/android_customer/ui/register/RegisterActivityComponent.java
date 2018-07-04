package lawyerku.android_customer.ui.register;

import dagger.Subcomponent;
import lawyerku.android_customer.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                RegisterActivityModule.class
        }
)
public interface RegisterActivityComponent {
    RegisterActivity inject(RegisterActivity activity);
}
