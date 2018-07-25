package lawyerku.customer.ui.listperkara;

import dagger.Subcomponent;
import lawyerku.customer.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                ListPerkaraActivityModule.class
        }
)
public interface ListPerkaraActiviyComponent {
    ListPerkaraActivity inject(ListPerkaraActivity activity);
}
