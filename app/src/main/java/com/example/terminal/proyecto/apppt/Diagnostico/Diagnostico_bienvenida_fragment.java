package com.example.terminal.proyecto.apppt.Diagnostico;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.terminal.proyecto.apppt.R;
import com.example.terminal.proyecto.apppt.sendData;

/**
 * Created by Jair Moreno Gaspar on 18/10/2017.
 */

//Esta clase sirve para mostrar el primer fragmento en el diagnostico
//Este fragmento es la bienvenida al examen
public class Diagnostico_bienvenida_fragment extends Fragment {

    View bienvenidaFragment;
    Button button;

    public Diagnostico_bienvenida_fragment(){
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bienvenidaFragment = inflater.inflate(R.layout.bienvenida_diagnostico, container, false);
        button = (Button) bienvenidaFragment.findViewById(R.id.comenzar_diagnostico);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity miactividad = getActivity();
                ((sendData)miactividad).switchFragment();
            }
        });


        return bienvenidaFragment;
    }
}
