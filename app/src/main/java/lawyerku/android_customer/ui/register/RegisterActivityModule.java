package lawyerku.android_customer.ui.register;

import dagger.Module;
import dagger.Provides;
import lawyerku.android_customer.api.model.CredentialModel;
import lawyerku.android_customer.base.annotation.ActivityScope;

@Module
public class RegisterActivityModule {
    RegisterActivityCons activity;

    public RegisterActivityModule(RegisterActivityCons activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    RegisterActivityCons provideRegisterActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    RegisterPresenter provideRegisterPresenter(/*CredentialModel credentialModel*/){
        return new RegisterPresenter(activity/*,credentialModel*/);
    }

}
