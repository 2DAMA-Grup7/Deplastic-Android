package org.deplastic.Deplastic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
            } catch (JSONException e) {
                e.printStackTrace();
            }


            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://deplastic.netlify.app/.netlify/functions/api/login";
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, credentials,
                    response -> {
                        try {
                            if (response.getBoolean("auth")) {
                                savetoPref(response);
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
    }
    private void savetoPref(JSONObject response) {
        SharedPreferences sharedpref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putString("Credentials", response.toString());

        editor.apply();}
}


