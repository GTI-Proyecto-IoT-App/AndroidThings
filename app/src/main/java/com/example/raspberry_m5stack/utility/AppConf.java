package com.example.raspberry_m5stack.utility;

import android.app.Application;

import com.example.raspberry_m5stack.model.MedidaBasura;

public class AppConf extends Application {

    private MedidaBasura medidaBasura;

    public MedidaBasura getMedidaBasura() {
        return medidaBasura;
    }

    public void setMedidaBasura(MedidaBasura medidaBasura) {
        this.medidaBasura = medidaBasura;
    }
}
