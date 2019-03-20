package ph.edu.ceu.weddingassistant.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.adapter.ClientCateringServiceAdapter;
import ph.edu.ceu.weddingassistant.adapter.SortAdapter;
import ph.edu.ceu.weddingassistant.models.FirebaseServiceProviderInfo;
import ph.edu.ceu.weddingassistant.models.ServiceProviderInfo;

public class SortFragment extends Fragment {

    View mView;
    RecyclerView recyclerView;
    SortAdapter adapter;
    private DatabaseReference mDatabase;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_sort, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference serviceProviderRef = mDatabase.child("serviceProviderInfo");

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

        serviceProviderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot data:dataSnapshot.getChildren()){
                        int rnd1 = new Random().nextInt(photographers_array.length);
                        int rnd2 = new Random().nextInt(catering_array.length);
                        final FirebaseServiceProviderInfo info = data.getValue(FirebaseServiceProviderInfo.class);
                        String category = info.f_category;
                        if(category.equals("Photographer")){
                            infoList.add(new ServiceProviderInfo(
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

        Button submit = mView.findViewById(R.id.submit_button);
        final EditText value = mView.findViewById(R.id.value);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                infoList.clear();
                String myValue = value.getText().toString();
                final Double dValue = Double.parseDouble(myValue);
                serviceProviderRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot data:dataSnapshot.getChildren()){

                                int rnd1 = new Random().nextInt(photographers_array.length);
                                int rnd2 = new Random().nextInt(catering_array.length);
                                final FirebaseServiceProviderInfo info = data.getValue(FirebaseServiceProviderInfo.class);
                                if(info.f_cost<=dValue){
                                    String category = info.f_category;
                                    if(category.equals("Photographer")){
                                        infoList.add(new ServiceProviderInfo(
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
                            dialog.hide();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        });

        return mView;

    }
}
