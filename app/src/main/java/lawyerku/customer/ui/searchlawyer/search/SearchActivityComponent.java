package lawyerku.customer.ui.searchlawyer.search;

import dagger.Subcomponent;
import lawyerku.customer.base.annotation.ActivityScope;

@ActivityScope
@Subcomponent(
        modules = {
                SearchActivityModule.class
        }
)
public interface SearchActivityComponent {
    SearchActivity inject(SearchActivity activity);
}
