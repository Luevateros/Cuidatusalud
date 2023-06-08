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
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Pattern;

public class NewUserFragment extends Fragment {

    SharedPreferences sharedPreferences;
    EditText nombre, correo, edad, altura, password, password2;
    RadioGroup sexo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Nuevo usuario");
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);

        // Entrada
        nombre = view.findViewById(R.id.nombre);
        correo = view.findViewById(R.id.correo);
        sexo = view.findViewById(R.id.sexo);
        edad = view.findViewById(R.id.edad);
        altura = view.findViewById(R.id.altura);
        password = view.findViewById(R.id.password);
        password2 = view.findViewById(R.id.password2);

        // Botones
        Button registro = view.findViewById(R.id.botonregistro);
        registro.setOnClickListener(viewActual -> {

            String nombStr = nombre.getText().toString();
            String corrStr = correo.getText().toString();
            String passStr = password.getText().toString();
            String pass2Str = password2.getText().toString();

            // Verificamos los valores de entrada.
            if (nombStr.equals("")){
                Toast.makeText(getActivity(), "Es necesario ingresar un nombre.", Toast.LENGTH_SHORT).show();
            }
            else if (corrStr.equals("")){
                Toast.makeText(getActivity(), "Es necesario ingresar un correo.", Toast.LENGTH_SHORT).show();
            }
            else if(!Pattern.compile("^(.+)@(.+)$").matcher(corrStr).matches()) {
                Toast.makeText(getActivity(), "Es necesario ingresar un correo válido.", Toast.LENGTH_SHORT).show();
            }
            else if(sexo.getCheckedRadioButtonId() == -1){
                Toast.makeText(getActivity(), "Es necesario elegir un sexo.", Toast.LENGTH_SHORT).show();
            }
            else if (edad.getText().toString().equals("")) {
                Toast.makeText(getActivity(), "Es necesario ingresar una edad.", Toast.LENGTH_SHORT).show();
            }
            else if (altura.getText().toString().equals("")){
                Toast.makeText(getActivity(), "Es necesario ingresar una altura.", Toast.LENGTH_SHORT).show();
            }
            else if (passStr.equals("")){
                Toast.makeText(getActivity(), "Tienes que ingresar una contraseña.", Toast.LENGTH_SHORT).show();
            }
            else if (pass2Str.equals("")){
                Toast.makeText(getActivity(), "Tienes que repetir la contraseña.", Toast.LENGTH_SHORT).show();
            }
            else if (!passStr.equals(pass2Str)){
                Toast.makeText(getActivity(), "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            } else {
                int edadInt = Integer.parseInt(edad.getText().toString());
                int altuInt = Integer.parseInt(altura.getText().toString());

                if (edadInt < 1 || edadInt > 150){
                    Toast.makeText(getActivity(), "La edad mínima es 1 año y máxima es 150.", Toast.LENGTH_SHORT).show();
                } else if (altuInt < 30 || altuInt > 272){
                    Toast.makeText(getActivity(), "La altura mínima es 30 cm y máxima es 272.", Toast.LENGTH_SHORT).show();
                } else {
                    sharedPreferences = getActivity().getSharedPreferences("guardarDatos", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("kNombre", nombStr);
                    editor.putString("kCorreo", corrStr);
                    editor.putString("kPass", passStr);
                    editor.putInt("kEdad", edadInt);
                    editor.putInt("kAltura", altuInt);
                    editor.putBoolean("kSexo", sexo.getCheckedRadioButtonId() == 0);
                    editor.apply();
                    Toast.makeText(getActivity(), "El usuario fue registrado.", Toast.LENGTH_SHORT).show();

                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenedor_fragmento, new HomeFragment())
                            .commit();
            }}});


        Button sesion = view.findViewById(R.id.botonsesion);
        sesion.setOnClickListener(viewActual ->
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_fragmento, new LoginFragment())
                        .commit());
        return view;
    }
}