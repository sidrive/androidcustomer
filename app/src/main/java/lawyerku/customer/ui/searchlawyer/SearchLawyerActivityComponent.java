package lawyerku.customer.ui.searchlawyer;

import dagger.Subcomponent;
import lawyerku.customer.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                SearchLawyerActivityModule.class
        }
)
public interface SearchLawyerActivityComponent {
    SearchLawyerActivity inject(SearchLawyerActivity activity);
}
