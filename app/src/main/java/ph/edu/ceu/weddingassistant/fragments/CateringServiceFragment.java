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

public class CateringServiceFragment extends Fragment {
    View mView;
    RecyclerView recyclerView;
    SortAdapter adapter;
    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_client_services, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference serviceProviderRef = mDatabase.child("serviceProviderInfo");

        recyclerView =(RecyclerView) mView.findViewById(R.id.recycler_view);

        final ArrayList<ServiceProviderInfo> infoList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new SortAdapter(getActivity(), infoList);
        recyclerView.setAdapter(adapter);

        final int catering_array[] = {
                R.drawable.catering1,
                R.drawable.catering2,
                R.drawable.catering3};

        serviceProviderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot data:dataSnapshot.getChildren()){
                        int rnd2 = new Random().nextInt(catering_array.length);

                        final FirebaseServiceProviderInfo info = data.getValue(FirebaseServiceProviderInfo.class);
                        String uid = data.getKey();
                        String category = info.f_category;
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
        getActivity().setTitle("Catering Services");
    }
}
