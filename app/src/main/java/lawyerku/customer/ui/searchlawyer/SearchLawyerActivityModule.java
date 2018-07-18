package lawyerku.customer.ui.searchlawyer;

import dagger.Module;
import dagger.Provides;
import lawyerku.customer.base.annotation.ActivityScope;

@Module
public class SearchLawyerActivityModule {
    SearchLawyerActivity activity;

    public SearchLawyerActivityModule(SearchLawyerActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    SearchLawyerActivity provideSearcLawyerActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    SearchLawyerPresenter provideSearchLawyerPresenter(){
        return new SearchLawyerPresenter(activity   );
    }
}
