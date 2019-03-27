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

public class NotificationsAdapter extends  RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>{

    private Context mContext;
    List<UserNotification> userNotifications;

    public NotificationsAdapter(Context mContext, List<UserNotification> userNotifications) {
        this.mContext = mContext;
        this.userNotifications = userNotifications;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView event_title,event_location,date,status;
        public NotificationViewHolder(@NonNull View itemView) {
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
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.notification_cards,null);
        NotificationsAdapter.NotificationViewHolder holder = new NotificationsAdapter.NotificationViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        final UserNotification events = userNotifications.get(position);
        final Context notificationContext = mContext;
        String displayStatus;
        holder.event_title.setText(events.getEventTitle());
        holder.event_location.setText(events.getEventLocation());
        holder.date.setText(events.getEventDate());
        if(events.getStatus().equals("Pending")){
            displayStatus = "Waiting for your confirmation";
            holder.status.setText(displayStatus);
            holder.status.setTextColor(Color.parseColor("#F57C00"));
        }
    }

    @Override
    public int getItemCount() {
        return userNotifications.size();
    }

}
