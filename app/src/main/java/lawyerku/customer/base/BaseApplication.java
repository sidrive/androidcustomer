package lawyerku.customer.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Geocoder;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import lawyerku.customer.api.data.remote.CredentialModelComponent;
import lawyerku.customer.api.data.remote.CredentialModelModule;
import lawyerku.customer.api.data.splash.SplashComponent;
import lawyerku.customer.api.data.splash.SplashModule;
import lawyerku.customer.api.model.CredentialModel;
import lawyerku.customer.base.config.DefaultConfig;
import lawyerku.customer.ui.splash.SplashActivity;

public class BaseApplication extends MultiDexApplication {

    private AppComponent appComponent;
    private DefaultConfig defaultConfig;
    private Context context;
    private CredentialModelComponent credentialModelComponent;
    private SplashComponent splashComponent;

    @SuppressLint("StaticFieldLeak")
    private static Context contextto;
    private static Geocoder geocoder;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        defaultConfig = new DefaultConfig(base);
        context =base;
        MultiDex.install(getBaseContext());
    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
        contextto = getApplicationContext();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
//                .firebaseModule(new FirebaseModule())
//                .networkModule(new NetworkModule(defaultConfig.getApiUrl()))
                .build();
//        FirebaseApp.initializeApp(getBaseContext());
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public CredentialModelComponent getCredentialModelComponent(){
        return credentialModelComponent;
    }

    public SplashComponent createSplashComponent(SplashActivity activity){
        splashComponent = credentialModelComponent.plus(new SplashModule(activity));
        return splashComponent;
    }

    public static Context getContext() {
        return contextto;
    }
}
