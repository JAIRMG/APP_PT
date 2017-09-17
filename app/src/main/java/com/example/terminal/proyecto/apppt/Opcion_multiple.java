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
public class Opcion_multiple extends Fragment {

    Button click02;
    View opcbierta;


    public Opcion_multiple() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        opcbierta = inflater.inflate(R.layout.fragment_opcion_multiple, container, false);

        click02 = (Button) opcbierta.findViewById(R.id.button_1);

        click02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity miactividad = getActivity();
                ((sendData)miactividad).numero(0, random());
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
