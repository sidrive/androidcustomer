package lawyerku.customer.ui.purchase;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.List;

import lawyerku.customer.api.LawyerkuService;
import lawyerku.customer.api.model.PerkaraModel;
import lawyerku.customer.base.BasePresenter;
import lawyerku.customer.preference.GlobalPreference;
import lawyerku.customer.preference.PrefKey;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.facebook.GraphRequest.TAG;

public class PurchasePresenter implements BasePresenter {
    PurchaseActivity activity;
    private CompositeSubscription subscription;

    public PurchasePresenter(PurchaseActivity activity){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
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

                        activity.initPayment(response.data);

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

    public void savePurchase(PerkaraModel.Purchase purchase, File file) {
        String accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);
        MultipartBody.Part imageBody = null;
        if (file != null) {
            RequestBody image = RequestBody.create(MediaType.parse("image/*"), file);
            imageBody = MultipartBody.Part.createFormData("image", file.getName(), image);
        }

        subscription.add(LawyerkuService.Factory.create().purchase(accessToken,purchase.id,"New",purchase.total,imageBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "getLawyer: "+response.data );

                    if (response.status >= 200 && response.status < 300) {

//                        activity.initPayment(response.data);

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
