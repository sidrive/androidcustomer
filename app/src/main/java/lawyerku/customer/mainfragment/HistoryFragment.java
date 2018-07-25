package lawyerku.customer.mainfragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import lawyerku.customer.R;
import lawyerku.customer.api.LawyerkuService;
import lawyerku.customer.api.adapter.AdapterPerkara;
import lawyerku.customer.api.model.PerkaraModel;
import lawyerku.customer.preference.GlobalPreference;
import lawyerku.customer.preference.PrefKey;
import lawyerku.customer.ui.detailperkara.DetailPerkaraActivity;
import lawyerku.customer.ui.listperkara.ListPerkaraActivity;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AdapterPerkara adapterPerkara;
    private CompositeSubscription subscription;

    private OnFragmentInteractionListener mListener;


    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        this.subscription = new CompositeSubscription();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        RecyclerView lsperkara = (RecyclerView) view.findViewById(R.id.lsperkara);

        initRecycleView(lsperkara);
        getPerkara(lsperkara);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void initRecycleView(RecyclerView lsperkara) {

        lsperkara.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        lsperkara.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        lsperkara.setLayoutManager(new LinearLayoutManager(getActivity()));
//        lsbarang.setLayoutManager(layoutManager);
        lsperkara.setNestedScrollingEnabled(false);

    }

    private void getPerkara(RecyclerView lsperkara) {
        String accessToken = GlobalPreference.read(PrefKey.accessToken, String.class);

        subscription.add(LawyerkuService.Factory.create().getListProject(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    List<PerkaraModel.Response.Data> listPerkara = new ArrayList<>();

                    if (response.status >= 200 && response.status < 300) {
                        for (int position = 0; position < response.data.size(); position++){
                            listPerkara.add(response.data.get(position));
                        }
                        initListPerkara(listPerkara,lsperkara);
                        Log.e(TAG, "searchLawyer: "+listPerkara );
                    } else {
//                        createProjectListener.onError(response.message);
                    }
                }, throwable -> {
                    int errorCode = ((HttpException) throwable).response().code();
                    if (errorCode > 400)
                        Log.e(TAG, "getPerkara: "+throwable );
//                        createProjectListener.onError(App.getContext().getString(R.string.error_general));
//                    createProjectListener.hideLoading();
                }));
    }

    public void initListPerkara(List<PerkaraModel.Response.Data> listPerkara, RecyclerView lsperkara){
        Log.e(TAG, "initListService: "+listPerkara );
        adapterPerkara = new AdapterPerkara((ArrayList<PerkaraModel.Response.Data>) listPerkara,getContext(), (ListPerkaraActivity) getActivity());
        adapterPerkara.UpdatePerkara(listPerkara);
        lsperkara.setAdapter(adapterPerkara);
        Log.e(TAG, "initListService: "+adapterPerkara );
    }
}
