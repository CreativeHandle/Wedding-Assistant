package ph.edu.ceu.weddingassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ph.edu.ceu.weddingassistant.models.Users;

public class WelcomeScreen extends AppCompatActivity {

    private FirebaseAuth auth;
    private Button btnRegister, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            checkUser();
            finish();
        }

        btnRegister = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        auth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreen.this, RoleSelectActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(WelcomeScreen.this, ######));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeScreen.this, LoginActivity.class));
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
                String role = user.getRole();
                if (role.equals("client")){
                    startActivity(new Intent(WelcomeScreen.this, ClientActivity.class));
                    finish();
                }

                if (role.equals("EventCoordinator")){
                    startActivity(new Intent(WelcomeScreen.this,EventCoordinatorActivity.class));
                    finish();
                }

                if (role.equals("serviceProvider")){
                    startActivity(new Intent(WelcomeScreen.this, ServiceProviderActivity.class));
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