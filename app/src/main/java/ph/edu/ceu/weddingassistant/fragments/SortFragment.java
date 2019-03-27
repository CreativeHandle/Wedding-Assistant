package ph.edu.ceu.weddingassistant.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.temporal.ChronoField;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.adapter.SortAdapter;
import ph.edu.ceu.weddingassistant.models.FirebaseServiceProviderInfo;
import ph.edu.ceu.weddingassistant.models.ServiceProviderInfo;
import ph.edu.ceu.weddingassistant.models.userSchedule;

public class SortFragment extends Fragment {

    View mView;
    RecyclerView recyclerView;
    SortAdapter adapter;
    private DatabaseReference mDatabase;
    private DatePickerDialog.OnDateSetListener mDateListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_sort, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");

        recyclerView =(RecyclerView) mView.findViewById(R.id.recyclerView);

        final int photographers_array[] = {
                R.drawable.photographer1,
                R.drawable.photographer2,
                R.drawable.photographer3};

        final int catering_array[] = {
                R.drawable.catering1,
                R.drawable.catering2,
                R.drawable.catering3};

        final ArrayList<ServiceProviderInfo> infoList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new SortAdapter(getActivity(), infoList);
        recyclerView.setAdapter(adapter);

        Button submit = mView.findViewById(R.id.submit_button);
        EditText value = mView.findViewById(R.id.value);

        TextView set_date = (TextView) mView.findViewById(R.id.select_date);

        //
        getServiceProviders(mDatabase,photographers_array,catering_array,infoList);
        //
        sortServiceProviders(submit,value,dialog,mDatabase,photographers_array,catering_array,infoList,set_date);
        //

        setDateText(set_date);

        return mView;

    }
    private void setDateText(final TextView set_date){
        set_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        LocalDate date  = LocalDate.of(year,month+1,dayOfMonth);
                        set_date.setText(date.toString());

                    }
                };

                LocalDate localDate = LocalDate.now().plusDays(7);
                int year = localDate.getYear();
                int month = localDate.getMonth().getValue();
                int day = localDate.getDayOfMonth();

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getActivity(),
                                R.style.Theme_MaterialComponents_Light_Dialog_MinWidth,
                                mDateListener,
                                year,
                                month,
                                day);

                final LocalDate min = LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.getDayOfMonth());
                final LocalDate max = LocalDate.of(localDate.getYear() + 1, localDate.getMonth(), localDate.getDayOfMonth());
                ZonedDateTime minZone = min.atStartOfDay(ZoneId.of("Asia/Singapore"));
                ZonedDateTime maxZone = max.atStartOfDay(ZoneId.of("Asia/Singapore"));
                datePickerDialog.getDatePicker().setMinDate(minZone.toInstant().toEpochMilli());
                datePickerDialog.getDatePicker().setMaxDate(maxZone.toInstant().toEpochMilli());

                datePickerDialog.show();

            }
        });
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Service Providers");
    }

    private void getServiceProviders(DatabaseReference mDatabase,
                                     final int[] photographers_array,
                                     final int[] catering_array,
                                     final List<ServiceProviderInfo> infoList){
        DatabaseReference serviceProviderRef = mDatabase.child("serviceProviderInfo");
        serviceProviderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot data:dataSnapshot.getChildren()){
                        int rnd1 = new Random().nextInt(photographers_array.length);
                        int rnd2 = new Random().nextInt(catering_array.length);
                        final FirebaseServiceProviderInfo info = data.getValue(FirebaseServiceProviderInfo.class);
                        String category = info.f_category;
                        String uid = data.getKey();
                        if(category.equals("Photographer")){
                            infoList.add(new ServiceProviderInfo(
                                    uid,
                                    info.f_service_name,
                                    info.f_service_email,
                                    info.f_contact,
                                    info.f_permit,
                                    info.f_category,
                                    info.f_cost,
                                    photographers_array[rnd1]
                            ));
                        }
                        if(category.equals("Catering Services")){
                            infoList.add(new ServiceProviderInfo(
                                    uid,
                                    info.f_service_name,
                                    info.f_service_email,
                                    info.f_contact,
                                    info.f_permit,
                                    info.f_category,
                                    info.f_cost,
                                    catering_array[rnd2]
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void sortServiceProviders(Button submit,
                                      final EditText value,
                                      final ProgressDialog dialog,
                                      final DatabaseReference mDatabase,
                                      final int[] photographers_array,
                                      final int[] catering_array,
                                      final List<ServiceProviderInfo> infoList,
                                      final TextView date){

        final DatabaseReference serviceProviderRef = mDatabase.child("serviceProviderInfo");
        final DatabaseReference excludeRef = mDatabase.child("userSchedule");
        final List<String> excluded_list = new ArrayList<>();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myValue = value.getText().toString();
                final Double dValue = Double.parseDouble(myValue);
                excluded_list.clear();
                if(dValue<1000){
                    dialog.hide();
                    Toast.makeText(getActivity(), "Value must be at least 1000", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String date_string = date.getText().toString();
                if(!date_string.equals("Select Date")){
                    excludeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot data:dataSnapshot.getChildren()){
                                String key = data.getKey();
                                List<String> schedule = new ArrayList<>();
                                for(DataSnapshot dates:data.getChildren()){
                                    userSchedule day = dates.getValue(userSchedule.class);
                                    schedule.add(day.getOccupiedDay());
                                }
                                if(schedule.contains(date_string)){
                                    excluded_list.add(key);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                dialog.show();
                infoList.clear();

                serviceProviderRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot data:dataSnapshot.getChildren()){
                                String key = data.getKey();
                                int rnd1 = new Random().nextInt(photographers_array.length);
                                int rnd2 = new Random().nextInt(catering_array.length);
                                final FirebaseServiceProviderInfo info = data.getValue(FirebaseServiceProviderInfo.class);
                                if(info.f_cost<=dValue && !excluded_list.contains(key)){
                                    String uid = dataSnapshot.getKey();
                                    String category = info.f_category;
                                    if(category.equals("Photographer")){
                                        infoList.add(new ServiceProviderInfo(
                                                uid,
                                                info.f_service_name,
                                                info.f_service_email,
                                                info.f_contact,
                                                info.f_permit,
                                                info.f_category,
                                                info.f_cost,
                                                photographers_array[rnd1]
                                        ));
                                    }
                                    if(category.equals("Catering Services")){
                                        infoList.add(new ServiceProviderInfo(
                                                uid,
                                                info.f_service_name,
                                                info.f_service_email,
                                                info.f_contact,
                                                info.f_permit,
                                                info.f_category,
                                                info.f_cost,
                                                catering_array[rnd2]
                                        ));
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                dialog.hide();
            }
        });
    }
}
