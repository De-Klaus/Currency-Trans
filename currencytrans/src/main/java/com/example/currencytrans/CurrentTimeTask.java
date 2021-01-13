package com.example.currencytrans;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentTimeTask extends AsyncTask<Void, String, Void> {

    private TextView tv1;
    private boolean stop = false;

    public CurrentTimeTask(TextView tv1){

        this.tv1 = tv1;
    }

    public void setStop(boolean stop){
        this.stop = stop;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (!stop){
            SimpleDateFormat sdf = new SimpleDateFormat(Const.FORMAT_DATEDINAM);
            publishProgress(sdf.format(new Date()));
            SystemClock.sleep(1000);
        }
        return null;
    }

    protected void onProgressUpdate(String... values){
        super.onProgressUpdate(values);
        tv1.setText(values[0]);
    }


}
