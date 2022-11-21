package org.deplastic.Deplastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        Button workingRegister = findViewById(R.id.RegisterButton);
        CheckBox EULABox = findViewById(R.id.checkBoxEULA);
        
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
                regData.put("roles", "client");
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
        }});}}




