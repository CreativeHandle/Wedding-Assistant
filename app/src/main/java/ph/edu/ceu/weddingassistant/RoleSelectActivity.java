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

        Button btnClient, btnEventCoordinator, btnServiceProvider;

        btnClient = (Button) findViewById(R.id.btn_client);
        btnEventCoordinator = (Button) findViewById(R.id.btn_event_coordinator);
        btnServiceProvider = (Button) findViewById(R.id.btn_service_provider);

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

        btnServiceProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RoleSelectActivity.this, ServiceProviderRegister.class));
            }
        });
    }
}
