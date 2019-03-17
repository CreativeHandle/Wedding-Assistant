package ph.edu.ceu.weddingassistant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.adapter.ClientPhotographersAdapter;
import ph.edu.ceu.weddingassistant.models.ServiceProviderInfo;

public class ClientPhotographersFragment extends Fragment {
    View mView;
    RecyclerView recyclerView;
    ClientPhotographersAdapter adapter;
    List <ServiceProviderInfo> infoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_client_photographer, container, false);
        //RECYCLER
        recyclerView =(RecyclerView) mView.findViewById(R.id.recycler_view_client_photographer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        infoList = new ArrayList<>();
        infoList.add(
                new ServiceProviderInfo("Eujohn Magno",
                        "eujohnmagno@gmail.com",
                        "09292929292",
                        null,
                        null,
                        123456789.0));


        adapter = new ClientPhotographersAdapter(this.getActivity(), infoList);
        recyclerView.setAdapter(adapter);

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Photographers");
    }

}
