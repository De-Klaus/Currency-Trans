package com.example.currencytrans;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherTask extends AsyncTask<Void, Void, String> {

    private String key = "2e3592e13c514adeb0950bcfb6c3d301";
    private String city;
    private TextView tv3;
    private String temps;


    public WeatherTask(TextView tv3, String city){
        this.city = city;
        this.tv3 = tv3;
        //System.out.println(city+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    protected String doInBackground(Void... voids) {
        //String content = getContent("https://api.openweathermap.org/data/2.5/weather?q=Novosibirsk&appid=2e3592e13c514adeb0950bcfb6c3d301");
        String content = getContent("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+key);
        return content;
    }

    protected void onPostExecute(String content) {
        try {
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
            JSONObject json = new JSONObject(content);
            double temp = json.getJSONObject("main").getDouble("temp")-273;
            temps = Math.round(temp) + " \u2103";
            System.out.println(temps);
            tv3.setText(temps);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getContent(String path){
        try {
            URL url = new URL(path);
            //HttpsURLConnection c = (HttpsURLConnection) url.openConnection();
            HttpsURLConnection c = (HttpsURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(20000);
            c.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            String contents = "";
            String line = "";
            while ((line = reader.readLine()) != null) {
                contents += line + "\n";
            }
            System.out.println(contents);
            return contents;
    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
            return "";
    }

}
