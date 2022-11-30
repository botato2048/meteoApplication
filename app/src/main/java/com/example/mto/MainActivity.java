package com.example.mto;

import com.android.volley.Request.*;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchableInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    //Déclaration des champs
    TextView date, city, temperature, Description;
    private RequestQueue requestQueue;
    ImageView imageView5;
    String maVille = "Toronto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
       requestQueue = Volley.newRequestQueue(this);

        date = findViewById(R.id.date);
        city = findViewById(R.id.city);
        temperature = findViewById(R.id.temperature);
        Description = findViewById(R.id.Description);
        afficher();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.recherche, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView =(SearchView)menuItem.getActionView();
        searchView.setQueryHint("ecrire le nom de la ville");
        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                maVille = query;
                afficher();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    public void afficher() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + maVille + "&appid=74ba4b0d6163b7741666f4e8d4d845fa&units=metric";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {

            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    Log.d("tag", "resultat = " + array);
                    Log.d("tag", "resultat  température= " + main_object);
                    JSONObject object = array.getJSONObject(0);
                    int tempC = (int) Math.round(main_object.getDouble("temp"));
                    String temp = String.valueOf(tempC);

                    String d = object.getString("description");
                    String c = response.getString("name");
                    String i = object.getString("icon");

                    city.setText(c);
                    temperature.setText(temp);
                    Description.setText(d);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM, dd");
                    String formatted_date = simpleDateFormat.format(calendar.getTime());

                    date.setText(formatted_date);

                    String imageurl = "http://openweathermap.org/img/w/" + i + ".png";

                    imageView5 = findViewById(R.id.imageView5);

                    Uri myUri = Uri.parse(imageurl);

                    Picasso.with(MainActivity.this).load(myUri).resize(200, 200).into(imageView5);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }

        });
        // RequesQueue queue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}