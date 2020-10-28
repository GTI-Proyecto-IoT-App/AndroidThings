package com.example.raspberry_m5stack.datos.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.raspberry_m5stack.datos.firebase.callback.CallBack;
import com.example.raspberry_m5stack.datos.firebase.callback.FirebaseChildCallBack;
import com.example.raspberry_m5stack.datos.firebase.constants.Constant;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.Map;

/**
 * @author Ruben Pardo Casanova
 * Escribimos todos los oyentes de lectura y escritura
 * de Firebase Cloud Firestore en una clase para que sea fácil
 * de administrar y flexible para cambios futuros.
 */
public class FirebaseRepository {


    /**
     * Insert data on FireStore
     *
     * @param documentReference Document reference of data to be add
     * @param model             Model to insert into Document
     * @param callback          callback for event handling
     */
    protected final void fireStoreCreate(final DocumentReference documentReference, final Object model, final CallBack callback) {
        documentReference.set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess(Constant.SUCCESS);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e);
            }
        });
    }



    /**
     * FireStore Create or Merge
     *
     * @param documentReference Document reference of data to create update
     * @param model             Model to create or update into Document
     */
    protected final void fireStoreCreateOrMerge(final DocumentReference documentReference, final Object model, final CallBack callback) {
        documentReference.set(model, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess(Constant.SUCCESS);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e);
            }
        });
    }

    protected final void fireStoreUpdateHasMap(final DocumentReference documentReference,String field, final Object model, final CallBack callback) {
        documentReference.update(field, FieldValue.arrayUnion(model)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callback.onSuccess(Constant.SUCCESS);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError(e);
            }
        });
    }



    /**
     * FireStore Batch write
     *
     * @param batch    Document reference of data to delete
     * @param callback callback for event handling
     */
    protected final void batchWrite(WriteBatch batch, final CallBack callback) {
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    callback.onSuccess(Constant.SUCCESS);
                else
                    callback.onError(Constant.FAIL);
            }
        });
    }


}
