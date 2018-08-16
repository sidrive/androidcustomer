package lawyerku.customer.ui.detailperkara;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lawyerku.customer.api.LawyerkuService;
import lawyerku.customer.api.model.LawyerModel;
import lawyerku.customer.api.model.PerkaraModel;
import lawyerku.customer.base.BasePresenter;
import lawyerku.customer.preference.GlobalPreference;
import lawyerku.customer.preference.PrefKey;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.facebook.GraphRequest.TAG;

public class DetailPerkaraPresenter implements BasePresenter {
    DetailPerkaraActivity activity;
    private CompositeSubscription subscription;
    private PerkaraModel.Status perkara;

    public DetailPerkaraPresenter(DetailPerkaraActivity activity){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
        this.perkara = new PerkaraModel.Status();
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void getProject(int idproject){
        String accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);

        subscription.add(LawyerkuService.Factory.create().getProject(accessToken,String.valueOf(idproject))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "getLawyer: "+response.data );

                    if (response.status >= 200 && response.status < 300) {

                        activity.initProject(response.data);

                    } else {
//                        profileListener.onError(response.message);
                    }
//                    profileListener.hideLoading();
                }, throwable -> {
                    int errorCode = ((HttpException) throwable).response().code();
                    Log.e(TAG, "getProject: "+throwable );
//                    if (errorCode > 400)
//                        profileListener.onError(App.getContext().getString(R.string.error_general));
//                    profileListener.hideLoading();
                }));
    }

    public void closeCase(int id){
        String accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);
        perkara.id = id;
        perkara.status = "finished";
        subscription.add(LawyerkuService.Factory.create().setStatus(accessToken,perkara)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "closeCase: "+response.data );

                    if (response.status >= 200 && response.status < 300) {

//                        activity.initProject(response.data);

                    } else {
//                        profileListener.onError(response.message);
                    }
//                    profileListener.hideLoading();
                }, throwable -> {
                    int errorCode = ((HttpException) throwable).response().code();
                    Log.e(TAG, "getProject: "+throwable );
//                    if (errorCode > 400)
//                        profileListener.onError(App.getContext().getString(R.string.error_general));
//                    profileListener.hideLoading();
                }));
    }
}
