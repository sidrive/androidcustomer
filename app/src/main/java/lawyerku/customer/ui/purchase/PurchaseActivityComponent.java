package lawyerku.customer.ui.purchase;

import dagger.Subcomponent;
import lawyerku.customer.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                PurchaseActivityModule.class
        }
)
public interface PurchaseActivityComponent {
    PurchaseActivity inject(PurchaseActivity activity);
}
