package lawyerku.customer.ui.splash;

import lawyerku.customer.api.model.CredentialModel;
import lawyerku.customer.base.BasePresenter;

public class SplashPresenter implements BasePresenter {

    SplashActivity activity;
    CredentialModel credentialModel;

    public SplashPresenter(SplashActivity activity/*, CredentialModel credentialModel*/){
        this.activity = activity;
        this.credentialModel = credentialModel;
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
