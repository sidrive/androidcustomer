package lawyerku.customer.ui.detaillawyer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lawyerku.customer.api.LawyerkuService;
import lawyerku.customer.api.model.LawyerModel;
import lawyerku.customer.base.BasePresenter;
import lawyerku.customer.preference.GlobalPreference;
import lawyerku.customer.preference.PrefKey;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.facebook.GraphRequest.TAG;

public class DetailLawyerPresenter implements BasePresenter {
    DetailLawyerActivity activity;
    private CompositeSubscription subscription;

    public DetailLawyerPresenter(DetailLawyerActivity activity){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void getLawyer(int idlawyer){
        String accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);

        subscription.add(LawyerkuService.Factory.create().getLawyer(accessToken,idlawyer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "getLawyer: "+response.data );
                    List<LawyerModel.Data> listLawyer = new ArrayList<>();
                    if (response.status >= 200 && response.status < 300) {
//                        for (int position = 0; position < response.data.size(); position++){
//                            listLawyer.add(response.data.get(position));
//                        }
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
}
