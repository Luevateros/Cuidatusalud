package com.luevateros.cuidatusalud;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

public class ResultadoFragment extends Fragment {

    private final int edad;
    private final int altura;
    private final int peso;
    private final boolean sexo;

    // Para los menores de 20, se calcula el IMC diferente.
    private final double[][] fem = {
            { 9.5, 13.6, 15.7},     // 1
            {10.2, 14.3, 16.5},     // 2
            {11.1, 15.2, 17.3},     // 3
            {11.9, 16.0, 18.1},     // 4
            {12.7, 16.8, 18.9},     // 5
            {12.7, 16.9, 19.1},     // 6
            {12.8, 17.3, 20.0},     // 7
            {13.0, 17.9, 20.9},     // 8
            {13.3, 18.6, 21.9},     // 9
            {13.5, 18.9, 22.5},     // 10
            {13.9, 19.8, 23.4},     // 11
            {14.4, 20.7, 24.9},     // 12
            {14.9, 21.7, 26.1},     // 13
            {15.4, 22.6, 27.2},     // 14
            {15.9, 23.4, 28.1},     // 15
            {16.2, 24.0, 28.8},     // 16
            {16.4, 24.4, 29.2},     // 17
            {16.4, 24.7, 29.4},     // 18
            {16.5, 24.9, 29.6},     // 19
    };
    private final double[][] mas = {
            { 9.5, 11.0, 12.7},     // 1
            {10.2, 13.1, 14.8},     // 2
            {11.1, 14.6, 16.3},     // 3
            {11.9, 16.1, 17.8},     // 4
            {13.0, 16.6, 18.3},     // 5
            {13.0, 16.7, 18.4},     // 6
            {13.1, 16.9, 18.9},     // 7
            {13.4, 17.6, 20.0},     // 8
            {13.6, 18.1, 20.8},     // 9
            {13.7, 18.4, 21.3},     // 10
            {14.1, 19.1, 22.4},     // 11
            {14.5, 19.8, 23.5},     // 12
            {14.9, 20.7, 24.7},     // 13
            {15.5, 21.7, 25.8},     // 14
            {16.0, 22.6, 26.9},     // 15
            {16.5, 23.4, 27.8},     // 16
            {16.9, 24.2, 28.5},     // 17
            {17.3, 24.8, 29.1},     // 18
            {17.6, 25.3, 29.6},     // 19
    };

    public ResultadoFragment(int edad, int altura, int peso, boolean sexo) {
        this.edad = edad;
        this.altura = altura;
        this.peso = peso;
        this.sexo = sexo;   // Femenino: True
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resultado, container, false);

        double h = altura * 0.01;
        double imc = peso / (h * h);
        boolean positivo = false;
        RelativeLayout fondo = view.findViewById(R.id.resultadoimc);
        TextView imcLabel = view.findViewById(R.id.imc);
        TextView interpretacion = view.findViewById(R.id.interpretacion);

        if (edad < 20){
            double[] escala;
            if (sexo)
                escala = fem[edad - 1];
            else
                escala = mas[edad - 1];
            int valor = -1;
            for (int i = 0; i < escala.length; i++) {
                if (imc <= escala[i]) {
                    valor = i;
                    break;
                }
            }
            switch (valor){
                case -1:
                    imcLabel.setTextColor(getResources().getColor(R.color.white));
                    interpretacion.setText("Obesidad");
                    fondo.setBackgroundColor(Color.parseColor("#ff152f"));
                    break;
                case 0:
                    interpretacion.setText("Desnutrición");
                    fondo.setBackgroundColor(Color.parseColor("#36a9e1"));
                    break;
                case 1:
                    interpretacion.setText("Normal");
                    fondo.setBackgroundColor(Color.parseColor("#68f2e8"));
                    positivo = true;
                    break;
                default:
                    interpretacion.setText("Sobrepeso");
                    fondo.setBackgroundColor(Color.parseColor("#c6e555"));
            }
        } else {
            if (imc < 18.5) {
                interpretacion.setText("Bajo peso");
                fondo.setBackgroundColor(Color.parseColor("#36a9e1"));
            }
            else if (imc >= 18.5 && imc < 24.9) {
                positivo = true;
                interpretacion.setText("Aceptable");
                fondo.setBackgroundColor(Color.parseColor("#68f2e8"));
            }
            else if (imc >= 25.0 && imc < 29.9) {
                interpretacion.setText("Sobrepeso");
                fondo.setBackgroundColor(Color.parseColor("#c6e555"));
            }
            else if (imc >= 30.0 && imc < 34.9) {
                interpretacion.setText("Obesidad grado 1");
                fondo.setBackgroundColor(Color.parseColor("#ffce00"));
            }
            else if (imc >= 35.0 && imc < 39.9) {
                interpretacion.setText("Obesidad grado 2");
                fondo.setBackgroundColor(Color.parseColor("#fc7b1a"));
            }
            else {
                imcLabel.setTextColor(getResources().getColor(R.color.white));
                interpretacion.setText("Obesidad grado 3");
                fondo.setBackgroundColor(Color.parseColor("#ff152f"));
            }
        }

        // Resultado
        imcLabel.setText(String.format(Locale.US, "%.1f", imc));

        // Interpretación
        ImageView icono = view.findViewById(R.id.iconoresultado);
        if (positivo)
            icono.setImageResource(R.drawable.exito);

        return view;
    }
}