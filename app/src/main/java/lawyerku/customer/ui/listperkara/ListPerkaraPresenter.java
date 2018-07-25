package lawyerku.customer.ui.listperkara;

import lawyerku.customer.base.BasePresenter;
import rx.subscriptions.CompositeSubscription;

public class ListPerkaraPresenter implements BasePresenter {
    ListPerkaraActivity activity;
    private CompositeSubscription subscription;

    public ListPerkaraPresenter(ListPerkaraActivity activity){
        this.activity = activity;
        this.subscription = new CompositeSubscription();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
