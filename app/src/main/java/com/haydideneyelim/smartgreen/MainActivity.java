package com.haydideneyelim.smartgreen;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    static String reqRespond = null;
    OkHttpClient client = new OkHttpClient();
    TextView tvConsole, tvPower1, tvMains1, tvAlt1, tvSum1, tvCost1, tvPower2, tvMains2, tvAlt2, tvSum2, tvCost2, tvDay, tvTime, tvSource;
    ImageView im11, im12, im13, im21, im22, im23;
    Apartment ap1, ap2;
    int day, time, lastTime = -1;
    boolean isMains, consoleOn = false;

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 205;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvConsole = findViewById(R.id.textView);
        tvPower1 = findViewById(R.id.tvPower1);
        tvMains1 = findViewById(R.id.tvMains1);
        tvAlt1   = findViewById(R.id.tvAlt1);
        tvSum1   = findViewById(R.id.tvSum1);
        tvCost1  = findViewById(R.id.tvCost1);
        tvPower2 = findViewById(R.id.tvPower2);
        tvMains2 = findViewById(R.id.tvMains2);
        tvAlt2   = findViewById(R.id.tvAlt2);
        tvSum2   = findViewById(R.id.tvSum2);
        tvCost2  = findViewById(R.id.tvCost2);
        tvDay    = findViewById(R.id.tvDay);
        tvTime   = findViewById(R.id.tvTime);
        tvSource   = findViewById(R.id.tvSource);

        im11 = findViewById(R.id.imAp11);
        im12 = findViewById(R.id.imAp12);
        im13 = findViewById(R.id.imAp13);
        im21 = findViewById(R.id.imAp21);
        im22 = findViewById(R.id.imAp22);
        im23 = findViewById(R.id.imAp23);

        ap1 = new Apartment();
        ap2 = new Apartment();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    @Override
    protected  void onResume() {
        handler.postDelayed(runnable = new Runnable() {
            public void run(){
                update();
                handler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    public void button11 (View v) {
        if (ap1.isLampOpen) {
            request("http://192.168.1.200/A11CLOSE");
            im11.setImageResource(R.drawable.lamp0);
            ap1.isLampOpen = false;
        } else {
            request("http://192.168.1.200/A11OPEN");
            im11.setImageResource(R.drawable.lamp1);
            ap1.isLampOpen = true;
        }
        if (consoleOn) tvConsole.setText(reqRespond);
    }

    public void button12 (View v) {
        if (ap1.isWaterOpen) {
            request("http://192.168.1.200/A12CLOSE");
            im12.setImageResource(R.drawable.water0);
            ap1.isWaterOpen = false;
        } else {
            request("http://192.168.1.200/A12OPEN");
            im12.setImageResource(R.drawable.water1);
            ap1.isWaterOpen = true;
        }
        if (consoleOn) tvConsole.setText(reqRespond);
    }

    public void button13 (View v) {
        if (ap1.isTvOpen) {
            request("http://192.168.1.200/A13CLOSE");
            im13.setImageResource(R.drawable.tv0);
            ap1.isTvOpen = false;
        } else {
            request("http://192.168.1.200/A13OPEN");
            im13.setImageResource(R.drawable.tv1);
            ap1.isTvOpen = true;
        }
        if (consoleOn) tvConsole.setText(reqRespond);
    }

    public void button21 (View v) {
        if (ap2.isLampOpen) {
            request("http://192.168.1.200/A21CLOSE");
            im21.setImageResource(R.drawable.lamp0);
            ap2.isLampOpen = false;
        } else {
            request("http://192.168.1.200/A21OPEN");
            im21.setImageResource(R.drawable.lamp1);
            ap2.isLampOpen = true;
        }
        if (consoleOn) tvConsole.setText(reqRespond);
    }

    public void button22 (View v) {
        if (ap2.isWaterOpen) {
            request("http://192.168.1.200/A22CLOSE");
            im22.setImageResource(R.drawable.water0);
            ap2.isWaterOpen = false;
        } else {
            request("http://192.168.1.200/A22OPEN");
            im22.setImageResource(R.drawable.water1);
            ap2.isWaterOpen = true;
        }
        if (consoleOn) tvConsole.setText(reqRespond);
    }

    public void button23 (View v) {
        if (ap2.isTvOpen) {
            request("http://192.168.1.200/A23CLOSE");
            im23.setImageResource(R.drawable.tv0);
            ap2.isTvOpen = false;
        } else {
            request("http://192.168.1.200/A23OPEN");
            im23.setImageResource(R.drawable.tv1);
            ap2.isTvOpen = true;
        }
        if (consoleOn) tvConsole.setText(reqRespond);
    }

    synchronized void request(String url) {
        final Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        reqRespond = null;
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            reqRespond = response.body().string();
                        } catch (IOException e) {
                            reqRespond = null;
                        }
                    }
                });
            }
        });
    }

    /*
    {"ap11":"on",
    {"ap11":"off",

    "ap12":"on",
    "ap12":"off",

    "time":"1"
    "day":"1"

    "source":"alt"}
    "source":"mains"}
    */
    void update() {
        request("http://192.168.1.200/GETDATA");
        if (consoleOn) tvConsole.setText(reqRespond);

        if (reqRespond != null) {
            try {
                JSONObject jData = new JSONObject(reqRespond);

                day = jData.getInt("day");
                time = jData.getInt("time");
                isMains = jData.getBoolean("mains");

                ap1.isLampOpen = jData.getBoolean("ap11");
                ap1.isWaterOpen = jData.getBoolean("ap12");
                ap1.isTvOpen= jData.getBoolean("ap13");

                ap2.isLampOpen = jData.getBoolean("ap21");
                ap2.isWaterOpen = jData.getBoolean("ap22");
                ap2.isTvOpen= jData.getBoolean("ap23");

                ap1.updateCurrentPower();
                ap2.updateCurrentPower();

                if (lastTime != time) {
                    lastTime = time;
                    tvDay.setText(String.valueOf(day));
                    tvTime.setText(String.valueOf(time));
                    if(isMains) tvSource.setText("M");
                    else tvSource.setText("A");

                    if(day == 0 && time == 0) {
                        ap1.reset();
                        ap2.reset();
                    }

                    ap1.updateSums(isMains);
                    ap2.updateSums(isMains);

                    tvPower1.setText(String.format("%.2f kW", ap1.powerLast));
                    tvMains1.setText(String.format("%.2f kWh", ap1.mainsSum));
                    tvAlt1.setText(String.format("%.2f kWh", ap1.pvSum));
                    tvSum1.setText(String.format("%.2f kWh", ap1.mainsSum+ap1.pvSum));
                    tvCost1.setText(String.format("%.2f ₺", ap1.cost));

                    tvPower2.setText(String.format("%.2f kW", ap2.powerLast));
                    tvMains2.setText(String.format("%.2f kWh", ap2.mainsSum));
                    tvAlt2.setText(String.format("%.2f kWh", ap2.pvSum));
                    tvSum2.setText(String.format("%.2f kWh", ap2.mainsSum+ap2.pvSum));
                    tvCost2.setText(String.format("%.2f ₺", ap2.cost));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if (ap2.isLampOpen) im21.setImageResource(R.drawable.lamp1);
        else im21.setImageResource(R.drawable.lamp0);
        if (ap2.isWaterOpen) im22.setImageResource(R.drawable.water1);
        else im22.setImageResource(R.drawable.water0);
        if (ap2.isTvOpen) im23.setImageResource(R.drawable.tv1);
        else im23.setImageResource(R.drawable.tv0);
        if (ap1.isLampOpen) im11.setImageResource(R.drawable.lamp1);
        else im11.setImageResource(R.drawable.lamp0);
        if (ap1.isWaterOpen) im12.setImageResource(R.drawable.water1);
        else im12.setImageResource(R.drawable.water0);
        if (ap1.isTvOpen) im13.setImageResource(R.drawable.tv1);
        else im13.setImageResource(R.drawable.tv0);
    }
}
