package ph.edu.ceu.weddingassistant.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ph.edu.ceu.weddingassistant.R;
import ph.edu.ceu.weddingassistant.models.FirebaseServiceProviderInfo;
import ph.edu.ceu.weddingassistant.models.Users;

public class ServiceProviderProfileFragment extends Fragment {
    View mView;

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference infoRef = rootRef.child("serviceProviderInfo").child(uid);

    EditText text_name,text_phone,text_permit,text_category,text_fee;
    Button update,submit,cancel;
    Spinner spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_service_provider_profile, container, false);

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");

        String[] categories = {"Catering Services", "Photographer"};
        spinner = (Spinner) mView.findViewById(R.id.sp_s_p_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(adapter);

        text_name = mView.findViewById(R.id.s_p_name);
        text_phone = mView.findViewById(R.id.s_p_phone);
        text_permit = mView.findViewById(R.id.s_p_business_permit);
        text_fee = mView.findViewById(R.id.s_p_cost);
        //BUTTON
        update = mView.findViewById(R.id.btn_service_profile_update);
        submit = mView.findViewById(R.id.btn_service_profile_submit);
        cancel = mView.findViewById(R.id.btn_service_profile_cancel);

        editTextToggle(false,text_name,text_phone,text_permit,text_fee,spinner);
        setText(text_name,text_phone,text_permit,text_fee,spinner);
        buttonsInitial(update,submit,cancel);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsOnClick(update,submit,cancel);
                editTextToggle(true,text_name,text_phone,text_permit,text_fee,spinner);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                buttonsInitial(update,submit,cancel);
                updateInfo(dialog,text_name,text_phone,text_permit,text_fee,spinner);
                setText(text_name,text_phone,text_permit,text_fee,spinner);
                dialog.hide();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsInitial(update,submit,cancel);
                editTextToggle(false,text_name,text_phone,text_permit,text_fee,spinner);
                setText(text_name,text_phone,text_permit,text_fee,spinner);
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
                         final EditText phone,
                         final EditText text_permit,
                         final EditText text_fee,
                         final Spinner category){
        infoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseServiceProviderInfo info = dataSnapshot.getValue(FirebaseServiceProviderInfo.class);
                name.setText(info.f_service_name);
                phone.setText(info.f_service_email);
                text_permit.setText(info.f_permit);
                text_fee.setText(info.f_cost.toString());
                if(info.f_category.equals("Catering Services")){
                    category.setSelection(0);
                }else{
                    category.setSelection(1);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void buttonsInitial(Button Update,
                                Button Submit,
                                Button Cancel){
        Update.setVisibility(View.VISIBLE);
        Submit.setVisibility(View.GONE);
        Cancel.setVisibility(View.GONE);
    }

    private void updateInfo(ProgressDialog dialog,
                            EditText name,
                            EditText phone,
                            EditText permit,
                            EditText fee,
                            Spinner category){
        final String fullName_string = name.getText().toString();
        final String phone_string = phone.getText().toString();
        final String permit_string = permit.getText().toString();
        final String fee_string = fee.getText().toString();
        final Double fee_double = Double.parseDouble(fee_string);
        final String category_string = category.getSelectedItem().toString().trim();

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

        if (TextUtils.isEmpty(permit_string)) {
            dialog.hide();
            Toast.makeText(getActivity(), "Enter permit number.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(fee_string)) {
            dialog.hide();
            Toast.makeText(getActivity(), "Enter fee", Toast.LENGTH_SHORT).show();
            return;
        }

        if(fee_double<1000){
            dialog.hide();
            Toast.makeText(getActivity(), "Fee must be at least 1000", Toast.LENGTH_SHORT).show();
            return;
        }

        String uid = FirebaseAuth.getInstance().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("users").child(uid).child("name").setValue(fullName_string);
        ref.child("users").child(uid).child("contactNumber").setValue(phone_string);
        ref.child("serviceProviderInfo").child(uid).child("f_contact").setValue(phone_string);
        ref.child("serviceProviderInfo").child(uid).child("f_cost").setValue(fee_double);
        ref.child("serviceProviderInfo").child(uid).child("f_category").setValue(category_string);

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
                                EditText text_phone,
                                EditText text_permit,
                                EditText text_fee,
                                Spinner category
    ){
        text_name.setEnabled(toggle);
        text_phone.setEnabled(toggle);
        text_permit.setEnabled(toggle);
        text_fee.setEnabled(toggle);
        category.setEnabled(toggle);

    }
}
