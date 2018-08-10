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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lawyerku.customer.R;
import lawyerku.customer.api.model.MessageModel;
import lawyerku.customer.ui.message.MessageActivity;

import static android.support.constraint.Constraints.TAG;

public class AdapterMessage extends RecyclerView.Adapter<AdapterMessage.ViewHolder> {
    private Context mcontext;
    private List<MessageModel.Data> mitem;
    private MessageActivity activity;
    private int idcustomer;
    private int idlawyer;

    public AdapterMessage(ArrayList<MessageModel.Data> item, Context context, MessageActivity activity, int idcustomer, int idlawyer ){
        this.mcontext = context;
        this.mitem = item;
        this.activity = activity;
        this.idcustomer = idcustomer;
        this.idlawyer = idlawyer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        View view = inflater.inflate(R.layout.list_item_message, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageModel.Data pesan = getItem(position);
        Log.e(TAG, "onBindViewHolder: "+idcustomer);
        if(pesan.sender_id == idlawyer){
            holder.txtChat.setText(pesan.body);
            holder.txtDateReceiver.setText(pesan.date);
            holder.txtChat2.setVisibility(View.GONE);
            holder.txtDateSender.setVisibility(View.GONE);
        }
        if(pesan.sender_id == idcustomer ){
            holder.txtChat2.setText(pesan.body);
            holder.txtChat.setVisibility(View.GONE);
            holder.txtDateSender.setText(pesan.date);
            holder.txtDateReceiver.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return mitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_chat)
        TextView txtChat;
        @BindView(R.id.tv_chat2)
        TextView txtChat2;
        @BindView(R.id.tv_date_sender)
        TextView txtDateSender;
        @BindView(R.id.tv_date_receiver)
        TextView txtDateReceiver;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.setIsRecyclable(false);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            MessageModel.Data perkara = getItem(this.getAdapterPosition());
//            activity.detailLawyer(perkara);

        }
    }

    private MessageModel.Data getData(int adptPosition){
        return mitem.get(adptPosition);
    }

    @Nullable
    public MessageModel.Data getItem(int position) {
        return mitem.get(position);
    }

    public void UpdateMessage(List<MessageModel.Data> listarray) {
        mitem = listarray;
        notifyDataSetChanged();
    }
}
