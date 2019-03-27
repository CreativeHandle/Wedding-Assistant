package ph.edu.ceu.weddingassistant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.adapter.NotificationsAdapter;
import ph.edu.ceu.weddingassistant.models.UserEvents;
import ph.edu.ceu.weddingassistant.models.UserNotification;

public class ServiceProviderNotificationsFragment extends Fragment {

    View mView;
    private DatabaseReference mDatabase;
    RecyclerView recyclerView;
    NotificationsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_service_provider_notifications, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference notificationRef = mDatabase.child("userEvents").child(uid);

        recyclerView =(RecyclerView) mView.findViewById(R.id.recyclerView);

        final ArrayList<UserNotification> notificationList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NotificationsAdapter(getActivity(), notificationList);
        recyclerView.setAdapter(adapter);

        notificationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        UserEvents event = data.getValue(UserEvents.class);
                        String key = data.getKey();
                        if(event.getStatus().equals("Pending")){

                            notificationList.add(
                                    new UserNotification(
                                            key,
                                            event.getClientId(),
                                            event.getServiceProvider(),
                                            event.getStatus(),
                                            event.getEventTitle(),
                                            event.getEventDate(),
                                            event.getEventLocation()));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Notifications");
    }
}
