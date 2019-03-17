package ph.edu.ceu.weddingassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ph.edu.ceu.weddingassistant.models.Users;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DAGDAG
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Please wait...");
        //DAGDAG
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            checkUser();
            finish();
        }


        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.txt_email);
        inputPassword = (EditText) findViewById(R.id.txt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Enter e-mail address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    dialog.hide();
                    Toast.makeText(getApplicationContext(), "Enter password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    dialog.hide();
                    inputPassword.setError("Password length must be more than six characters.");
                    return;
                }

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (task.isSuccessful()) {
                                    dialog.hide();
                                    checkUser();
                                    finish();

                                } else {
                                    dialog.hide();
                                    Toast.makeText(LoginActivity.this, ("Authentication failed."), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    private void checkUser(){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef = rootRef.child("users").child(uid);
        ValueEventListener loginListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users user = dataSnapshot.getValue(Users.class);
                String role = user.role;
                if (role.equals("client")){
                    startActivity(new Intent(LoginActivity.this, ClientActivity.class));
                    finish();
                }

                if (role.equals("eventCoordinator")){
                    startActivity(new Intent(LoginActivity.this,EventCoordinatorActivity.class));
                    finish();
                }

                if (role.equals("serviceProvider")){
                    startActivity(new Intent(LoginActivity.this, ServiceProviderActivity.class));
                    finish();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LoginActivity", "loadPost:onCancelled", databaseError.toException());
            }
        };
        userRef.addValueEventListener(loginListener);
    }
}