package lawyerku.customer.ui.register;

import dagger.Module;
import dagger.Provides;
import lawyerku.customer.api.model.CredentialModel;
import lawyerku.customer.base.annotation.ActivityScope;

@Module
public class RegisterActivityModule {
    RegisterActivity activity;

    public RegisterActivityModule(RegisterActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    RegisterActivity provideRegisterActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    RegisterPresenter provideRegisterPresenter(/*CredentialModel credentialModel*/){
        return new RegisterPresenter(activity/*,credentialModel*/);
    }

}
