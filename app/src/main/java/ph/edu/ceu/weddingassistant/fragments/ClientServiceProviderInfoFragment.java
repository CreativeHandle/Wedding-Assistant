package ph.edu.ceu.weddingassistant.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.models.UserEvents;
import ph.edu.ceu.weddingassistant.models.userSchedule;

public class ClientServiceProviderInfoFragment extends Fragment
        implements OnDateSelectedListener {
    View mView;

    TextView text_service_name,
            text_service_email,
            text_service_cost,
            text_service_contact,
            text_service_category,
            text_service_permit;

    ImageView serviceProviderThumbnail;

    DatabaseReference rootRef;

    String event,
            location,
            uid,
            service_name,
            service_email,
            service_cost,
            service_contact,
            service_category,
            service_permit;

    int service_image;

    Button button;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d-MM-yyyy");

    MaterialCalendarView widget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_client_service_provider_info, container, false);

        Bundle bundle = this.getArguments();
        uid = bundle.getString("service_uid");
        service_name = bundle.getString("service_name");
        service_email = bundle.getString("service_email");
        service_cost = bundle.getString("service_cost");
        service_contact = bundle.getString("service_contact");
        service_category = bundle.getString("service_category");
        service_permit = bundle.getString("service_permit");
        service_image = bundle.getInt("service_image");

        String this_cost = "P "+service_cost;

        text_service_name = mView.findViewById(R.id.info_name);
        text_service_email = mView.findViewById(R.id.info_email);
        text_service_cost = mView.findViewById(R.id.info_cost);
        text_service_contact = mView.findViewById(R.id.info_contact);
        text_service_category = mView.findViewById(R.id.info_category);
        text_service_permit = mView.findViewById(R.id.info_permit);
        serviceProviderThumbnail = mView.findViewById(R.id.thumbnail);

        Glide.with(getActivity()).load(service_image).into(serviceProviderThumbnail);

        text_service_name.setText(service_name);
        text_service_email.setText(service_email);
        text_service_cost.setText(this_cost);
        text_service_contact.setText(service_contact);
        text_service_category.setText(service_category);
        text_service_permit.setText(service_permit);

        widget = (MaterialCalendarView) mView.findViewById(R.id.calendarViewPhotographer);

        final LocalDate calendar = LocalDate.now().plusDays(7);
        final LocalDate min = LocalDate.of(calendar.getYear(), calendar.getMonth(), calendar.getDayOfMonth());
        final LocalDate max = LocalDate.of(calendar.getYear() + 1, calendar.getMonth(), calendar.getDayOfMonth());

        getDatesToDisable(new MyCallback() {
            @Override
            public void onCallback(List<CalendarDay> value) {
                widget.addDecorator(new disableDates(value));
            }

        });


        widget.setOnDateChangedListener(this);

        widget.state().edit()
                .setMinimumDate(min)
                .setMaximumDate(max)
                .commit();

        button = (Button) mView.findViewById(R.id.hire);

        //DIALOG
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("New Request");

        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_request, (ViewGroup) getView(), false);

        final EditText eventTitle = (EditText) viewInflated.findViewById(R.id.input);
        final EditText eventLocation = (EditText) viewInflated.findViewById(R.id.location);
        final TextView details = (TextView) viewInflated.findViewById(R.id.details);

        builder.setView(viewInflated);

        onButtonClick(button,uid,service_name,widget,builder,eventTitle,eventLocation,details,viewInflated);

        return mView;

    }

    private class disableDates implements DayViewDecorator{
        private List<CalendarDay> list;

        public disableDates(List<CalendarDay> list) {
            this.list = list;
        }

        @Override
        public boolean shouldDecorate(CalendarDay calendarDay) {
            if(list!=null){
                return list.contains(calendarDay);
            }
            return false;
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.setDaysDisabled(true);
            view.addSpan(new DotSpan(5, Color.RED));
        }
    }

    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected) {
        final String text = String.format("%s is available", FORMATTER.format(date.getDate()));
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    private void onButtonClick(Button btn,
                               final String uidServiceProvider,
                               final String service_name,
                               final MaterialCalendarView widget,
                               final AlertDialog.Builder builder,
                               final EditText event_title,
                               final EditText event_location,
                               final TextView details,
                               final View viewInflated){

        builder.setPositiveButton("Hire", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                event = event_title.getText().toString();
                location = event_location.getText().toString();

                if(!event.isEmpty()&&!location.isEmpty()){
                    CalendarDay day = widget.getSelectedDate();

                    rootRef = FirebaseDatabase.getInstance().getReference();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    UserEvents events = new UserEvents(uid,uidServiceProvider,"Pending",event,day.getDate().toString(),location);

                    //String stringDay = day.getDate().toString();
                    //userSchedule newSched = new userSchedule(stringDay);
                    //rootRef.child("userSchedule").child(uidServiceProvider).push().setValue(newSched);

                    rootRef.child("userEvents").child(uid).push().setValue(events);
                    rootRef.child("userEvents").child(uidServiceProvider).push().setValue(events);

                    Toast.makeText(getActivity(), "Request sent!", Toast.LENGTH_SHORT).show();

                    dialog.dismiss();
                }else{
                    Toast.makeText(getActivity(), "Please supply the required fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //BUTTON
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarDay day = widget.getSelectedDate();
                if(day==null){
                    Toast.makeText(getActivity(), "Please select a day", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    String text = "You are hiring "+ service_name +"  on " + day.getDate();
                    details.setText(text);
                    event_title.setText("");
                    event_location.setText("");
                    if(viewInflated.getParent()!=null){
                        ((ViewGroup)viewInflated.getParent()).removeView(viewInflated);
                    }
                    builder.setView(viewInflated);
                    builder.show();
                }
            }
        });
    }

    // GET DATE FROM DATABASE

    public interface MyCallback {
        void onCallback(List<CalendarDay> value);
    }

    public void getDatesToDisable(final MyCallback myCallback){
        rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("userSchedule").child(uid);
        final List<CalendarDay> Disable = new ArrayList<>();


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    userSchedule schedule = data.getValue(userSchedule.class);
                    String day = schedule.getOccupiedDay();
                    LocalDate parsedDay = LocalDate.parse(day);
                    CalendarDay date = CalendarDay.from(parsedDay.getYear(),parsedDay.getMonth().getValue(),parsedDay.getDayOfMonth());
                    Disable.add(date);
                }
                myCallback.onCallback(Disable);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
