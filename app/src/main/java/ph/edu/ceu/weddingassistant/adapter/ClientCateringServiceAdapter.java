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
        final ServiceProviderInfo info = providerInfos.get(position);
        final Context cateringContext = mContext;
        holder.text_service_name.setText(info.getService_name());
        holder.text_service_email.setText(info.getService_email());
        holder.text_cost.setText(info.getCost().toString());
        holder.text_contact.setText(info.getContact());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                ClientCateringServiceInfoFragment ccServiceInfo= new ClientCateringServiceInfoFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("catering_service_name",info.getService_name());
                                bundle.putString("catering_service_email",info.getService_email());
                                bundle.putString("catering_service_cost",info.getCost().toString());
                                bundle.putString("catering_service_contact",info.getContact());
                                bundle.putString("catering_service_category",info.getCategory());
                                bundle.putString("catering_service_permit",info.getPermit());
                                ccServiceInfo.setArguments(bundle);
                                FragmentTransaction ft = ((AppCompatActivity)cateringContext).getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame_client, ccServiceInfo);
                                ft.commit();
                            case DialogInterface.BUTTON_NEGATIVE:
                                return;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(cateringContext);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
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
