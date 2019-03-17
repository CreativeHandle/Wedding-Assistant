package ph.edu.ceu.weddingassistant.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.fragments.ClientCateringServiceInfoFragment;
import ph.edu.ceu.weddingassistant.fragments.ClientPhotographersInfoFragment;
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
        final ServiceProviderInfo info = providerInfos.get(position);
        final Context photographerContext = mContext;
        holder.text_service_name.setText(info.getService_name());
        holder.text_service_email.setText(info.getService_email());
        holder.text_cost.setText(info.getCost().toString());
        holder.text_contact.setText(info.getContact());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                ClientPhotographersInfoFragment cpServiceInfo= new ClientPhotographersInfoFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("photographer_service_name",info.getService_name());
                                bundle.putString("photographer_service_email",info.getService_email());
                                bundle.putString("photographer_service_cost",info.getCost().toString());
                                bundle.putString("photographer_service_contact",info.getContact());
                                bundle.putString("photographer_service_category",info.getCategory());
                                bundle.putString("photographer_service_permit",info.getPermit());
                                cpServiceInfo.setArguments(bundle);
                                FragmentTransaction ft = ((AppCompatActivity)photographerContext).getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame_client, cpServiceInfo);
                                ft.commit();
                            case DialogInterface.BUTTON_NEGATIVE:
                                return;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(photographerContext);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return providerInfos.size();
    }

    class PhotographersViewHolder extends RecyclerView.ViewHolder{
        public View view;
        TextView text_service_name,text_service_email,text_cost,text_contact;
        public PhotographersViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            text_service_name = (TextView) itemView.findViewById(R.id.photographer_person_name);
            text_service_email = (TextView) itemView.findViewById(R.id.photographer_person_email);
            text_cost = (TextView) itemView.findViewById(R.id.photographer_cost);
            text_contact = (TextView) itemView.findViewById(R.id.photographer_contact);
        }
    }
}
