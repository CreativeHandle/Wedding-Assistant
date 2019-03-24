package ph.edu.ceu.weddingassistant.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.fragments.ClientServiceProviderInfoFragment;
import ph.edu.ceu.weddingassistant.models.ServiceProviderInfo;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.SortViewHolder> {
    private Context mContext;
    List<ServiceProviderInfo> providerInfos;


    public SortAdapter(Context mContext, List<ServiceProviderInfo> providerInfos) {
        this.mContext = mContext;
        this.providerInfos = providerInfos;
    }

    public class SortViewHolder extends RecyclerView.ViewHolder{
        public View view;
        TextView name,category,cost;
        ImageView image;
        public SortViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            name = (TextView) itemView.findViewById(R.id.title);
            category = (TextView) itemView.findViewById(R.id.category);
            cost = (TextView) itemView.findViewById(R.id.cost);
            image = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }

    @NonNull
    @Override
    public SortAdapter.SortViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recycler_cards,null);
        SortAdapter.SortViewHolder holder = new SortAdapter.SortViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SortAdapter.SortViewHolder holder, int position) {
        final ServiceProviderInfo info = providerInfos.get(position);
        final Context sortContext = mContext;
        String cost = "P "+info.getCost().toString();
        holder.name.setText(info.getService_name());
        holder.category.setText(info.getCategory());
        holder.cost.setText(cost);
        Glide.with(mContext).load(info.getThumbnail()).into(holder.image);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                ClientServiceProviderInfoFragment cpServiceInfo= new ClientServiceProviderInfoFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("service_uid",info.getUid());
                                bundle.putString("service_name",info.getService_name());
                                bundle.putString("service_email",info.getService_email());
                                bundle.putString("service_cost",info.getCost().toString());
                                bundle.putString("service_contact",info.getContact());
                                bundle.putString("service_category",info.getCategory());
                                bundle.putString("service_permit",info.getPermit());
                                bundle.putInt("service_image",info.getThumbnail());
                                cpServiceInfo.setArguments(bundle);
                                FragmentTransaction ft = ((AppCompatActivity)sortContext).getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.content_frame_client_and_event_coordinator, cpServiceInfo);
                                ft.commit();
                            case DialogInterface.BUTTON_NEGATIVE:
                                return;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(sortContext);
                builder.setMessage("Do you want to hire "+ info.getService_name() +"?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return providerInfos.size();
    }
}
