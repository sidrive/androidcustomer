package lawyerku.customer.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import lawyerku.customer.base.config.DefaultConfig;

public class BaseApplication extends MultiDexApplication {

    private AppComponent appComponent;
    private DefaultConfig defaultConfig;
    private Context context;

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
}
