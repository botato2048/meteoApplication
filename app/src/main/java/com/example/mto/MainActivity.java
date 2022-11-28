package com.example.mto;

import com.android.volley.Request.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    //Déclaration des champs
RequestQueue requestQueue;
    TextView date,city, temperature, Description;
    ImageView imageView5;
    String maVille="Toronto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        date=findViewById(R.id.date);
        city=findViewById(R.id.city);
        temperature=findViewById(R.id.temperature);
        Description=findViewById(R.id.Description);
        requestQueue= Volley.newRequestQueue(this);
        afficher();

    }
public void afficher() {
    String url = "https://api.openweathermap.org/data/2.5/weather?q=Toronto&appid=74ba4b0d6163b7741666f4e8d4d845fa&units=metric";
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
        requestQueue.add(jsonObjectRequest);
        public void onResponse(JSONObject response) {
            try {
                JSONObject main_object = response.getJSONObject("main");
                JSONArray array = response.getJSONArray("weather");
                Log.d("tag","resultat = "+ array.toString());
                System.out.print("salut");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }

    });
}
}