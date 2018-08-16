package lawyerku.customer.ui.rating;

import dagger.Module;
import dagger.Provides;
import lawyerku.customer.base.annotation.ActivityScope;
@Module
public class RatingActivityModule {
    RatingActivity activity;

    public RatingActivityModule(RatingActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    RatingActivity provideRatingActivity(){return activity;}

    @ActivityScope
    @Provides
    RatingPresenter provideRatingPresenter(){
        return new RatingPresenter(activity);
    }
}
