package lawyerku.customer.ui.message;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import lawyerku.customer.api.LawyerkuService;
import lawyerku.customer.api.model.MessageModel;
import lawyerku.customer.base.BasePresenter;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.support.constraint.Constraints.TAG;

public class MessagePresenter implements BasePresenter {

    MessageActivity activity;
    CompositeSubscription subscription;

    public MessagePresenter(MessageActivity activity){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void getMessage(String accessToken, RecyclerView rvMessage, int id) {

        subscription.add(LawyerkuService.Factory.create().getMessage(accessToken,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "getMessage: "+response );
                    List<MessageModel.Data> listMessage = new ArrayList<>();

                    if (response.status >= 200 && response.status < 300) {
                        for (int position = 0; position < response.data.size(); position++){
                            listMessage.add(response.data.get(position));
                        }
                        activity.initListMessage(listMessage,rvMessage);

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

    public void send(String accessToken, MessageModel.MessageToSend pesan) {
        subscription.add(LawyerkuService.Factory.create().sendMessage(accessToken,pesan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e(TAG, "getMessage: "+response );
                    if (response.status >= 200 && response.status < 300) {

                        getMessage(accessToken,activity.rvMessage,activity.idproject);

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
