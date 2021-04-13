package com.nilesh.weatheralertandforecasting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
TextView textView;
TextView cit;
TextView max;
    TextView min;
    TextView PRESS;
    TextView wea;
    TextView hour;
    TextView la;
    TextView lo;
    TextView wea2;
    TextView night;
    TextView day;
    TextView night3;
    TextView day3;
EditText et;
    String lat;
    String lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        et=findViewById(R.id.location);
        cit=findViewById(R.id.city);
        max=findViewById(R.id.Max);
        min=findViewById(R.id.Min);
        PRESS=findViewById(R.id.PRESS);
        hour=findViewById(R.id.hour);
        wea=findViewById(R.id.weather);
        la=findViewById(R.id.lat);
        lo=findViewById(R.id.lon);
        wea2=findViewById(R.id.wea2);
        day=findViewById(R.id.day);
        night=findViewById(R.id.night);
        day3=findViewById(R.id.day3);
        night3=findViewById(R.id.night3);
    }
    public void about(View vv){
        Intent intent = new Intent(this, Aboutus.class);
        startActivity(intent);
    }
    public void get(View V){

        String apiKey="0074f0b05461c854b54c6fcf89bef764";
        String city=et.getText().toString();
        String url="https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid=0074f0b05461c854b54c6fcf89bef764";
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject object = response.getJSONObject("main");
                    String temprature = object.getString("temp");
                    String tempmax = object.getString("temp_max");
                    String tempmin = object.getString("temp_min");
                    String press = object.getString("pressure");
                    int pres=Integer.valueOf(press);
                    if(pres>1000 && pres<1010){
                        Toast.makeText(MainActivity.this, "Cloudy Weather & Very Small Rain Possibilty", Toast.LENGTH_SHORT).show();
                    }
                    else if(pres>1010 && pres<1023){
                        Toast.makeText(MainActivity.this, "Cloudy Weather & High Possibilty Of Rain OR Snow", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Normal Weather", Toast.LENGTH_SHORT).show();
                    }
                    cit.setText(city);
                    max.setText(tempmax+"  °C");
                    min.setText(tempmin+"  °C");
                    PRESS.setText(press +"  mb");
                    textView.setText(temprature +"  °C");
                    JSONArray objec = response.getJSONArray("weather");
                    String clou=objec.getString(0);
                    String cloud=clou.substring(18,24);
                    wea.setText(cloud);
                    JSONObject object1 = response.getJSONObject("coord");
                     String lat =object1.getString("lat");
                    String log=object1.getString("lon");

                    lo.setText(lat);

                    la.setText(log);
                    lat=lo.getText().toString();
                     lon=la.getText().toString();
                    String url2="https://api.openweathermap.org/data/2.5/onecall?lat="+lat+"&lon="+lon+"&units=metric&exclude=hourly,minutely&appid=0074f0b05461c854b54c6fcf89bef764";

                    RequestQueue queue2= Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest request1=new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                JSONObject object3 = response.getJSONObject("current");
                                String te = object3.getString("temp");

                                hour.setText(":"+te+" °C");
                                JSONArray object5 = response.getJSONArray("daily");
                                String clou=object5.getString(0);
                                String day1=clou.substring(71,78);
                                String night1=clou.substring(83,86);
                                String night2=clou.substring(134,139);
                                String day2=clou.substring(121,127);





                                wea2.setText("Night "+night1+" °C");
                                day.setText("Day "+day1+" °C");
                                night.setText("Night "+night1+" °C");
                                day3.setText("Day "+day2+" °C");
                                night3.setText("Night "+night2+" °C");


                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                            error -> Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show());

                    queue2.add(request1);


                } catch (JSONException e1) {
                    Toast.makeText(getApplicationContext(), e1.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);




    }

}