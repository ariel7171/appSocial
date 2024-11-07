package com.example.proyectointegrador.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectointegrador.R;
import com.example.proyectointegrador.databinding.ActivityUserBinding;
import com.example.proyectointegrador.model.User;
import com.example.proyectointegrador.viewModel.UserViewModel;

public class UserActivity extends AppCompatActivity {
    private ActivityUserBinding binding;
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        esperarObservers();
        manejarEventos();
    }

    private void esperarObservers() {
    }

    private void manejarEventos() {
        binding.btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User usuario = obtenerDatosDelUsuario();
                viewModel.createUser(usuario, UserActivity.this);
            }
        });

        binding.btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User usuario = obtenerDatosDelUsuario();
                viewModel.updateUser(usuario, UserActivity.this);
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = binding.etId.getText().toString().trim();
                viewModel.deleteUser(id, UserActivity.this);
            }
        });

        binding.btnReadUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.etCorreo.getText().toString().trim();
                viewModel.getUser(email, UserActivity.this);
            }
        });

        binding.circuloBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
    }

    private User obtenerDatosDelUsuario() {
        String username = binding.etUsuario.getText().toString();
        String email = binding.etCorreo.getText().toString().trim();
        String id = binding.etId.getText().toString().trim();
        String password = binding.etPass1.getText().toString().trim();
        return new User(id, username, email, password);
    }

    private void mostrarUsuario(User user) {
        binding.etUsuario.setText(user.getUsername());
        binding.etCorreo.setText(user.getEmail());
        binding.etId.setText(user.getId());
        binding.etPass1.setText(user.getPassword());
        Log.d("mostrar", user.getId() + "-" + user.getUsername());
    }

    private void limpiar() {
        binding.etUsuario.setText("");
        binding.etCorreo.setText("");
        binding.etId.setText("");
        binding.etPass1.setText("");
    }
}