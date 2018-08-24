package lawyerku.customer.api.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lawyerku.customer.R;
import lawyerku.customer.api.model.LawyerModel;
import lawyerku.customer.ui.searchlawyer.search.SearchActivity;

import static com.facebook.GraphRequest.TAG;

public class AdapterLawyer extends RecyclerView.Adapter<AdapterLawyer.ViewHolder>{
    private Context mcontext;
    private List<LawyerModel.Data> mitem;
    private SearchActivity activity;

    public AdapterLawyer(ArrayList<LawyerModel.Data> item, Context context, SearchActivity activity){
        this.mcontext = context;
        this.mitem = item;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.list_item_lawyer, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LawyerModel.Data lawyer = getItem(position);
        Log.e(TAG, "onBindViewHolder: "+lawyer.name+"skill"+lawyer.jobskills.size() );

        holder.txtLawyer.setText(lawyer.name);
        if(lawyer.jobskills.size() == 0) {
            holder.txtKeahlian.setText("Keahlian : ");
        }
        if(lawyer.jobskills.size() >= 1){
            holder.txtKeahlian.setText("Keahlian : "+lawyer.jobskills.get(0).name);
        }

    }

    @Override
    public int getItemCount() {
        return mitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_lawyer)
        TextView txtLawyer;
        @BindView(R.id.tv_keahlian)
        TextView txtKeahlian;
        @BindView(R.id.tv_biaya)
        TextView txtBiaya;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.setIsRecyclable(false);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            LawyerModel.Data lawyer = getItem(this.getAdapterPosition());
            activity.createProject(lawyer);

        }
    }

    private LawyerModel.Data getData(int adptPosition){
        return mitem.get(adptPosition);
    }

    @Nullable
    public LawyerModel.Data getItem(int position) {
        return mitem.get(position);
    }

    public void UpdateLawyer(List<LawyerModel.Data> listarray) {
        mitem = listarray;
        notifyDataSetChanged();
    }
}
