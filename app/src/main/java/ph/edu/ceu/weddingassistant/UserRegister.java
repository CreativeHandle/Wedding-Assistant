package ph.edu.ceu.weddingassistant;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ph.edu.ceu.weddingassistant.models.Users;

public class UserRegister extends AppCompatActivity {

    EditText email,password,confirm_password,firstName,lastName;
    Button submit;
    DatabaseReference userRegistration = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        //EDIT TEXT

        email = (EditText) findViewById(R.id.txt_user_register_email);
        password = (EditText) findViewById(R.id.txt_user_register_password);
        confirm_password = (EditText) findViewById(R.id.txt_user_register_confirm_password);
        firstName = (EditText) findViewById(R.id.txt_user_register_first_name);
        lastName = (EditText) findViewById(R.id.txt_user_register_last_name);

        submit = (Button) findViewById(R.id.btn_user_register_submit);
        onSubmitClick(submit,email,password,confirm_password,firstName,lastName);

    }

    //SUBMIT CLICK
    private void onSubmitClick(Button submit,
                               final EditText email,
                               final EditText password,
                               final EditText confirm_password,
                               final EditText firstName,
                               final EditText lastName){


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email_string = email.getText().toString();
                final String password_string = password.getText().toString();
                String confirm_password_string = confirm_password.getText().toString();
                final String firstName_string = firstName.getText().toString();
                final String lastName_string = lastName.getText().toString();
                //PLEASE CHECK

                mAuth.createUserWithEmailAndPassword(email_string,password_string).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                sendToDatabase(task.getResult().
                                        getUser().getUid(),
                                        email_string,
                                        password_string,
                                        firstName_string,
                                        lastName_string);
                            }
                        });

            }
        });
    }

    //SEND TO DATABASE
    private void sendToDatabase(String id,
                                String email,
                                String password,
                                String firstName,
                                String lastName){
        Users user = new Users(email,password,firstName,lastName);
        userRegistration.child("users").child(id).setValue(user);
    }

}
