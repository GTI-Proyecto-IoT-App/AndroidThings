package com.example.androidthings.assistant.repository;

import com.example.androidthings.assistant.firebase.callback.CallBack;
import com.example.androidthings.assistant.model.MedidaBasura;

public interface MedidaBasuraRepository {
    void crearMedidaBasura(String id, MedidaBasura medidaBasura, CallBack callback);
    void readLLenadoBasura(String id, CallBack callBack);
}
