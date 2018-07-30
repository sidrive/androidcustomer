package lawyerku.customer.ui.profilcustomer;

import dagger.Module;
import dagger.Provides;
import lawyerku.customer.base.annotation.ActivityScope;

@Module
public class DetailProfileActivityModule {
    DetailProfileActivity activity;

    public DetailProfileActivityModule(DetailProfileActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    DetailProfileActivity provideDetailProfileActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    DetailProfilePresenter provideDetailProfilePresenter(){
        return new DetailProfilePresenter(activity);
    }
}
