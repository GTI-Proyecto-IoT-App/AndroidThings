package com.example.androidthings.assistant.repository.impl;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.androidthings.assistant.firebase.FirebaseReferences;
import com.example.androidthings.assistant.firebase.FirebaseRepository;
import com.example.androidthings.assistant.firebase.callback.CallBack;
import com.example.androidthings.assistant.model.MedidaBasura;
import com.example.androidthings.assistant.repository.MedidaBasuraRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.androidthings.assistant.firebase.constants.Constant.FAIL;
import static com.example.androidthings.assistant.firebase.constants.Constant.SUCCESS;
import static com.example.androidthings.assistant.firebase.constants.FirebaseConstants.TABLA_DISPOSITIVOS;


public class MedidaBasuraImpl extends FirebaseRepository implements MedidaBasuraRepository {

    private CollectionReference medidaBasuraCollectionReference;

    public MedidaBasuraImpl() {
        medidaBasuraCollectionReference = FirebaseReferences.getInstancia().getDATABASE().collection(TABLA_DISPOSITIVOS);
    }

    @Override
    public void crearMedidaBasura(String id, MedidaBasura medidaBasura, final CallBack callback) {
        // cogemos el key del document

        if(medidaBasura!=null){
            medidaBasuraCollectionReference.document(id).collection("mediciones").add(medidaBasura)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    callback.onSuccess(SUCCESS);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    callback.onError(null);
                }
            });
            //DocumentReference documentReference = medidaBasuraCollectionReference.document(id);


            // intentara crear la medida y al terminar llamará al callback de on succes o error

            /*fireStoreUpdateHasMap(documentReference, CAMPO_MESURA, medidaBasura, new CallBack() {
                @Override
                public void onSuccess(Object object) {
                    callback.onSuccess(SUCCESS);
                }

                @Override
                public void onError(Object object) {
                    callback.onError(object);
                }
            });*/
        }else{
            callback.onError(FAIL);
        }
    }

    // devuelve el tanto por ciento de los 4 contenedores (max 400%)
    @Override
    public void readLLenadoBasura(String id, final CallBack callBack) {
        final int[] contMesuras = {4};

        final double[] llenadoTotal = {0};

        final Query queryPlastico = medidaBasuraCollectionReference.document(id).collection("mediciones")
                .whereEqualTo("tipoMedida","plastico")
                .orderBy("unixTime", Query.Direction.DESCENDING)
                .limit(1);

        final Query queryVidrio = medidaBasuraCollectionReference.document(id).collection("mediciones")
                .whereEqualTo("tipoMedida","vidrio")
                .orderBy("unixTime", Query.Direction.DESCENDING)
                .limit(1);

        final Query queryPapel = medidaBasuraCollectionReference.document(id).collection("mediciones")
                .whereEqualTo("tipoMedida","papel")
                .orderBy("unixTime", Query.Direction.DESCENDING)
                .limit(1);

        final Query queryOrganico = medidaBasuraCollectionReference.document(id).collection("mediciones")
                .whereEqualTo("tipoMedida","organico")
                .orderBy("unixTime", Query.Direction.DESCENDING)
                .limit(1);


        queryPapel.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    contMesuras[0]--;

                    if(task.getResult()!=null){
                        if(!task.getResult().getDocuments().isEmpty()){
                            MedidaBasura m = task.getResult().getDocuments().get(0).toObject(MedidaBasura.class);
                            Log.d("Escribir",m.getTipoMedida()+": "+m.getLlenado());

                            if(m!=null){
                                llenadoTotal[0] += m.getLlenado();
                            }
                        }

                    }


                    if(contMesuras[0] == 0){
                        callBack.onSuccess(llenadoTotal[0]);
                    }

                }
            }
        });

        queryOrganico.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    contMesuras[0]--;

                    if(task.getResult()!=null){
                        if(!task.getResult().getDocuments().isEmpty()){
                            MedidaBasura m = task.getResult().getDocuments().get(0).toObject(MedidaBasura.class);
                            Log.d("Escribir",m.getTipoMedida()+": "+m.getLlenado());
                            if(m!=null){
                                llenadoTotal[0] += m.getLlenado();
                            }
                        }

                    }


                    if(contMesuras[0] == 0){
                        callBack.onSuccess(llenadoTotal[0]);
                    }

                }
            }
        });

        queryVidrio.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    contMesuras[0]--;

                    if(task.getResult()!=null){
                        if(!task.getResult().getDocuments().isEmpty()){
                            MedidaBasura m = task.getResult().getDocuments().get(0).toObject(MedidaBasura.class);
                            Log.d("Escribir",m.getTipoMedida()+": "+m.getLlenado());
                            if(m!=null){
                                llenadoTotal[0] += m.getLlenado();
                            }
                        }

                    }


                    if(contMesuras[0] == 0){
                        callBack.onSuccess(llenadoTotal[0]);
                    }

                }
            }
        });

        queryPlastico.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    contMesuras[0]--;

                    if(task.getResult()!=null){
                        if(!task.getResult().getDocuments().isEmpty()){
                            MedidaBasura m = task.getResult().getDocuments().get(0).toObject(MedidaBasura.class);
                            Log.d("Escribir",m.getTipoMedida()+": "+m.getLlenado());
                            if(m!=null){
                                llenadoTotal[0] += m.getLlenado();
                            }
                        }
                    }


                    if(contMesuras[0] == 0){
                        callBack.onSuccess(llenadoTotal[0]);
                    }

                }
            }
        });


    }
}
