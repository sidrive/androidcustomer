package lawyerku.customer.ui.purchase;

import dagger.Module;
import dagger.Provides;
import lawyerku.customer.base.annotation.ActivityScope;

@Module
public class PurchaseActivityModule {
    PurchaseActivity activity;

    public PurchaseActivityModule(PurchaseActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    PurchaseActivity providePurchaseActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    PurchasePresenter providePurchasePresenter(){
        return new PurchasePresenter(activity);
    }
}
