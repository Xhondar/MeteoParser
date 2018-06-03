package com.example.adrian.meteoparser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView temp, citta, desc, dataO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temp = (TextView) findViewById(R.id.textView);
        citta = (TextView) findViewById(R.id.textView1);
        desc = (TextView) findViewById(R.id.textView2);
        dataO = (TextView) findViewById(R.id.textView3);

        find_weather();
    }

    public void find_weather(){
        String url = "http://samples.openweathermap.org/data/2.5/weather?q=Carpi,it&appid=b6907d289e10d714a6e88b30761fae22";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try
                {
                    JSONObject obj = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject obj2 = array.getJSONObject(0);

                    String temperature = String.valueOf(obj.getDouble("temp"));
                    String description = obj2.getString("description");
                    String city = response.getString("name");

                    temp.setText(temperature);
                    citta.setText(city);
                    desc.setText(description);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MM-dd");
                    String formatted_date = sdf.format(calendar.getTime());

                    dataO.setText(formatted_date);
                    double t_int = Double.parseDouble(temperature);
                    double centi = (t_int - 32) / 1.8000;
                    centi = Math.round(centi);
                    int i = (int)centi;
                    temp.setText(String.valueOf(i));



                }catch(JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);
    }
}
