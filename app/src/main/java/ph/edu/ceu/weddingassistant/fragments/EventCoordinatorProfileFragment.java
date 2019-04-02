package ph.edu.ceu.weddingassistant.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.models.Users;

public class EventCoordinatorProfileFragment extends Fragment {
    View mView;

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef = rootRef.child("users").child(uid);

    EditText text_name,text_phone;
    Button update,submit,cancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_event_coordinator_profile, container, false);

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");

        //TEXT
        text_name = mView.findViewById(R.id.e_p_name);
        text_phone = mView.findViewById(R.id.e_p_phone);
        //BUTTON
        update = mView.findViewById(R.id.btn_e_p_update);
        submit = mView.findViewById(R.id.btn_e_p_submit);
        cancel = mView.findViewById(R.id.btn_e_p_cancel);

        editTextToggle(false,text_name,text_phone);
        setText(text_name,text_phone);
        buttonsInitial(update,submit,cancel);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsOnClick(update,submit,cancel);
                editTextToggle(true,text_name,text_phone);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                editTextToggle(false,text_name,text_phone);
                buttonsInitial(update,submit,cancel);
                updateInfo(dialog,text_name,text_phone);
                setText(text_name,text_phone);
                dialog.hide();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsInitial(update,submit,cancel);
                editTextToggle(false,text_name,text_phone);
                setText(text_name,text_phone);
            }
        });

        return mView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile");
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

    private void updateInfo(ProgressDialog dialog,
                            EditText name,
                            EditText phone){
        final String fullName_string = name.getText().toString();
        final String phone_string = phone.getText().toString();

        if (TextUtils.isEmpty(fullName_string)) {
            dialog.hide();
            Toast.makeText(getActivity(), "Enter your name.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phone_string)) {
            dialog.hide();
            Toast.makeText(getActivity(), "Enter contact number.", Toast.LENGTH_SHORT).show();
            return;
        }
        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("users").child(uid).child("name").setValue(fullName_string);
        ref.child("users").child(uid).child("contactNumber").setValue(phone_string);
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
