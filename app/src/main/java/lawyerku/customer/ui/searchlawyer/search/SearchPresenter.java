package lawyerku.customer.ui.searchlawyer.search;

import android.util.Log;

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

public class SearchPresenter implements BasePresenter {
    SearchActivity activity;
    private CompositeSubscription subscription;

    public SearchPresenter(SearchActivity activity){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void searchLawyer(LawyerModel.Request requestBody){
        String accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);

        String language = "2";
        String skill = "1";
        String latitude = "-59.288834";
        String longitude = "-167.767563";
        subscription.add(LawyerkuService.Factory.create().searchLawyer(accessToken, language,skill,latitude,longitude)
//        subscription.add(LawyerkuService.Factory.create().searchLawyer(accessToken, String.valueOf(requestBody.language),
//                String.valueOf(requestBody.skill),String.valueOf(requestBody.latitude),String.valueOf(requestBody.longitude))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    List<LawyerModel.Data> listLawyer = new ArrayList<>();
                    if (response.status >= 200 && response.status < 300) {
                        for (int position = 0; position < response.data.size(); position++){
                            listLawyer.add(response.data.get(position));
                        }
                        activity.initListLawyer(listLawyer);
                        Log.e(TAG, "searchLawyer: "+listLawyer );
                    } else {
//                        createProjectListener.onError(response.message);
                    }
                }, throwable -> {
                    int errorCode = ((HttpException) throwable).response().code();
                    if (errorCode > 400)
                        Log.e(TAG, "searchLawyer: null" );
//                        createProjectListener.onError(App.getContext().getString(R.string.error_general));
//                    createProjectListener.hideLoading();
                }));
    }
}
