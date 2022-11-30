package org.deplastic.Deplastic.Credentials;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.deplastic.Deplastic.R;
import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button workingRegister = findViewById(R.id.RegisterButton);
        CheckBox EULABox = findViewById(R.id.checkBoxEULA);
        EULABox.setOnClickListener(v -> {
            AlertDialog.Builder eula = new AlertDialog.Builder(Register.this);
            eula.setMessage(R.string.EULAcontents);
            AlertDialog title = eula.create();
            title.setTitle(getString(R.string.eulaAlert));
            title.show();
        });

        workingRegister.setOnClickListener(v -> {
            if (EULABox.isChecked()) {

                EditText usernameReg = findViewById(R.id.registerUser);
                String usernameStr = usernameReg.getText().toString();

                EditText emailReg = findViewById(R.id.registerEmail);
                String emailStr = emailReg.getText().toString();

                EditText passReg = findViewById(R.id.registerPassword);
                String passStr = passReg.getText().toString();

                RadioButton ClientRadio = findViewById(R.id.radioButtonUser);
                if (ClientRadio.isChecked()) {
                    String rolStr = "client";
                    JSONObject regData = new JSONObject();
                    try {
                        regData.put("username", usernameStr);
                        regData.put("password", passStr);
                        regData.put("email", emailStr);
                        regData.put("roles", rolStr);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RequestQueue tail = Volley.newRequestQueue(getApplicationContext());
                    String tailUrl = "https://deplastic.netlify.app/.netlify/functions/api/register";
                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, tailUrl, regData,
                            response -> {
                                try {
                                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                    if (response.getBoolean("success")) {
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
                else {
                    String rolStr = "artist";
                    JSONObject regData = new JSONObject();
                    try {
                        regData.put("username", usernameStr);
                        regData.put("password", passStr);
                        regData.put("email", emailStr);
                        regData.put("roles", rolStr);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RequestQueue tail = Volley.newRequestQueue(getApplicationContext());
                    String tailUrl = "https://deplastic.netlify.app/.netlify/functions/api/register";
                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, tailUrl, regData,
                            response -> {
                                try {
                                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                    if (response.getBoolean("success")) {
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
            } else {
                Toast.makeText(getApplicationContext(), "Must Agree with EULA", Toast.LENGTH_SHORT).show();
            }
        });
    }
}




