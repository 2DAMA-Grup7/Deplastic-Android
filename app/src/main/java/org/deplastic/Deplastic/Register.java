package org.deplastic.Deplastic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
private CheckBox alertCheckBox;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        Button workingRegister = findViewById(R.id.RegisterButton);
        CheckBox EULABox = findViewById(R.id.checkBoxEULA);
        alertCheckBox = (CheckBox) findViewById(R.id.checkBoxEULA);
        alertCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder EULAtxt = new AlertDialog.Builder(Register.this);
                EULAtxt.setMessage(R.string.EULAcontents);
                AlertDialog title = EULAtxt.create();
                title.setTitle(getString(R.string.eulaAlert));
                title.show();
            }

        });

                workingRegister.setOnClickListener(v -> {
        if (EULABox.isChecked()) {

            EditText usernameReg = findViewById(R.id.registerUser);
            String usernameStr = usernameReg.getText().toString();

            EditText emailReg = findViewById(R.id.registerEmail);
            String emailStr = emailReg.getText().toString();

            EditText passReg = findViewById(R.id.registerPassword);
            String passStr = passReg.getText().toString();



            JSONObject regData = new JSONObject();

            try {
                regData.put("username", usernameStr);
                regData.put("password", passStr);
                regData.put("email", emailStr);
                regData.put("roles","client");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestQueue tail = Volley.newRequestQueue(getApplicationContext());
            String tailUrl = "https://deplastic.netlify.app/.netlify/functions/api/register";
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, tailUrl, regData,
                    response -> {
                        try {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                            if (response.getBoolean("true")) {
                                Toast.makeText(getApplicationContext(), "You have successfully registered", Toast.LENGTH_SHORT).show();
                                Intent newIntent = new Intent(getApplicationContext(), Login.class);
                                startActivity(newIntent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Unable to register, try again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, Throwable::printStackTrace);
            tail.add(stringRequest);
        }
        else{


                        Toast.makeText(getApplicationContext(), "Must Agree with EULA", Toast.LENGTH_SHORT).show();

        }
                });}
}




