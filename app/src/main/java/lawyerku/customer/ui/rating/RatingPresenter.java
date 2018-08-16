package lawyerku.customer.ui.rating;

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

public class RatingPresenter implements BasePresenter{
    RatingActivity activity;
    private CompositeSubscription subscription;

    public RatingPresenter(RatingActivity activity){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void getLawyer(int id) {
        String accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);

        subscription.add(LawyerkuService.Factory.create().getLawyer(accessToken,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "getLawyer: "+response.data );

                    if (response.status >= 200 && response.status < 300) {

                        activity.initLawyer(response.data.get(0));
                    } else {
//                        profileListener.onError(response.message);
                    }
//                    profileListener.hideLoading();
                }, throwable -> {
                    int errorCode = ((HttpException) throwable).response().code();
//                    if (errorCode > 400)
//                        profileListener.onError(App.getContext().getString(R.string.error_general));
//                    profileListener.hideLoading();
                }));
    }

    public void postRating(PerkaraModel.Rating rating){
        String accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);

        subscription.add(LawyerkuService.Factory.create().Rating(accessToken,rating)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "getLawyer: "+response.data );

                    if (response.status >= 200 && response.status < 300) {

                        activity.showHome();
                    } else {
//                        profileListener.onError(response.message);
                    }
//                    profileListener.hideLoading();
                }, throwable -> {
                    int errorCode = ((HttpException) throwable).response().code();
//                    if (errorCode > 400)
//                        profileListener.onError(App.getContext().getString(R.string.error_general));
//                    profileListener.hideLoading();
                }));
    }
}
