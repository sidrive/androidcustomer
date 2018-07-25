package lawyerku.customer.ui.listperkara;

import dagger.Module;
import dagger.Provides;
import lawyerku.customer.base.annotation.ActivityScope;

@Module
public class ListPerkaraActivityModule {
    ListPerkaraActivity activity;

    public ListPerkaraActivityModule(ListPerkaraActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    ListPerkaraActivity provideListPerkaraActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    ListPerkaraPresenter provideListPerkaraPresenter(){
        return new ListPerkaraPresenter(activity);
    }
}
