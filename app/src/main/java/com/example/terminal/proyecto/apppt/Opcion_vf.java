package com.example.terminal.proyecto.apppt;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class Opcion_vf extends Fragment {

    Button click03;
    View opcvf;
    TextView textView;
    RadioButton opc1, opc2;
    RadioGroup radioGroup;
    public String[] reactivos_diagnostico = new String[20];
    public String[] respuestas_diagnostico = new String[20];
    String reactivo;
    String Answer; //Este string contiene a la respuesta correcta

    public Opcion_vf() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        opcvf = inflater.inflate(R.layout.fragment_opcion_vf, container, false);

        click03 = (Button) opcvf.findViewById(R.id.button_1);
        textView = (TextView) opcvf.findViewById(R.id.reactivo_vf);
        radioGroup = (RadioGroup) opcvf.findViewById(R.id.radioGroup);

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

        click03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Aquí tenemos que preguntar si se encuentra resolviendo diagnostico o no porque los reactivos de practicar
                //vienen en sharedPreferences distintos
                checkAnswer(Answer);
                Activity miactividad = getActivity();
                ((sendData)miactividad).switchFragment();


            }
        });



        // Inflate the layout for this fragment
        return opcvf;
    }

    public Boolean checkDiagnostico(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Boolean check = preferences.getBoolean("DIAGNOSTICO",false);
        return check;
    }

    public void checkAnswer(String answer){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton rb = radioGroup.findViewById(selectedId);
        String userAnswer = ""; //Inicializamos la variable por si el usuario no contesta evitamos crash
        if(selectedId >= 0)userAnswer = rb.getText().toString().toLowerCase(); //Solo si selecciona 1 opción extraigo el texto

        if(userAnswer.equals(answer)){
            Toast.makeText(getActivity(),"Correcto" + answer +" : " + userAnswer, Toast.LENGTH_LONG).show();
            increasePoints();
        } else {
            Toast.makeText(getActivity(),"Incorrecto" + answer +" : " + userAnswer, Toast.LENGTH_LONG).show();
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
