package com.example.raspberry_m5stack.firebase;


import com.google.firebase.firestore.FirebaseFirestore;

/**
 * @author Ruben Pardo Casanova
 * contiene la referencia a nuestra base de datos.
 */
public class FirebaseReferences {

    private static FirebaseReferences INSTANCIA = new FirebaseReferences();

    private final FirebaseFirestore DATABASE = FirebaseFirestore.getInstance();

    public static FirebaseReferences getInstancia() {
        return INSTANCIA;
    }


    private FirebaseReferences() {
    }

    public FirebaseFirestore getDATABASE() {
        return DATABASE;
    }

}
