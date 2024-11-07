package com.example.proyectointegrador.providers;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectointegrador.model.User;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProvider {
    private final FirebaseFirestore firestore;
    private static final String USERS_COLLECTION = "users";

    public UserProvider() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public LiveData<String> createUser(User user) {
        MutableLiveData<String> result = new MutableLiveData<>();
        firestore.collection(USERS_COLLECTION).document(user.getId()).set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        result.setValue("Usuario creado correctamente");
                    } else {
                        result.setValue("Error al crear el usuario");
                    }
                });
        return result;
    }


    public LiveData<User> getUser(String mail) {
        MutableLiveData<User> userData = new MutableLiveData<>();
        firestore.collection(USERS_COLLECTION)
                .whereEqualTo("email", mail)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        User user = document.toObject(User.class);
                        if (user != null) {
                            Log.d("UserProvider", "Usuario encontrado: " + user.getEmail());
                        } else {
                            Log.d("UserProvider", "Error: documento encontrado, pero usuario es null");
                        }
                        userData.setValue(user);
                    } else {
                        Log.d("UserProvider", "Usuario no encontrado en Firestore con email: " + mail);
                        userData.setValue(null);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("UserProvider", "Error en la consulta en Firestore: " + e);
                    userData.setValue(null);
                });
        return userData;
    }

    public LiveData<String> updateUser(User user) {
        MutableLiveData<String> result = new MutableLiveData<>();
        firestore.collection(USERS_COLLECTION).document(user.getId()).set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        result.setValue("Usuario actualizado correctamente");
                    } else {
                        result.setValue("Error al actualizar el usuario");
                    }
                });
        return result;
    }

    public LiveData<String> deleteUser(String userId) {
        MutableLiveData<String> result = new MutableLiveData<>();
        firestore.collection(USERS_COLLECTION).document(userId).delete()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        result.setValue("Usuario eliminado correctamente");
                    } else {
                        result.setValue("Error al eliminar el usuario");
                    }
                });
        return result;
    }

}
