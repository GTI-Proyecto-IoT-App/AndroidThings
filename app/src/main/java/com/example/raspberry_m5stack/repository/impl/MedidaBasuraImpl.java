package com.example.raspberry_m5stack.repository.impl;

import com.example.raspberry_m5stack.firebase.FirebaseReferences;
import com.example.raspberry_m5stack.firebase.FirebaseRepository;
import com.example.raspberry_m5stack.firebase.callback.CallBack;
import com.example.raspberry_m5stack.model.MedidaBasura;
import com.example.raspberry_m5stack.repository.MedidaBasuraRepository;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import static com.example.raspberry_m5stack.firebase.constants.Constant.FAIL;
import static com.example.raspberry_m5stack.firebase.constants.Constant.SUCCESS;
import static com.example.raspberry_m5stack.firebase.constants.FirebaseConstants.CAMPO_MESURA;
import static com.example.raspberry_m5stack.firebase.constants.FirebaseConstants.TABLA_DISPOSITIVOS;


public class MedidaBasuraImpl extends FirebaseRepository implements MedidaBasuraRepository {

    private CollectionReference medidaBasuraCollectionReference;

    public MedidaBasuraImpl() {
        medidaBasuraCollectionReference = FirebaseReferences.getInstancia().getDATABASE().collection(TABLA_DISPOSITIVOS);
    }

    @Override
    public void crearMedidaBasura(String id, MedidaBasura medidaBasura, final CallBack callback) {
        // cogemos el key del document

        if(medidaBasura!=null){
            DocumentReference documentReference = medidaBasuraCollectionReference.document(id);


            // intentara crear la medida y al terminar llamará al callback de on succes o error
            fireStoreUpdateHasMap(documentReference, CAMPO_MESURA, medidaBasura, new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    callback.onSuccess(SUCCESS);
                }

                @Override
                public void onError(Object object) {
                    callback.onError(object);
                }
            });
        }else{
            callback.onError(FAIL);
        }
    }
}
