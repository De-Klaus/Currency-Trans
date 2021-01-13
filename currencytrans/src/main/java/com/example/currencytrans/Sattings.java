package com.example.currencytrans;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Sattings extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private String feast;
    private String second;
    private String feast_int;
    private String second_int;

    private String city;
    private String variant;

    private String val_1;
    private String val_2;
    private String val_3;


    private int val_1n;
    private int val_2n;
    private int val_3n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sattings);

        Bundle args = getIntent().getExtras();
        if(args != null){
            ((EditText) findViewById(R.id.city)).setText(args.getString("city"));
            feast = args.getString("feast");
            second = args.getString("second");
            feast_int = args.getString("feast_int");
            second_int = args.getString("second_int");

            val_1n = Integer.parseInt(args.getString("val_1n"));
            val_2n = Integer.parseInt(args.getString("val_2n"));
            val_3n = Integer.parseInt(args.getString("val_3n"));

            ((Spinner) findViewById(R.id.spinner1)).setSelection(val_1n);
            ((Spinner) findViewById(R.id.spinner2)).setSelection(val_2n);
            ((Spinner) findViewById(R.id.spinner3)).setSelection(val_3n);
        }

        Spinner spinner = findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);

        Spinner spinne = findViewById(R.id.spinner2);
        spinne.setOnItemSelectedListener(this);

        Spinner spinn = findViewById(R.id.spinner3);
        spinn.setOnItemSelectedListener(this);

        Button save = findViewById(R.id.save);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save: {
                city = ((EditText) findViewById(R.id.city)).getText().toString();
                variant = "0";

                for (int i = 0; i < city.length(); i++) {
                    String simvol = city.charAt(i) + "";
                    if (simvol.matches("\\d")) {
                        //Toast.makeText(this, "Уберите цифры из названия города", Toast.LENGTH_SHORT).show();
                        variant = "5";
                    }
                }


                        //if(!city.matches("[a-zA-Z]+")) Toast.makeText(this, "Название города не соедержит цифр", Toast.LENGTH_SHORT).show();

                    //if (variant.equals("6")) {
                    if (variant.equals("5")) {
                        Toast.makeText(this, "Уберите цифры из названия города", Toast.LENGTH_SHORT).show();
                    }
                    else  {
                        Intent intent = new Intent(Sattings.this, MainActivity.class);
                        intent.putExtra("city", city);
                        intent.putExtra("val_1", val_1);
                        intent.putExtra("val_2", val_2);
                        intent.putExtra("val_3", val_3);

                        intent.putExtra("val_1n", val_1n + "");
                        intent.putExtra("val_2n", val_2n + "");
                        intent.putExtra("val_3n", val_3n + "");

                        intent.putExtra("feast", feast);
                        intent.putExtra("second", second);
                        intent.putExtra("feast_int", feast_int);
                        intent.putExtra("second_int", second_int);
                        startActivity(intent);

                    }
                }
            }
        }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner1: {
                val_1 = parent.getItemAtPosition(position).toString().substring(0, 3);
                val_1n = position;
                System.out.println(val_1);
                break;
            }
            case R.id.spinner2: {
                val_2 = parent.getItemAtPosition(position).toString().substring(0, 3);
                val_2n = position;
                System.out.println(val_2);
                break;
            }
            case R.id.spinner3: {
                val_3 = parent.getItemAtPosition(position).toString().substring(0, 3);
                val_3n = position;
                System.out.println(val_3);
                break;
            }
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
