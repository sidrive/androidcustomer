package lawyerku.customer.ui.detaillawyer;

import dagger.Subcomponent;
import lawyerku.customer.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                DetailLawyerActivityModule.class
        }
)
public interface DetailLawyerActivityComponent {
    DetailLawyerActivity inject(DetailLawyerActivity activity);
}
