package lawyerku.customer.ui.detailperkara;

import dagger.Subcomponent;
import lawyerku.customer.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                DetailPerkaraActivityModule.class
        }
)
public interface DetailPerkaraActivityComponent {
    DetailPerkaraActivity inject(DetailPerkaraActivity activity);
}
