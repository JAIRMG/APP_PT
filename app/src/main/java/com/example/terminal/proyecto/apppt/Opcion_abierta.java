package com.example.terminal.proyecto.apppt;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class Opcion_abierta extends Fragment {

    Button click01;
    View opcbierta;


    public Opcion_abierta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        opcbierta = inflater.inflate(R.layout.fragment_opcion_abierta, container, false);

        click01 = (Button) opcbierta.findViewById(R.id.button_1);

        click01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity miactividad = getActivity();
                ((sendData)miactividad).numero2();
            }
        });


        // Inflate the layout for this fragment
        return opcbierta;
    }


    public int random(){
        Random r = new Random();
        int i1 = r.nextInt(3) + 1;
        return i1;
    }


}
