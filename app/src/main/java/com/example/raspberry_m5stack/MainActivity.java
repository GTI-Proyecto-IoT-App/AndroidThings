package com.example.raspberry_m5stack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.raspberry_m5stack.firebase.callback.CallBack;
import com.example.raspberry_m5stack.model.MedidaBasura;
import com.example.raspberry_m5stack.repository.impl.MedidaBasuraImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
    private MedidaBasuraImpl medidaBasuraRepository;


    private static final String DELIMITADOR_TIPO_DISPOSITIVO = "%";
    private static final String DELIMITADOR_MENSAJE_UART = "\\$\\$\\$\\$";
    private static final String DISPOSITIVO_BASURA = "basura";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Log.i("TESTING", "Lista de UART disponibles: " + ArduinoUart.disponibles());
        uart = new ArduinoUart("UART0", 115200);
        medidaBasuraRepository = new MedidaBasuraImpl();
        //Log.d(TAG, "Mandado a Arduino: D");
        //uart.escribir("D");


        try {
            int i =0;
            while(true){
                String s = uart.leer();
                String[] res = s.split(DELIMITADOR_MENSAJE_UART);
                if (res.length>1){
                    Log.d(TAG, "Recibido de Arduino -> "+i+": "+res[1]);
                    try {
                        JSONObject jsonObject = new JSONObject(res[1]);
                        String tipoDispositivo = jsonObject.getString("id").split(DELIMITADOR_TIPO_DISPOSITIVO)[1];

                        switch (tipoDispositivo){
                            case DISPOSITIVO_BASURA:
                                addMesuraBasura(jsonObject);
                                break;
                        }

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

    /**
     * Envia peticion de creacion de mesura de basura
     * @param jsonObject el json object que recibimos del m5 stack
     * @throws JSONException mal formateo del json
     */
    private void addMesuraBasura(JSONObject jsonObject) throws JSONException {
        MedidaBasura medidasB = new MedidaBasura(jsonObject);
        String id = jsonObject.getString("id");

        medidaBasuraRepository.crearMedidaBasura(id,medidasB, new CallBack() {
            @Override
            public void onSuccess(Object object) {
                Log.d(TAG,object.toString());
            }

            @Override
            public void onError(Object object) {
                Log.e(TAG,object.toString());
            }
        });
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }
}

