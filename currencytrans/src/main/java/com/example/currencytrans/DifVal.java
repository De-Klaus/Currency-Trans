package com.example.currencytrans;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class DifVal extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private String city;
    private String val_1;
    private String val_2;
    private String val_3;
    private String val_1n;
    private String val_2n;
    private String val_3n;

    private String feast;
    private String second;

    private int feast_int;
    private int second_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dif_val);


        Button back = findViewById(R.id.back);
        back.setOnClickListener(this);

        Spinner spinner = findViewById(R.id.sp1);
        spinner.setOnItemSelectedListener(this);

        Spinner spinners = findViewById(R.id.sp2);
        spinners.setOnItemSelectedListener(this);

        Bundle args = getIntent().getExtras();
        if(args != null){

            city = args.getString("city");
            val_1 = args.getString("val_1");
            val_2 = args.getString("val_2");
            val_3 = args.getString("val_3");
            val_1n = args.getString("val_1n");
            val_2n = args.getString("val_2n");
            val_3n = args.getString("val_3n");

            feast = args.getString("feast");
            second = args.getString("second");

            feast_int = Integer.parseInt(args.getString("feast_int"));
            second_int = Integer.parseInt(args.getString("second_int"));

            ((Spinner) findViewById(R.id.sp1)).setSelection(feast_int);
            ((Spinner) findViewById(R.id.sp2)).setSelection(second_int);
        }

        TextView tvs = findViewById(R.id.tvs);
        tvs.setText(feast+" -> "+second);

    }



    @Override
    public void onClick(View v) {

        Intent intent = new Intent(DifVal.this, MainActivity.class);

        intent.putExtra("city", city);
        intent.putExtra("val_1", val_1);
        intent.putExtra("val_2",  val_2);
        intent.putExtra("val_3", val_3);
        intent.putExtra("val_1n", val_1n);
        intent.putExtra("val_2n", val_2n);
        intent.putExtra("val_3n", val_3n);

        intent.putExtra("feast", feast);
        intent.putExtra("second", second);
        intent.putExtra("feast_int", feast_int+"");
        intent.putExtra("second_int", second_int+"");
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp1: {
                feast = parent.getItemAtPosition(position).toString().substring(0, 3);
                feast_int = position;
                //System.out.println(feast);
                break;
            }
            case R.id.sp2: {
                second = parent.getItemAtPosition(position).toString().substring(0, 3);
                second_int = position;
                //System.out.println(second);
                ((Spinner) findViewById(R.id.sp1)).setSelection(feast_int);
                ((Spinner) findViewById(R.id.sp2)).setSelection(second_int);
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
