package ph.edu.ceu.weddingassistant.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ph.edu.ceu.weddingassistant.R;
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

    }

    @Override
    public int getItemCount() {
        return providerInfos.size();
    }
}
