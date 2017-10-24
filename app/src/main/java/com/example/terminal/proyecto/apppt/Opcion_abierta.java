package com.example.terminal.proyecto.apppt;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class Opcion_abierta extends Fragment {

    Button click01;
    View opcbierta;
    TextView textView;
    EditText editText;
    public String[] reactivos_diagnostico = new String[20];
    public String[] respuestas_diagnostico = new String[20];
    String reactivo;
    String Answer; //Este string contiene a la respuesta correcta

    public Opcion_abierta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        opcbierta = inflater.inflate(R.layout.fragment_opcion_abierta, container, false);
        click01 = (Button) opcbierta.findViewById(R.id.button_1);
        textView = (TextView) opcbierta.findViewById(R.id.reactivo_abierta);
        editText = (EditText) opcbierta.findViewById(R.id.respuesta);

        if(!checkDiagnostico()){ //Checamos que este resolviendo el diagnostico

            // Log.i("index_diag", ""+preferences.getInt("INDEX_DIAGNOSTICO",0));
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            reactivo = preferences.getString("REACTIVOS_DIAGNOSTICO", "");
            reactivos_diagnostico = reactivo.split("//");
            //obtenemos las respuestas
            reactivo = preferences.getString("RESPUESTAS_DIAGNOSTICO","");
            respuestas_diagnostico = reactivo.split("//");
            int i = preferences.getInt("INDEX_DIAGNOSTICO",0);
            Log.i("index_diag", ""+i);
            textView.setText(reactivos_diagnostico[i-1]);
            Answer = respuestas_diagnostico[i-1];

        }

        click01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Aquí tenemos que preguntar si se encuentra resolviendo diagnostico o no
                checkAnswer(Answer);
                //Llamar a la función que cambia de fragmento
                Activity miactividad = getActivity();
                ((sendData)miactividad).switchFragment();


            }
        });

        return opcbierta;
    }



        /* SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        reactivo = preferences.getString("REACTIVOS", "");
        php_reactivos = reactivo.split(",");
        for (int i = 0; i < 10; i++) {
            Log.i("reactivos_recuperados", php_reactivos[i]);
        }
        Log.i("index_abierta",  "");
        textView.setText(php_reactivos[preferences.getInt("INDEX",0)]); */


    public Boolean checkDiagnostico(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Boolean check = preferences.getBoolean("DIAGNOSTICO",false);
        return check;
    }

    public void checkAnswer(String answer){

        if(editText.getText().toString().equals(answer)){
            Toast.makeText(getActivity(),"Correcto" + answer +" : " + editText.getText().toString(), Toast.LENGTH_LONG).show();
            increasePoints();
        } else {
            Toast.makeText(getActivity(),"Incorrecto" + answer +" : " + editText.getText().toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void increasePoints(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int puntos = preferences.getInt("PUNTAJE_GENERAL", 0);
        puntos = puntos + 10;
        SharedPreferences.Editor editor;
        editor = preferences.edit();
        editor.putInt("PUNTAJE_GENERAL", puntos);
        editor.apply();
    }

}
