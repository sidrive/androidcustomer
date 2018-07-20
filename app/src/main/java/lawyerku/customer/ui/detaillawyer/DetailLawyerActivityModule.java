package lawyerku.customer.ui.detaillawyer;

import dagger.Module;
import dagger.Provides;
import lawyerku.customer.base.annotation.ActivityScope;

@Module
public class DetailLawyerActivityModule {
    DetailLawyerActivity activity;

    public DetailLawyerActivityModule(DetailLawyerActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    DetailLawyerActivity provideDetailLawyerActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    DetailLawyerPresenter provideDetailLawyerPresenter(){
        return new DetailLawyerPresenter(activity);
    }
}
