package com.luevateros.cuidatusalud;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {

    EditText correo, password;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Iniciar sesión");
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        correo = view.findViewById(R.id.correo);
        password = view.findViewById(R.id.password);

        // Botones
        Button registro = view.findViewById(R.id.botonregistro);
        Button sesion = view.findViewById(R.id.botonsesion);
        registro.setOnClickListener(viewActual ->
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_fragmento, new NewUserFragment())
                        .commit());
        sesion.setOnClickListener(viewActual -> {
            sharedPreferences = getActivity().getSharedPreferences("guardarDatos", Context.MODE_PRIVATE);
            String corrStr = sharedPreferences.getString("kCorreo", null);
            String passStr = sharedPreferences.getString("kPass", null);
            if (corrStr == null || passStr == null)
                Toast.makeText(getActivity(), "No tenemos ningún usuario registrado.", Toast.LENGTH_SHORT).show();
            else if (correo.getText().toString().equals(corrStr) && password.getText().toString().equals(passStr))
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_fragmento, new ImcFragment())
                        .commit();
            else
                Toast.makeText(getActivity(), "El correo o la contraseña son incorrectos", Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}