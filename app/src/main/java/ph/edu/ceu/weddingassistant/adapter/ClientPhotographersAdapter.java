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

public class ClientPhotographersAdapter extends RecyclerView.Adapter<ClientPhotographersAdapter.PhotographersViewHolder>{

    private Context mContext;
    List<ServiceProviderInfo> providerInfos;

    public ClientPhotographersAdapter(Context mContext, List<ServiceProviderInfo> providerInfos) {
        this.mContext = mContext;
        this.providerInfos = providerInfos;
    }

    @NonNull
    @Override
    public ClientPhotographersAdapter.PhotographersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_client_photographer_card,null);
        PhotographersViewHolder holder = new PhotographersViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotographersViewHolder holder, int position) {
        ServiceProviderInfo info = providerInfos.get(position);
        holder.text_service_name.setText(info.getService_name());
        holder.text_service_email.setText(info.getService_email());
        holder.text_cost.setText(info.getCost().toString());
        holder.text_contact.setText(info.getContact());
    }

    @Override
    public int getItemCount() {
        return providerInfos.size();
    }

    class PhotographersViewHolder extends RecyclerView.ViewHolder{
        TextView text_service_name,text_service_email,text_cost,text_contact;
        public PhotographersViewHolder(@NonNull View itemView) {
            super(itemView);
            text_service_name = (TextView) itemView.findViewById(R.id.photographer_person_name);
            text_service_email = (TextView) itemView.findViewById(R.id.photographer_person_email);
            text_cost = (TextView) itemView.findViewById(R.id.photographer_cost);
            text_contact = (TextView) itemView.findViewById(R.id.photographer_contact);
        }
    }
}
