package com.example.raspberry_m5stack.model;

import org.json.JSONException;
import org.json.JSONObject;

public class MedidaBasura {
    //Id del dispositivo
    private String tipoMedida;



    //private String time;
    private double llenado;
    private double peso;

    private long unixTime;

    public MedidaBasura(JSONObject obj) throws JSONException {
        this.tipoMedida = obj.getString("tipo");
        this.llenado = obj.getDouble("volumen");
        //this.llenado = obj.getDouble("peso");
        this.unixTime = System.currentTimeMillis();
    }

    public MedidaBasura(String tipoMedida, double llenado, double peso) {
        this.tipoMedida = tipoMedida;
        this.llenado = llenado;
        this.peso = peso;
        this.unixTime = System.currentTimeMillis();
    }



    public long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
    }

    public String getTipoMedida() {
        return tipoMedida;
    }

    public void setTipoMedida(String tipoMedida) {
        this.tipoMedida = tipoMedida;
    }

    public double getLlenado() {
        return llenado;
    }

    public void setLlenado(double llenado) {
        this.llenado = llenado;
    }

//    public double getPeso() {
//        return peso;
//    }
//
//    public void setPeso(double peso) {
//        this.peso = peso;
//    }

}
