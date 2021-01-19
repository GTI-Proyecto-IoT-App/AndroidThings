package com.example.raspberry_m5stack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.raspberry_m5stack.firebase.callback.CallBack;
import com.example.raspberry_m5stack.model.MedidaBasura;
import com.example.raspberry_m5stack.repository.impl.MedidaBasuraImpl;
import com.google.android.things.pio.PeripheralManager;
import com.google.gson.JsonArray;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONArray;
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
public class MainActivity extends AppCompatActivity implements MqttCallback {

    public static MqttClient client = null;
    private static final String TAG = MainActivity.class.getSimpleName();

    private MedidaBasuraImpl medidaBasuraRepository;
    private ArduinoUart uart;


    private static final String DELIMITADOR_TIPO_DISPOSITIVO = "%";
    private static final String DISPOSITIVO_BASURA = "basura";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        medidaBasuraRepository = new MedidaBasuraImpl();
        uart = new ArduinoUart("leds llenado",9600);

        try {
            Log.i(Mqtt.TAG, "Conectando al broker " + Mqtt.broker);
            client = new MqttClient(Mqtt.broker, Mqtt.clientId,
                    new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(60);
            connOpts.setWill(Mqtt.topicRoot+"WillTopic", "App desconectada".getBytes(),Mqtt.qos, false);
            client.connect(connOpts);

        } catch (MqttException e) {
            Log.e(Mqtt.TAG, "Error al conectar.", e);
        }

        try {
            Log.i(Mqtt.TAG, "Suscrito a " + Mqtt.topicRoot+"#");
            client.subscribe(Mqtt.topicRoot+"#", Mqtt.qos);
            client.setCallback(this);
        } catch (MqttException e) {
            Log.e(Mqtt.TAG, "Error al suscribir.", e);
        }

    }

        /*try {
            int i =0;
            while(true){
                ;
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

        String id = jsonObject.getString("id");
        JSONArray medidas = jsonObject.getJSONArray("medidas");
        for (int i = 0; i < medidas.length(); i++) {

            MedidaBasura medidasB = new MedidaBasura(medidas.getJSONObject(i));
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

    }

    @Override protected void onDestroy() {
        try {
            Log.i(Mqtt.TAG, "Desconectado");
            client.disconnect();
        } catch (MqttException e) {
            Log.e(Mqtt.TAG, "Error al desconectar.", e);
        }
        super.onDestroy();
    }


    @Override
    public void connectionLost(Throwable cause) {
        Log.d(Mqtt.TAG, "Conexión perdida");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        try {

            if(topic.contains("dispositivo")){
                JSONObject jsonObject = new JSONObject(message.toString());
                String tipoDispositivo = jsonObject.getString("id").split(DELIMITADOR_TIPO_DISPOSITIVO)[1];

                switch (tipoDispositivo){
                    case DISPOSITIVO_BASURA:
                        addMesuraBasura(jsonObject);
                        break;
                }
            }else if(topic.equals("proyectoGTI2A/sonoff/POWER")){
                obtenerLlenadoBasura();
            }



        }catch (JSONException err){
            Log.d("Error", err.toString());
        }
    }

    private void obtenerLlenadoBasura() {
        //Log.d(TAG, "Mandado a Arduino: D");

        medidaBasuraRepository.readLLenadoBasura("B4:E6:2D:97:B7:CD%basura", new CallBack() {
            @Override
            public void onSuccess(Object object) {
                uart.escribir(String.valueOf(object));
            }

            @Override
            public void onError(Object object) {

            }
        });
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.d(Mqtt.TAG, "Entrega completa");
    }

}



