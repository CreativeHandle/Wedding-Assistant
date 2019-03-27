package ph.edu.ceu.weddingassistant.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.models.UserEvents;
import ph.edu.ceu.weddingassistant.models.Users;
import ph.edu.ceu.weddingassistant.models.userSchedule;

public class EventsServiceProviderFragment extends Fragment {

    View mView;

    TextView eventTitle,eventLocation,eventDate,eventStatus,clientName,clientEmail,clientPhoneNumber;

    String eventId,clientId,eventTitleString,eventLocationString,eventDateString,eventStatusString;

    Button accept,decline;

    private DatabaseReference mDatabase,rootRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_event_service, container, false);

        Bundle bundle = this.getArguments();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        eventTitle = (TextView) mView.findViewById(R.id.event_title);
        eventLocation = (TextView) mView.findViewById(R.id.event_location);
        eventDate = (TextView) mView.findViewById(R.id.event_date);
        eventStatus = (TextView) mView.findViewById(R.id.event_status);
        clientName = (TextView) mView.findViewById(R.id.client_name);
        clientEmail = (TextView) mView.findViewById(R.id.client_email);
        clientPhoneNumber = (TextView) mView.findViewById(R.id.client_phone_number);

        accept = (Button) mView.findViewById(R.id.accept);
        decline = (Button) mView.findViewById(R.id.decline);

        eventId = bundle.getString("childId");
        clientId = bundle.getString("clientId");
        eventTitleString = bundle.getString("eventTitle");
        eventLocationString = bundle.getString("eventLocation");
        eventDateString = bundle.getString("eventDate");
        eventStatusString = bundle.getString("eventStatus");

        DatabaseReference userRef = mDatabase.child("users").child(clientId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users data = dataSnapshot.getValue(Users.class);
                eventTitle.setText(eventTitleString);
                eventLocation.setText(eventLocationString);
                eventDate.setText(eventDateString);
                eventStatus.setText(eventStatusString);
                clientName.setText(data.getName());
                clientEmail.setText(data.getEmail());
                clientPhoneNumber.setText(data.getContactNumber());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        getServiceProviderSchedule(new ScheduleCallback() {
            @Override
            public void onCallback(List<String> value) {
                if(value.contains(eventDateString)){
                    accept.setVisibility(View.INVISIBLE);
                }
            }
        });

        getEventStatus(new StatusCallback() {
            @Override
            public void onCallback(String value) {
                if(value.equals("Accepted")){
                    accept.setVisibility(View.INVISIBLE);
                    decline.setVisibility(View.INVISIBLE);
                }
                else if(value.equals("Declined")){
                    accept.setVisibility(View.INVISIBLE);
                    decline.setVisibility(View.INVISIBLE);
                }

            }
        });

        acceptButton(accept,clientId);
        declineButton(decline,clientId);

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Events");
    }

    public interface ScheduleCallback {
        void onCallback(List<String> value);
    }

    public interface StatusCallback {
        void onCallback(String value);
    }

    public void getServiceProviderSchedule(final ScheduleCallback myCallback){

        rootRef = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = rootRef.child("userSchedule").child(uid);
        final List<String> Disable = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    userSchedule schedule = data.getValue(userSchedule.class);
                    String day = schedule.getOccupiedDay();
                    Disable.add(day);
                }
                myCallback.onCallback(Disable);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void getEventStatus(final StatusCallback myCallback){

        rootRef = FirebaseDatabase.getInstance().getReference();
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = rootRef.child("userEvents").child(uid).child(eventId);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserEvents event = dataSnapshot.getValue(UserEvents.class);
                String status = event.getStatus();
                myCallback.onCallback(status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void acceptButton(Button accept,
                              final String clientId){
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                String uid = FirebaseAuth.getInstance().getUid();
                                reference.child("userEvents").child(clientId).child(eventId).child("status").setValue("Accepted");
                                reference.child("userEvents").child(uid).child(eventId).child("status").setValue("Accepted");
                                userSchedule newSched = new userSchedule(eventDateString);
                                reference.child("userSchedule").child(uid).push().setValue(newSched);
                                Toast.makeText(getActivity(), "Event accepted!", Toast.LENGTH_SHORT).show();
                                return;


                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                                return;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("This action is irreversible, Accept?").setPositiveButton("Accept", dialogClickListener)
                        .setNegativeButton("Cancel", dialogClickListener).show();

            }
        });
    }
    private void declineButton(Button decline,
                               final String clientId){
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                                String uid = FirebaseAuth.getInstance().getUid();
                                reference.child("userEvents").child(clientId).child(eventId).child("status").setValue("Declined");
                                reference.child("userEvents").child(uid).child(eventId).child("status").setValue("Declined");
                                return;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                                return;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("This action is irreversible, Decline?").setPositiveButton("Decline", dialogClickListener)
                        .setNegativeButton("Cancel", dialogClickListener).show();

            }
        });
    }

}
