package ph.edu.ceu.weddingassistant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.adapter.SortAdapter;
import ph.edu.ceu.weddingassistant.models.FirebaseServiceProviderInfo;
import ph.edu.ceu.weddingassistant.models.ServiceProviderInfo;

public class PhotographersFragment extends Fragment {
    View mView;
    RecyclerView recyclerView;
    SortAdapter adapter;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference serviceProviderRef = mDatabase.child("serviceProviderInfo");
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_client_photographer, container, false);
        //RECYCLER
        recyclerView =(RecyclerView) mView.findViewById(R.id.recycler_view_client_photographer);

        final ArrayList<ServiceProviderInfo> infoList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new SortAdapter(getActivity(), infoList);
        recyclerView.setAdapter(adapter);

        final int photographers_array[] = {
                R.drawable.photographer1,
                R.drawable.photographer2,
                R.drawable.photographer3};

        serviceProviderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot data:dataSnapshot.getChildren()){

                        int rnd1 = new Random().nextInt(photographers_array.length);

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
        getActivity().setTitle("Photographers");
    }
}
