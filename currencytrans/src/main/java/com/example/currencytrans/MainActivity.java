package com.example.currencytrans;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<String> beverages;
    private ArrayAdapter<String> adapter;

    private SharedPreferences settings;
    private final String SETTINGS_NAME = "settings";
    private CurrentTimeTask currentTimeTask;
    private WeatherTask weatherTask;
    private GettingCost gettingCost;
    private TextView tv2;

    private String city;
    private String val_1;
    private String val_2;
    private String val_3;

    private String val_1n;
    private String val_2n;
    private String val_3n;

    private String feast;
    private String second;
    private String feast_int;
    private String second_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beverages = new ArrayList();
        adapter = new ArrayAdapter(this, R.layout.specifics, beverages);

        tv2 = findViewById(R.id.tv2);

        TextView tv1 = findViewById(R.id.tv1);
        SimpleDateFormat sdf = new SimpleDateFormat(Const.FORMAT_DATESTAT);
        String date = sdf.format(new Date());
        tv1.setText(date+"");

        Button but = findViewById(R.id.button);
        but.setOnClickListener(this);

        ImageView imageView = findViewById(R.id.iv);
        imageView.setOnClickListener(this);

        loadSettings();

        startWeatherTask();

        startGettingCost();

        startMatch();
    }

    private void loadSettings(){
        settings = getSharedPreferences(SETTINGS_NAME, MODE_PRIVATE);
        city = settings.getString("city", "Novosibirsk");

        val_1 = settings.getString("val_1","USD");
        val_2 = settings.getString("val_2", "EUR");
        val_3 = settings.getString("val_3", "KZT");

        val_1n = settings.getString("val_1n", "10");
        val_2n = settings.getString("val_2n", "11");
        val_3n = settings.getString("val_3n", "13");

        feast = settings.getString("feast", "USD");
        second = settings.getString("second", "RUB");
        feast_int = settings.getString("feast_int", "10");
        second_int = settings.getString("second_int", "34");

        System.out.println("val - "+val_1+" vall - "+val_2+" valll - "+val_3+"  "+city+"  "+val_1n+"  "+val_2n+"  "+val_3n+" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void saveSattings(){
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();

        editor.putString("city", city);

        editor.putString("val_1", val_1);
        editor.putString("val_2", val_2);
        editor.putString("val_3", val_3);
        editor.putString("val_1n", val_1n);
        editor.putString("val_2n", val_2n);
        editor.putString("val_3n", val_3n);
        editor.putString("feast", feast);
        editor.putString("second", second);
        editor.putString("feast_int", feast_int);
        editor.putString("second_int", second_int);
        editor.commit();
        editor.apply();
    }

    private void startCurrentTimeTask(){
        if (currentTimeTask != null) currentTimeTask.setStop(true);
        currentTimeTask = new CurrentTimeTask((TextView) findViewById(R.id.tv3));
        currentTimeTask.execute();
    }

    private void startWeatherTask(){
        Bundle args = getIntent().getExtras();
        if(args != null){
            city = args.getString("city");
            saveSattings();
        }
        weatherTask = new WeatherTask((TextView) findViewById(R.id.tv2),city);
        weatherTask.execute();
    }

    private void startGettingCost(){
        Bundle args = getIntent().getExtras();
        if(args != null){
            val_1 = args.getString("val_1");
            val_2 = args.getString("val_2");
            val_3 = args.getString("val_3");

            val_1n = args.getString("val_1n");
            val_2n = args.getString("val_2n");
            val_3n = args.getString("val_3n");
            saveSattings();
        }
        gettingCost = new GettingCost((TextView) findViewById(R.id.tv4),val_1);
        //System.out.println(val_1+"?????????????????????????????????????????????????????????????????????????????????????");
        gettingCost.execute();
        gettingCost = new GettingCost((TextView) findViewById(R.id.tv6),val_2);
        //System.out.println(val_2+"?????????????????????????????????????????????????????????????????????????????????????");
        gettingCost.execute();
        gettingCost = new GettingCost((TextView) findViewById(R.id.tv5),val_3);
        //System.out.println(val_3+"?????????????????????????????????????????????????????????????????????????????????????");
        gettingCost.execute();
    }

    private void startMatch(){

        /*gettingCost = new GettingCost((TextView) findViewById(R.id.tv9),feast);
        gettingCost.execute();
        gettingCost = new GettingCost((TextView) findViewById(R.id.tv10),second);
        gettingCost.execute();*/

        Bundle args = getIntent().getExtras();
        if(args != null) {
            city = args.getString("city");

            val_1 = args.getString("val_1");
            val_2 = args.getString("val_2");
            val_3 = args.getString("val_3");

            val_1n = args.getString("val_1n");
            val_2n = args.getString("val_2n");
            val_3n = args.getString("val_3n");

            feast = args.getString("feast");
            second = args.getString("second");

            feast_int = args.getString("feast_int");
            second_int = args.getString("second_int");
            saveSattings();
        }

        gettingCost = new GettingCost((TextView) findViewById(R.id.tv9),feast);
        gettingCost.execute();
        gettingCost = new GettingCost((TextView) findViewById(R.id.tv10),second);
        gettingCost.execute();

            if(feast.equals("AUD"))((TextView) findViewById(R.id.tv8)).setText("AU$");
            if(feast.equals("AZN"))((TextView) findViewById(R.id.tv8)).setText("\u20BC");
            if(feast.equals("GBP"))((TextView) findViewById(R.id.tv8)).setText("£");
            if(feast.equals("AMD"))((TextView) findViewById(R.id.tv8)).setText("֏");
            if(feast.equals("BYN"))((TextView) findViewById(R.id.tv8)).setText("Br");
            if(feast.equals("BGN"))((TextView) findViewById(R.id.tv8)).setText("лв");
            if(feast.equals("BRL"))((TextView) findViewById(R.id.tv8)).setText("R$");
            if(feast.equals("HUF"))((TextView) findViewById(R.id.tv8)).setText("Ft");
            if(feast.equals("HKD"))((TextView) findViewById(R.id.tv8)).setText("HK$");
            if(feast.equals("DKK"))((TextView) findViewById(R.id.tv8)).setText("Kr");
            if(feast.equals("USD"))((TextView) findViewById(R.id.tv8)).setText("$");
            if(feast.equals("EUR"))((TextView) findViewById(R.id.tv8)).setText("€");
            if(feast.equals("INR"))((TextView) findViewById(R.id.tv8)).setText("र");
            if(feast.equals("KZT"))((TextView) findViewById(R.id.tv8)).setText("₸");
            if(feast.equals("CAD"))((TextView) findViewById(R.id.tv8)).setText("C$");
            if(feast.equals("KGS"))((TextView) findViewById(R.id.tv8)).setText("с");
            if(feast.equals("CNY"))((TextView) findViewById(R.id.tv8)).setText("원");
            if(feast.equals("MDL"))((TextView) findViewById(R.id.tv8)).setText("L");
            if(feast.equals("NOK"))((TextView) findViewById(R.id.tv8)).setText("kr");
            if(feast.equals("PLN"))((TextView) findViewById(R.id.tv8)).setText("zł");
            if(feast.equals("RON"))((TextView) findViewById(R.id.tv8)).setText("L");
            if(feast.equals("XDR"))((TextView) findViewById(R.id.tv8)).setText("SDR");
            if(feast.equals("SGD"))((TextView) findViewById(R.id.tv8)).setText("S$");
            if(feast.equals("TJS"))((TextView) findViewById(R.id.tv8)).setText("с");
            if(feast.equals("TRY"))((TextView) findViewById(R.id.tv8)).setText("₺");
            if(feast.equals("TMT"))((TextView) findViewById(R.id.tv8)).setText("T");
            if(feast.equals("UZS"))((TextView) findViewById(R.id.tv8)).setText("So'm");
            if(feast.equals("UAH"))((TextView) findViewById(R.id.tv8)).setText("₴");
            if(feast.equals("CZK"))((TextView) findViewById(R.id.tv8)).setText("Kč");
            if(feast.equals("SEK"))((TextView) findViewById(R.id.tv8)).setText("kr");
            if(feast.equals("CHF"))((TextView) findViewById(R.id.tv8)).setText("₣");
            if(feast.equals("ZAR"))((TextView) findViewById(R.id.tv8)).setText("R");
            if(feast.equals("KRW"))((TextView) findViewById(R.id.tv8)).setText("₩");
            if(feast.equals("JPY"))((TextView) findViewById(R.id.tv8)).setText("¥");
            if(feast.equals("RUB"))((TextView) findViewById(R.id.tv8)).setText("\u20BD");

            ((TextView) findViewById(R.id.button)).setText(feast+" -> "+second);
    }

    protected void onPause() {
        super.onPause();
        currentTimeTask.setStop(true);
    }

    protected void onResume() {
        super.onResume();
        startCurrentTimeTask();
    }

    private void onCalculate(){
        String summ = ((EditText) findViewById(R.id.tv7)).getText().toString();
        if(((EditText) findViewById(R.id.tv7)).getText().toString().equals("")) Toast.makeText(this, "Вы не указали колличество валюты", Toast.LENGTH_SHORT).show();
        else {
            System.out.println(summ + " --5555555555555555555555555++++++++++++++++++++++++++5555555555555555555");
            String starts = ((TextView) findViewById(R.id.tv9)).getText().toString().substring(3);
            System.out.println(starts + " --44444444444444444444444444444++++++++++++++++++++++++++44444444444444444444444444444");
            String seconds = ((TextView) findViewById(R.id.tv10)).getText().toString().substring(3);
            System.out.println(seconds + " --333333333333333333333333333++++++++++++++++++++++++++333333333333333333333333333");

            double calculate = Double.parseDouble(summ) * Double.parseDouble(starts) / Double.parseDouble(seconds);
            String point = (((double) Math.round(calculate * 100)) / 100) + "";

            System.out.println(summ);
            System.out.println(point);

            adapter.add(summ + ".0 " + feast + " -> " + point + " " + second);
            //adapter.notifyDataSetChanged();

            ListView lv = findViewById(R.id.lv);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:{
                Intent intent = new Intent(this, DifVal.class);
                intent.putExtra("city", city);
                intent.putExtra("val_1", val_1);
                intent.putExtra("val_2", val_2);
                intent.putExtra("val_3", val_3);
                intent.putExtra("val_1n", val_1n);
                intent.putExtra("val_2n", val_2n);
                intent.putExtra("val_3n", val_3n);

                intent.putExtra("feast", feast);
                intent.putExtra("second", second);
                intent.putExtra("feast_int", feast_int);
                intent.putExtra("second_int", second_int);
                startActivity(intent);
                break;
            }
            case R.id.iv:{
                onCalculate();

                break;
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getTitle().equals("Настройки")){
            Intent intent = new Intent(this, Sattings.class);
            intent.putExtra("city", city);

            intent.putExtra("val_1", val_1);
            intent.putExtra("val_2", val_2);
            intent.putExtra("val_3", val_3);

            intent.putExtra("val_1n", val_1n);
            intent.putExtra("val_2n", val_2n);
            intent.putExtra("val_3n", val_3n);

            intent.putExtra("feast", feast);
            intent.putExtra("second", second);
            intent.putExtra("feast_int", feast_int);
            intent.putExtra("second_int", second_int);

            startActivity(intent);
        }
        if(item.getTitle().equals("Фоновая картинка")){
System.out.println("Фоновая картинка");
            Toast.makeText(this, "Фоновая картинка уже установлена, разработчик вас опередил :)", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
