package com.example.androidthings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.androidthings.model.MedidaBasura;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the PeripheralManager
 * For example, the snippet below will open a GPIO pin and set it to HIGH:
 * <p>
 * PeripheralManager manager = PeripheralManager.getInstance();
 * try {
 * Gpio gpio = manager.openGpio("BCM6");
 * gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * gpio.setValue(true);
 * } catch (IOException e) {
 * Log.e(TAG, "Unable to access GPIO");
 * }
 * <p>
 * You can find additional examples on GitHub: https://github.com/androidthings
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArduinoUart uart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Log.i("TESTING", "Lista de UART disponibles: " + ArduinoUart.disponibles());
        uart = new ArduinoUart("UART0", 115200);

        //Log.d(TAG, "Mandado a Arduino: D");
        //uart.escribir("D");


        try {
            int i =0;
            while(true){
                String s = uart.leer();
                String[] res = s.split("\\$\\$\\$\\$");
                if (res.length>1){
                    Log.d(TAG, "Recibido de Arduino -> "+i+": "+res[1]);
                    try {
                        MedidaBasura medidasB = new MedidaBasura(new JSONObject(res[1]));
                        Log.d(TAG, "id de Arduino -> "+medidasB.getId());
                        Log.d(TAG, "tipo  de Arduino -> "+medidasB.getTipoMedida());
                        Log.d(TAG, "valor de Arduino -> "+medidasB.getValor());
                    }catch (JSONException err){
                        Log.d("Error", err.toString());
                    }
                }
                //5 sengundos de espera para evitar duplicidad de datos
                Thread.sleep(5000);
                i++;
            }

        } catch (InterruptedException e) {
            Log.w(TAG, "Error en sleep()", e);
        }


    }


    @Override protected void onDestroy() {
        super.onDestroy();
    }
}

