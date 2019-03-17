package ph.edu.ceu.weddingassistant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ph.edu.ceu.weddingassistant.models.Users;

public class ClientRegister extends AppCompatActivity {

    EditText name,email,password,confirm_password,phone;
    Button submit;
    DatabaseReference userRegistration;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_and_event_coordinator_register);
        //Firebase auth
        mAuth = FirebaseAuth.getInstance();
        userRegistration = FirebaseDatabase.getInstance().getReference();
        //EDIT TEXT
        name = (EditText) findViewById(R.id.e_name);
        email = (EditText) findViewById(R.id.e_email);
        password = (EditText) findViewById(R.id.e_password);
        confirm_password = (EditText) findViewById(R.id.e_confirm_password);
        phone = (EditText) findViewById(R.id.e_phone);
        //Button
        submit = (Button) findViewById(R.id.btn_submit);
        //Onclick
        onSubmitClick(submit,name,email,phone,password,confirm_password);

    }

    //SUBMIT CLICK
    private void onSubmitClick(Button submit,
                               final EditText name,
                               final EditText email,
                               final EditText phone,
                               final EditText password,
                               final EditText confirm_password){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullName_string = name.getText().toString();
                final String email_string = email.getText().toString();
                final String password_string = password.getText().toString();
                String confirm_password_string = confirm_password.getText().toString();
                final String phone_string = phone.getText().toString();

                if (TextUtils.isEmpty(fullName_string)) {
                    Toast.makeText(getApplicationContext(), "Enter your name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email_string)) {
                    Toast.makeText(getApplicationContext(), "Enter e-mail address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password_string)) {
                    Toast.makeText(getApplicationContext(), "Enter password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password_string)) {
                    Toast.makeText(getApplicationContext(), "Confirm your password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone_string)) {
                    Toast.makeText(getApplicationContext(), "Enter contact number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(confirm_password_string)){
                    Toast.makeText(getApplicationContext(), "Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!TextUtils.equals(password_string,confirm_password_string)){
                    Toast.makeText(getApplicationContext(), "Password not Match", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email_string,password_string).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    sendToDatabase(
                                            task.getResult().getUser(),
                                            fullName_string,
                                            email_string,
                                            phone_string);
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
                                String contactNumber){
        String id = users.getUid();
        Users user = new Users(name,email,"client",contactNumber,null);
        userRegistration.child("users").child(id).setValue(user);
        startActivity(new Intent(ClientRegister.this, ClientActivity.class));
        finish();
    }
}