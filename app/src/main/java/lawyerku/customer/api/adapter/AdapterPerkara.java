package lawyerku.customer.api.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lawyerku.customer.R;
import lawyerku.customer.api.model.PerkaraModel;
import lawyerku.customer.ui.listperkara.ListPerkaraActivity;

import static android.support.constraint.Constraints.TAG;

public class AdapterPerkara extends RecyclerView.Adapter<AdapterPerkara.ViewHolder> {

    private Context mcontext;
    private List<PerkaraModel.Response.Data> mitem;
    private ListPerkaraActivity activity;

    public AdapterPerkara(ArrayList<PerkaraModel.Response.Data> item, Context context, ListPerkaraActivity activity){
        this.mcontext = context;
        this.mitem = item;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.list_item_perkara_cons, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PerkaraModel.Response.Data perkara = getItem(position);
        Log.e(TAG, "onBindViewHolder: "+perkara );

        holder.txtCustomer.setText(perkara.customer.name);
        holder.txtBidangHukum.setText("Bidang Hukum :"+perkara.jobskill.name);
        holder.txtMulai.setText("Mulai : "+perkara.start_date);
        holder.txtSelesai.setText("Selesai : "+perkara.end_date);
        holder.txtLawyer.setText(perkara.lawyer.lawyername);
        holder.txtLevel.setText("Level : "+perkara.lawyer.level);
        holder.txtRating.setText("Rating : "+String.valueOf(perkara.lawyer.rateMax));
        holder.txtKeahlian.setText("Keahlian : "+perkara.jobskill.name);
        holder.txtStatus.setText("Status Perkara : "+perkara.last_status.status);


    }

    @Override
    public int getItemCount() {
        return mitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_customer)
        TextView txtCustomer;
        @BindView(R.id.tv_bidang)
        TextView txtBidangHukum;
        @BindView(R.id.tv_tgl_mulai)
        TextView txtMulai;
        @BindView(R.id.tv_tgl_selesai)
        TextView txtSelesai;
        @BindView(R.id.tv_lawyer)
        TextView txtLawyer;
        @BindView(R.id.tv_keahlian)
        TextView txtKeahlian;
        @BindView(R.id.tv_level)
        TextView txtLevel;
        @BindView(R.id.tv_rating)
        TextView txtRating;
        @BindView(R.id.tv_status)
        TextView txtStatus;
        @BindView(R.id.btn_message)
        ImageButton btnMessage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.setIsRecyclable(false);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            PerkaraModel.Response.Data perkara = getItem(this.getAdapterPosition());
            activity.detailLawyer(perkara);

        }
    }

    private PerkaraModel.Response.Data getData(int adptPosition){
        return mitem.get(adptPosition);
    }

    @Nullable
    public PerkaraModel.Response.Data getItem(int position) {
        return mitem.get(position);
    }

    public void UpdatePerkara(List<PerkaraModel.Response.Data> listarray) {
        mitem = listarray;
        notifyDataSetChanged();
    }

}
