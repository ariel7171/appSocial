package com.example.proyectointegrador.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectointegrador.R;
import com.example.proyectointegrador.databinding.ActivityRegisterBinding;
import com.example.proyectointegrador.model.User;
import com.example.proyectointegrador.util.Validaciones;
import com.example.proyectointegrador.viewModel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        manejarEventos();
    }

    private void manejarEventos() {
        // Evento volver a login
        binding.circuloBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Evento de registro
        binding.btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarRegistro();
            }
        });
    }

    private void realizarRegistro() {
        String usuario = binding.etUsuario.getText().toString().trim();
        String email = binding.etCorreo.getText().toString().trim();
        String pass = binding.etPass.getText().toString().trim();
        String pass1 = binding.etConfirmPass.getText().toString().trim();

        if (!Validaciones.validarUsuario(usuario)) {
            showToast("Usuario incorrecto");
            return;
        }

        if (!Validaciones.validarMail(email)) {
            showToast("El correo no es válido");
            return;
        }

        String passError = Validaciones.validarPass(pass, pass1);
        if (passError != null) {
            showToast(passError);
            return;
        }

        User user = new User(usuario, email, pass);
        viewModel.register(user).observe(this, result -> {
            showToast(result);
        });
    }

    private void showToast(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
        finish();
    }
}