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
    String respuesta = "kk";
    String reactivo = "kk";
    public String[] php_reactivos = new String[10];

    public Opcion_abierta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        opcbierta = inflater.inflate(R.layout.fragment_opcion_abierta, container, false);
        // Inflate the layout for this fragment
        return opcbierta;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public int random(){
        Random r = new Random();
        int i1 = r.nextInt(3) + 1;
        return i1;
    }

    @Override
    public void onResume() {
        super.onResume();

        textView = (TextView) opcbierta.findViewById(R.id.reactivo_abierta);
        editText = (EditText) opcbierta.findViewById(R.id.respuesta);
        click01 = (Button) opcbierta.findViewById(R.id.button_1);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        reactivo = preferences.getString("REACTIVOS", "");
        php_reactivos = reactivo.split(",");
        for (int i = 0; i < 10; i++) {
            Log.i("reactivos_recuperados", php_reactivos[i]);
        }
        Log.i("index_abierta",  "");
        textView.setText(php_reactivos[preferences.getInt("INDEX",0)]);

        click01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("click", "dio click en next");
                Activity miactividad = getActivity();
                ((sendData)miactividad).numero2();


            }
        });




    }
}
