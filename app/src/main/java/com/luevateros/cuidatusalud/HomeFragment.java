package com.luevateros.cuidatusalud;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class HomeFragment extends Fragment {

    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Portada");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button registro = view.findViewById(R.id.botonregistro);
        Button sesion = view.findViewById(R.id.botonsesion);
        registro.setOnClickListener(viewActual ->
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_fragmento, new NewUserFragment())
                        .commit());

        // Se inicia sesión.
        sesion.setOnClickListener(viewActual -> {

            sharedPreferences = getActivity().getSharedPreferences("guardarDatos", Context.MODE_PRIVATE);
            String correo = sharedPreferences.getString("kCorreo", null);

            // Si el usuario hace clic en iniciar sesión pero no hay ningún usuario registrado,
            // entonces no se puede iniciar sesión.
            if(correo == null)
                Toast.makeText(getActivity(), "No tenemos ningún usuario registrado.", Toast.LENGTH_SHORT).show();
            else
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_fragmento, new LoginFragment())
                        .commit();
        });
        return view;
    }
}