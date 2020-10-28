package com.example.raspberry_m5stack.repository;

import com.example.raspberry_m5stack.firebase.callback.CallBack;
import com.example.raspberry_m5stack.model.MedidaBasura;

public interface MedidaBasuraRepository {
    void crearMedidaBasura(String id, MedidaBasura medidaBasura, CallBack callback);
}
