package org.deplastic.Deplastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button b = findViewById(R.id.LoginButton);
        b.setOnClickListener(v -> {
            EditText email = findViewById(R.id.editText1);
            String emailVal = email.getText().toString();

            EditText passwd = findViewById(R.id.editText2);
            String passwdVal = passwd.getText().toString();

            JSONObject credentials = new JSONObject();

            try {
                credentials.put("email", emailVal);
                credentials.put("password", passwdVal);
                credentials.put("type", 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://deplastic.netlify.app/.netlify/functions/api/login";
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, credentials,
                    response -> {
                        try {
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                            if (response.getBoolean("auth")) {
                                Toast.makeText(getApplicationContext(), "Benvingut User.", Toast.LENGTH_SHORT).show();
                                Intent newIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(newIntent);
                            }else {
                                Toast.makeText(getApplicationContext(), "Wrong user or password, try again.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, Throwable::printStackTrace);
            queue.add(stringRequest);
        });

        Button R = findViewById(org.deplastic.Deplastic.R.id.toRegisterButton);
        R.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),Register.class);
            startActivity(intent);
        });
        Intent newIntent = new Intent(getApplicationContext(), MainActivity.class);
    }
}


