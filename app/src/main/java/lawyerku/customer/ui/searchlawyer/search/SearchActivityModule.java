package lawyerku.customer.ui.searchlawyer.search;

import dagger.Module;
import dagger.Provides;
import lawyerku.customer.base.annotation.ActivityScope;

@Module
public class SearchActivityModule {
    SearchActivity activity;

    public SearchActivityModule(SearchActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    SearchActivity provideSearchActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    SearchPresenter provideSearchPresenter(){
        return new SearchPresenter(activity);
    }
}
