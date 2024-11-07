package com.example.proyectointegrador.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectointegrador.R;
import com.example.proyectointegrador.databinding.ActivityMainBinding;
import com.example.proyectointegrador.util.Validaciones;
import com.example.proyectointegrador.viewModel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        manejarEventos();

    }

    private void manejarEventos() {
        binding.tvRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.etCorreo.getText().toString().trim();
                String pass = binding.etPass.getText().toString().trim();

                if (!Validaciones.validarMail(email)) {
                    showToast("Email incorrecto");
                    return;
                }

                if (!Validaciones.controlarPasword(pass)) {
                    showToast("Password incorrecto");
                    return;
                }

                viewModel.login(email, pass).observe(MainActivity.this, loginSuccessful ->{
                    if (loginSuccessful) {
                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                        startActivity(intent);
                    } else {
                        showToast("Login fallido");
                    }
                });
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        limipiarCampos();
    }

    private void limipiarCampos() {
        binding.etCorreo.setText("");
        binding.etPass.setText("");
    }
}