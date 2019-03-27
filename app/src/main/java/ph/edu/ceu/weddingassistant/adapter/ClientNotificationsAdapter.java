package ph.edu.ceu.weddingassistant.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.models.UserNotification;

public class ClientNotificationsAdapter extends RecyclerView.Adapter<ClientNotificationsAdapter.ClientNotificationsViewHolder>{

    private Context mContext;
    List<UserNotification> userNotifications;

    public ClientNotificationsAdapter(Context mContext, List<UserNotification> userNotifications) {
        this.mContext = mContext;
        this.userNotifications = userNotifications;
    }

    public class ClientNotificationsViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView event_title,event_location,date,status;
        public ClientNotificationsViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            event_title = (TextView) itemView.findViewById(R.id.event_title);
            event_location = (TextView) itemView.findViewById(R.id.location);
            date = (TextView) itemView.findViewById(R.id.date);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }

    @NonNull
    @Override
    public ClientNotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.notification_cards,null);
        ClientNotificationsAdapter.ClientNotificationsViewHolder holder = new ClientNotificationsAdapter.ClientNotificationsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientNotificationsViewHolder holder, int position) {
        final UserNotification events = userNotifications.get(position);
        final Context notificationContext = mContext;
        String displayStatus;
        holder.event_title.setText(events.getEventTitle());
        holder.event_location.setText(events.getEventLocation());
        holder.date.setText(events.getEventDate());
        if(events.getStatus().equals("Pending")){
            displayStatus = "Pending";
            holder.status.setText(displayStatus);
            holder.status.setTextColor(Color.parseColor("#F57C00"));
        }
        if(events.getStatus().equals("Accepted")){
            displayStatus = "Accepted";
            holder.status.setText(displayStatus);
            holder.status.setTextColor(Color.parseColor("#4CAF50"));
        }
        if(events.getStatus().equals("Declined")){
            displayStatus = "Declined";
            holder.status.setText(displayStatus);
            holder.status.setTextColor(Color.parseColor("#D32F2F"));
        }

    }

    @Override
    public int getItemCount() {
        return userNotifications.size();
    }

}
