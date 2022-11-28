package org.deplastic.Deplastic.Credentials;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.deplastic.Deplastic.MainActivity;
import org.deplastic.Deplastic.R;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);

        if(sp.getBoolean("logged",false)){
            Intent newIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(newIntent);
        }
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
            } catch (JSONException e) {
                e.printStackTrace();
            }


            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://deplastic.netlify.app/.netlify/functions/api/login";
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, credentials,
                    response -> {
                        try {
                            if (response.getBoolean("auth")) {

                                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_LONG).show();
                                Intent newIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(newIntent);
                                sp.edit().putBoolean("logged",true).apply();
                             /*   sp.edit().putString("email",emailVal);
                                sp.edit().putString("password",passwdVal);
                                sp.edit().putString("auth",);    */
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
    }

    private String readfromPref() {
        SharedPreferences prefs = this.getSharedPreferences("general_settings", Context.MODE_PRIVATE);
        String lanSettings = prefs.getString("Credentials", null);

        return lanSettings;
    }

    private void savetoPref(JSONObject response) {
        SharedPreferences sharedpref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putString("Credentials", response.toString());

        editor.apply();}
}


