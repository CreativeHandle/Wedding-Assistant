package ph.edu.ceu.weddingassistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class UserRegister extends AppCompatActivity {

    EditText email,password,confirm_password,firstName,lastName;
    Button submit;

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





    }

    //SUBMIT CLICK
    private void onSubmitClick(){

    }
}
