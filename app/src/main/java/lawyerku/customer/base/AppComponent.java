package lawyerku.customer.base;

import javax.inject.Singleton;

import dagger.Component;
import lawyerku.customer.api.data.remote.CredentialModelModule;
import lawyerku.customer.ui.detaillawyer.DetailLawyerActivityComponent;
import lawyerku.customer.ui.detaillawyer.DetailLawyerActivityModule;
import lawyerku.customer.ui.detailperkara.DetailPerkaraActivityComponent;
import lawyerku.customer.ui.detailperkara.DetailPerkaraActivityModule;
import lawyerku.customer.ui.listperkara.ListPerkaraActivityModule;
import lawyerku.customer.ui.listperkara.ListPerkaraActiviyComponent;
import lawyerku.customer.ui.login.LoginActivityComponent;
import lawyerku.customer.ui.login.LoginActivityModule;
import lawyerku.customer.ui.register.RegisterActivityComponent;
import lawyerku.customer.ui.register.RegisterActivityModule;
import lawyerku.customer.ui.searchlawyer.SearchLawyerActivityComponent;
import lawyerku.customer.ui.searchlawyer.SearchLawyerActivityModule;
import lawyerku.customer.ui.searchlawyer.search.SearchActivityComponent;
import lawyerku.customer.ui.searchlawyer.search.SearchActivityModule;
import lawyerku.customer.ui.splash.SplashActivity;
import lawyerku.customer.ui.splash.SplashActivityComponent;
import lawyerku.customer.ui.splash.SplashActivityModule;

@Singleton
@Component(
        modules = {
                AppModule.class,
//                CredentialModelModule.class
//                FirebaseModule.class,
//                NetworkModule.class
        }
)
public interface AppComponent {

        LoginActivityComponent plus(LoginActivityModule activityModule);

        RegisterActivityComponent plus(RegisterActivityModule activityModule);

        SplashActivityComponent plus(SplashActivityModule activityModule);

        SearchLawyerActivityComponent plus(SearchLawyerActivityModule activityModule);

        SearchActivityComponent plus(SearchActivityModule activityModule);

        DetailLawyerActivityComponent plus(DetailLawyerActivityModule activityModule);

        DetailPerkaraActivityComponent plus(DetailPerkaraActivityModule activityModule);

        ListPerkaraActiviyComponent plus(ListPerkaraActivityModule activityModule);
}
