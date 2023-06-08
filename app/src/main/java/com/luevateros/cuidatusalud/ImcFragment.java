package com.luevateros.cuidatusalud;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ImcFragment extends Fragment {

    TextView pesoActual;
    SeekBar seekBar;
    Button calcular;
    int peso;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Calcula tu IMC");
        View view = inflater.inflate(R.layout.fragment_imc, container, false);

        // Barra para seleccionar peso.
        seekBar = view.findViewById(R.id.seekbarpeso);
        pesoActual = view.findViewById(R.id.pesoactual);

        // Se muestra el peso ideal por defecto.
        sharedPreferences = getActivity().getSharedPreferences("guardarDatos", Context.MODE_PRIVATE);
        int altura = sharedPreferences.getInt("kAltura", 0);
        peso = altura == 0 ? 80 : altura < 120 ? altura : altura - 100;

        seekBar.setMax(240);
        seekBar.setProgress(peso);
        pesoActual.setText(String.valueOf(peso));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                peso = i;
                pesoActual.setText(String.valueOf(peso));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        calcular = view.findViewById(R.id.calcula);
        calcular.setOnClickListener(view1 -> {
            if(peso == 0)
                Toast.makeText(getActivity(), "El peso debe ser mayor a 0 Kg", Toast.LENGTH_SHORT).show();
            else if (sharedPreferences.getInt("kEdad", 0) == 0)
                Toast.makeText(getActivity(), "Para calcular el IMC, primero tienes que registrarte.", Toast.LENGTH_SHORT).show();
            else{
                int edad = sharedPreferences.getInt("kEdad", 0);
                boolean sexo = sharedPreferences.getBoolean("kSexo", false);
                ResultadoFragment resultado = new ResultadoFragment(edad, altura, peso, sexo);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.resultado, resultado)
                        .commit();
            }
        });

        return view;
    }

}