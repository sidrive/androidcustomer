package lawyerku.android_customer.base;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class
//                FirebaseModule.class,
//                NetworkModule.class
        }
)
public interface AppComponent {
}
