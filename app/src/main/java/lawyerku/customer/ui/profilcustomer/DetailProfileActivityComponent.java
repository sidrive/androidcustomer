package lawyerku.customer.ui.profilcustomer;

import dagger.Subcomponent;
import lawyerku.customer.base.annotation.ActivityScope;
import lawyerku.customer.ui.detailperkara.DetailPerkaraActivity;

@ActivityScope
@Subcomponent(
        modules = {
                DetailProfileActivityModule.class
        }
)
public interface DetailProfileActivityComponent {
    DetailProfileActivity inject(DetailProfileActivity activity);
}
