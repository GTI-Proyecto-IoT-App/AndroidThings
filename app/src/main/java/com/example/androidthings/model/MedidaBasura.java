package com.example.androidthings.model;

import org.json.JSONException;
import org.json.JSONObject;

public class MedidaBasura {
    //Id del dispositivo
    private String id;
    private String tipoMedida;
    //private String time;
    private double valor;

    public MedidaBasura(JSONObject obj) throws JSONException {
        this.id = obj.getString("id");
        this.tipoMedida = obj.getString("tipoMedida");
        this.valor = obj.getDouble("valor");
    }

    public MedidaBasura(String id, String tipoMedida, double valor) {
        this.id = id;
        this.tipoMedida = tipoMedida;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoMedida() {
        return tipoMedida;
    }

    public void setTipoMedida(String tipoMedida) {
        this.tipoMedida = tipoMedida;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
