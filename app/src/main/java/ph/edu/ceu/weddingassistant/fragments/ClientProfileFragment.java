package ph.edu.ceu.weddingassistant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.models.Users;

public class ClientProfileFragment extends Fragment {
    View mView;

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = rootRef.child("users").child(uid);

    EditText text_name,text_phone;
    Button update,submit,cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_client_profile, container, false);

        //TEXT
        text_name = mView.findViewById(R.id.c_p_name);
        text_phone = mView.findViewById(R.id.c_p_phone);
        //BUTTON
        update = mView.findViewById(R.id.btn_c_p_update);
        submit = mView.findViewById(R.id.btn_c_p_submit);
        cancel = mView.findViewById(R.id.btn_c_p_cancel);

        editTextToggle(false,text_name,text_phone);
        setText(text_name,text_phone);
        buttonsInitial(update,submit,cancel);

        return  mView;
    }

    private void setText(final EditText name,
                         final EditText phone){
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users info = dataSnapshot.getValue(Users.class);
                name.setText(info.getName());
                phone.setText(info.getContactNumber());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile");
    }

    private void buttonsInitial(Button Update,
                                Button Submit,
                                Button Cancel){
        Update.setVisibility(View.VISIBLE);
        Submit.setVisibility(View.GONE);
        Cancel.setVisibility(View.GONE);
    }
    private void buttonsOnClick(Button Update,
                                Button Submit,
                                Button Cancel){
        Update.setVisibility(View.GONE);
        Submit.setVisibility(View.VISIBLE);
        Cancel.setVisibility(View.VISIBLE);
    }
    private void updateInfo(EditText name,
                       EditText phone){

    }
    private void editTextToggle(boolean toggle,
                              EditText text_name,
                              EditText text_phone
                              ){
        text_name.setEnabled(toggle);
        text_phone.setEnabled(toggle);

    }

}
