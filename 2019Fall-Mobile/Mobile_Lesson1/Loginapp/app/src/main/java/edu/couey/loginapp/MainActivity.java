package edu.couey.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.content.Context;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                EditText usernameCtrl = (EditText) findViewById(R.id.userName);
                EditText passwordCtrl = (EditText) findViewById(R.id.password);
                String username = usernameCtrl.getText().toString();
                String password = passwordCtrl.getText().toString();
                boolean validationFlag = false;

                //verify if the username and pw are not empty
                if (!username.isEmpty() && !password.isEmpty()) {
                    if (username.equals("Admin") && password.equals("Admin")) {
                        validationFlag = true;
                    }
                }
                if (!validationFlag) {
                    //write code to handle app when validation is false
                    Context context = getApplicationContext();
                    CharSequence text = "Invalid Login!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();

                } else {
                    // code for validation true
                    reDirectToLoginSuccess(v);
                }
            }
    });

    }
    public void reDirectToLoginSuccess(View view) {
        Intent redirect = new Intent (MainActivity.this, LoginSuccess.class);
        startActivity(redirect);
    }

}
