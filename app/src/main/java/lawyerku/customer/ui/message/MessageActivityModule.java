package lawyerku.customer.ui.message;

import dagger.Module;
import dagger.Provides;
import lawyerku.customer.base.annotation.ActivityScope;

@Module
public class MessageActivityModule {
    MessageActivity activity;

    public MessageActivityModule(MessageActivity activity){
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    MessageActivity provideMessageActivity(){
        return activity;
    }

    @ActivityScope
    @Provides
    MessagePresenter provideMessagePresenter(){
        return new MessagePresenter(activity);
    }
}
