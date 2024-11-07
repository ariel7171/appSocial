package com.example.proyectointegrador.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectointegrador.providers.AuthProvider;

public class MainViewModel extends ViewModel {
    public final AuthProvider authProvider;

    public MainViewModel() {
        this.authProvider = new AuthProvider();
    }

    public LiveData<Boolean> login(String email, String password) {
        MutableLiveData<Boolean> loginresult = new MutableLiveData<>();
        authProvider.signIn(email, password).observeForever(userId -> {
            loginresult.setValue(userId != null);
        });
        return loginresult;
    }
}
