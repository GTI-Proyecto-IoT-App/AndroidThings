package com.example.androidthings.assistant.utility;

import android.app.Application;

import com.example.androidthings.assistant.model.MedidaBasura;

public class AppConf extends Application {

    private MedidaBasura medidaBasura;

    public MedidaBasura getMedidaBasura() {
        return medidaBasura;
    }

    public void setMedidaBasura(MedidaBasura medidaBasura) {
        this.medidaBasura = medidaBasura;
    }
}
