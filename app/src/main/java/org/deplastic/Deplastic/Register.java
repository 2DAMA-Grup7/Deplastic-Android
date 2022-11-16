package org.deplastic.Deplastic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
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
        workingRegister.setOnClickListener(v ->{

            EditText usernameReg = findViewById(R.id.registerUser);
            String usernameStr = usernameReg.getText().toString();

            EditText emailReg = findViewById(R.id.registerEmail);
            String emailStr = emailReg.getText().toString();

            EditText passReg = findViewById(R.id.registerPassword);
            String passStr = passReg.getText().toString();

            JSONObject regData = new JSONObject();

            try {
                regData.put("username",usernameStr)
                regData.put("email",emailStr);
                regData.put("password",passStr);
                regData.put("type",0);

            } catch (JSONException e){
                e.printStackTrace();
            }

            requestQueue tail = Volley.newRequestQueue(getApplicationContext());
            String tailUrl = "https://deplastic.netlify.app/.netlify/functions/api/user";
            JsonObjectR stringRequest = new JsonObjectRequest(Request.Method.POST,url,regData,
                    response -> {
                      try {
                          Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_SHORT).show();




                    );



        });
    }
}