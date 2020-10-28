package com.example.raspberry_m5stack.model;

import org.json.JSONException;
import org.json.JSONObject;

public class MedidaBasura {
    //Id del dispositivo
    private String tipoMedida;
    //private String time;
    private double valor;

    public MedidaBasura(JSONObject obj) throws JSONException {
        this.tipoMedida = obj.getString("tipoMedida");
        this.valor = obj.getDouble("valor");
    }

    public MedidaBasura(String tipoMedida, double valor) {
        this.tipoMedida = tipoMedida;
        this.valor = valor;
    }


    public String getTipoMedida() {
        return tipoMedida;
    }


    public double getValor() {
        return valor;
    }

}
