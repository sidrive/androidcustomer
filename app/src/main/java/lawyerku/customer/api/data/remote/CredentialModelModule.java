package lawyerku.customer.api.data.remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lawyerku.customer.api.model.CredentialModel;
import lawyerku.customer.base.BaseApplication;
import lawyerku.customer.base.annotation.UserScope;

@Module
public class CredentialModelModule {
    CredentialModel credentialModel;

    public CredentialModelModule (CredentialModel credentialModel){
        this.credentialModel = credentialModel;
    }

    @Provides
    @UserScope
    CredentialModel provideCredentialModel(){
        return credentialModel;
    }


}
