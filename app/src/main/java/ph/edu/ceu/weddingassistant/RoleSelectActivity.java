package ph.edu.ceu.weddingassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoleSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_select);

        Button btnClient, btnEventCoordinator, btnBusiness;

        btnClient = (Button) findViewById(R.id.btn_client);
        btnEventCoordinator = (Button) findViewById(R.id.btn_event_coordinator);
        btnBusiness = (Button) findViewById(R.id.btn_business);

        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RoleSelectActivity.this, ClientRegister.class));
            }
        });

        btnEventCoordinator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RoleSelectActivity.this, EventCoordinatorRegister.class));
            }
        });

        btnBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RoleSelectActivity.this, BusinessRegister.class));
            }
        });
    }
}
