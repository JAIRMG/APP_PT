package com.example.terminal.proyecto.apppt;


import android.app.Activity;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class Opcion_multiple extends Fragment {

    Button click02;
    View opcMulti;
    public String[] reactivos_diagnostico = new String[20];
    public String[] respuesta_diagnostico = new String[20];
    public String[] res_opc1 = new String[20];
    public String[] res_opc2 = new String[20];
    public String[] res_opc3 = new String[20];
    String reactivo;
    TextView textView;
    RadioButton opc1, opc2, opc3, opc4; //Las cuatro opciones por elegir, una es la correcta
    RadioGroup radioGroup;
    int indexRespuesta;

    public Opcion_multiple() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        opcMulti = inflater.inflate(R.layout.fragment_opcion_multiple, container, false);

        click02 = (Button) opcMulti.findViewById(R.id.button_1);
        textView = (TextView) opcMulti.findViewById(R.id.reactivo_multiple);
        radioGroup = (RadioGroup) opcMulti.findViewById(R.id.radioGroup);



        if(!checkDiagnostico()){ //Checamos que este resolviendo el diagnostico

            // Log.i("index_diag", ""+preferences.getInt("INDEX_DIAGNOSTICO",0));
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            reactivo = preferences.getString("REACTIVOS_DIAGNOSTICO", "");
            reactivos_diagnostico = reactivo.split("//");
            int i = preferences.getInt("INDEX_DIAGNOSTICO",0);
            Log.i("index_diag", ""+i);
            textView.setText(reactivos_diagnostico[i-1]); //Reactivo
            setOption(i-1);
            indexRespuesta = setOption(i-1);//Opciones
        }

        click02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //EvaluarRespuesta
                checkAnswer(indexRespuesta);
                //Llamar a la función que cambia de fragmento
                Activity miactividad = getActivity();
                ((sendData)miactividad).switchFragment();


            }
        });

        return opcMulti;
    }


    public Boolean checkDiagnostico(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Boolean check = preferences.getBoolean("DIAGNOSTICO",false);
        return check;
    }

    public int setOption(int index){
        int indice = index;
        Random r = new Random();
        int i = r.nextInt(5 - 1) + 1;
        opc1 = (RadioButton) opcMulti.findViewById(R.id.resp1);
        opc2 = (RadioButton) opcMulti.findViewById(R.id.resp2);
        opc3 = (RadioButton) opcMulti.findViewById(R.id.resp3);
        opc4 = (RadioButton) opcMulti.findViewById(R.id.resp4);

        //Leemos las opciones y la respuesta de sharedpreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        reactivo = preferences.getString("RESPUESTAS_DIAGNOSTICO", "");
        respuesta_diagnostico = reactivo.split("//");
        reactivo = preferences.getString("OPCION1_DIAGNOSTICO", "");
        res_opc1 = reactivo.split("//");
        reactivo = preferences.getString("OPCION2_DIAGNOSTICO", "");
        res_opc2 = reactivo.split("//");
        reactivo = preferences.getString("OPCION3_DIAGNOSTICO", "");
        res_opc3 = reactivo.split("//");

        switch (i){
            case 1:
                opc1.setText(respuesta_diagnostico[indice]);
                opc2.setText(res_opc1[indice]);
                opc3.setText(res_opc2[indice]);
                opc4.setText(res_opc3[indice]);
                break;
            case 2:
                opc1.setText(res_opc1[indice]);
                opc2.setText(respuesta_diagnostico[indice]);
                opc3.setText(res_opc2[indice]);
                opc4.setText(res_opc3[indice]);
                break;
            case 3:
                opc3.setText(respuesta_diagnostico[indice]);
                opc1.setText(res_opc1[indice]);
                opc2.setText(res_opc2[indice]);
                opc4.setText(res_opc3[indice]);
                break;
            case 4:
                opc4.setText(respuesta_diagnostico[indice]);
                opc1.setText(res_opc1[indice]);
                opc2.setText(res_opc2[indice]);
                opc3.setText(res_opc3[indice]);
                break;

        }
        return i;
    }

    public void checkAnswer(int index){
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonId);
        int selectedRadio = radioGroup.indexOfChild(radioButton);
        if(index == (selectedRadio + 1)){ //Porque el radiobutton empieza en 0 le sumamos uno
            Toast.makeText(getActivity(),"Correcto"+selectedRadio, Toast.LENGTH_SHORT).show();
            increasePoints();
        } else {
            Toast.makeText(getActivity(),"Incorrecto"+selectedRadio, Toast.LENGTH_SHORT).show();
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
