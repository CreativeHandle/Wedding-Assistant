package ph.edu.ceu.weddingassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ph.edu.ceu.weddingassistant.models.FirebaseServiceProviderInfo;
import ph.edu.ceu.weddingassistant.models.Users;

public class ServiceProviderRegister extends AppCompatActivity {

    EditText name,email,password,confirm_password,phone,businesspermit,fee;
    Button submit;
    DatabaseReference userRegistration;
    Spinner spinner_categories;
    private FirebaseAuth mAuth;
    private static final String[] categories= {"Catering Services", "Photographer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_register);
        //Firebase auth
        mAuth = FirebaseAuth.getInstance();
        userRegistration = FirebaseDatabase.getInstance().getReference();
        //EDIT TEXT
        name = (EditText) findViewById(R.id.e_name);
        email = (EditText) findViewById(R.id.e_email);
        password = (EditText) findViewById(R.id.e_password);
        confirm_password = (EditText) findViewById(R.id.e_confirm_password);
        phone = (EditText) findViewById(R.id.e_phone);
        businesspermit = (EditText) findViewById(R.id.e_business_permit);
        fee = (EditText) findViewById(R.id.e_cost);
        //Button
        submit = (Button) findViewById(R.id.btn_submit);
        //SPINNER
        spinner_categories = findViewById(R.id.sp_register_service_category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ServiceProviderRegister.this, android.R.layout.simple_spinner_item,categories);
        spinner_categories.setAdapter(adapter);
        //Onclick
        final ProgressDialog dialog = new ProgressDialog(ServiceProviderRegister.this);
        dialog.setMessage("Please wait...");
        onSubmitClick(submit,name,email,phone,password,confirm_password,businesspermit,spinner_categories,fee,dialog);

    }

    //SUBMIT CLICK
    private void onSubmitClick(Button submit,
                               final EditText name,
                               final EditText email,
                               final EditText phone,
                               final EditText password,
                               final EditText confirm_password,
                               final EditText businesspermit,
                               final Spinner spinner,
                               final EditText fee,
                               final ProgressDialog dialog){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                final String fullName_string = name.getText().toString();
                final String email_string = email.getText().toString();
                final String password_string = password.getText().toString();
                final String confirm_password_string = confirm_password.getText().toString();
                final String phone_string = phone.getText().toString();
                final String businesspermit_string = businesspermit.getText().toString();
                final String category_string = spinner.getSelectedItem().toString().trim();
                final String fee_string = fee.getText().toString();
                final Double fee_double = Double.parseDouble(fee_string);

                if (TextUtils.isEmpty(fullName_string)) {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Enter your name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone_string)) {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Enter contact number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email_string)) {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Enter e-mail address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password_string)) {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Enter password.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confirm_password_string)){
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!TextUtils.equals(password_string,confirm_password_string)){
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Password not Match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(businesspermit_string)) {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Enter business permit.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fee_string)) {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Enter your Fee.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fee_double<=1000){
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Fee must be at least 1000", Toast.LENGTH_SHORT).show();
                    return;
                }

                //PLEASE CHECK

                mAuth.createUserWithEmailAndPassword(email_string,password_string).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    dialog.hide();
                                    sendToDatabase(
                                            task.getResult().getUser(),
                                            email_string,
                                            fullName_string,
                                            phone_string,
                                            businesspermit_string);
                                    sendToDatabaseServiceProvider(task.getResult().getUser(),
                                            fullName_string,
                                            email_string,
                                            phone_string,
                                            businesspermit_string,
                                            category_string,
                                            fee_double);
                                    startActivity(new Intent(ServiceProviderRegister.this, ServiceProviderActivity.class));
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });
    }

    //SEND TO DATABASE
    private void sendToDatabase(FirebaseUser users,
                                String name,
                                String email,
                                String phone,
                                String permit){
        String id = users.getUid();
        Users user = new Users(name,email,"serviceProvider",phone,permit);
        userRegistration.child("users").child(id).setValue(user);
        finish();
    }
    private void sendToDatabaseServiceProvider(FirebaseUser users,
                                               String name,
                                               String email,
                                               String phone,
                                               String permit,
                                               String category,
                                               Double cost){
        String id = users.getUid();
        FirebaseServiceProviderInfo user = new FirebaseServiceProviderInfo(name,email,phone,permit,category,cost);
        userRegistration.child("serviceProviderInfo").child(id).setValue(user);
        finish();
    }

}
