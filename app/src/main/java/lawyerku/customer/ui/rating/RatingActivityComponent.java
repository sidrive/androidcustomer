package lawyerku.customer.ui.rating;

import dagger.Subcomponent;
import lawyerku.customer.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                RatingActivityModule.class
        }
)
public interface RatingActivityComponent {
    RatingActivity inject(RatingActivity activity);
}
