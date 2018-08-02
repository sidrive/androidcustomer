package lawyerku.customer.ui.profilcustomer;

import android.util.Log;

import lawyerku.customer.api.LawyerkuService;
import lawyerku.customer.api.model.LawyerModel;
import lawyerku.customer.base.BasePresenter;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.facebook.GraphRequest.TAG;

public class DetailProfilePresenter implements BasePresenter {
    DetailProfileActivity activity;
    private CompositeSubscription subscription;

    public DetailProfilePresenter(DetailProfileActivity activity){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void getAccount(String accessToken) {

        subscription.add(LawyerkuService.Factory.create().getProfileCustomer(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "getAccount: "+response );
                    if (response.status >= 200 && response.status < 300) {

                        activity.showProfile(response.data);

                    } else {
//                        profileListener.onError(response.message);
                    }
                }, throwable -> {
                    int errorCode = ((HttpException) throwable).response().code();
//                    if (errorCode > 400)
//                        profileListener.onError(App.getContext().getString(R.string.error_general));
//                    profileListener.hideLoading();
                }));
    }

    public void saveUpdate(String accessToken, LawyerModel.DataUpdata customer) {
        subscription.add(LawyerkuService.Factory.create().updateProfile(accessToken,customer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "getAccount: "+response );
                    if (response.status >= 200 && response.status < 300) {

                        activity.showMainActivity();

                    } else {
//                        profileListener.onError(response.message);
                    }
                }, throwable -> {
                    int errorCode = ((HttpException) throwable).response().code();
//                    if (errorCode > 400)
//                        profileListener.onError(App.getContext().getString(R.string.error_general));
//                    profileListener.hideLoading();
                }));
    }


}
