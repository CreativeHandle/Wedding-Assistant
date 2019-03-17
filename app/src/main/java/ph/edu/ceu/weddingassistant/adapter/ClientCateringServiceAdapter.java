package ph.edu.ceu.weddingassistant.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.models.ServiceProviderInfo;

public class ClientCateringServiceAdapter extends RecyclerView.Adapter<ClientCateringServiceAdapter.CateringViewHolder> {
    private Context mContext;
    List<ServiceProviderInfo> providerInfos;

    public ClientCateringServiceAdapter(Context mContext, List<ServiceProviderInfo> providerInfos) {
        this.mContext = mContext;
        this.providerInfos = providerInfos;
    }

    @NonNull
    @Override
    public ClientCateringServiceAdapter.CateringViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_client_catering_card,null);
        CateringViewHolder holder = new CateringViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CateringViewHolder holder, int position) {
        ServiceProviderInfo info = providerInfos.get(position);
        holder.text_service_name.setText(info.getService_name());
        holder.text_service_email.setText(info.getService_email());
        holder.text_cost.setText(info.getCost().toString());
        holder.text_contact.setText(info.getContact());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //
            }
        });
    }

    @Override
    public int getItemCount() {
        return providerInfos.size();
    }

    public class CateringViewHolder extends RecyclerView.ViewHolder{
        public View view;
        TextView text_service_name,text_service_email,text_cost,text_contact;
        public CateringViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            text_service_name = (TextView) itemView.findViewById(R.id.catering_person_name);
            text_service_email = (TextView) itemView.findViewById(R.id.catering_person_email);
            text_cost = (TextView) itemView.findViewById(R.id.catering_cost);
            text_contact = (TextView) itemView.findViewById(R.id.catering_contact);
        }
    }
}
