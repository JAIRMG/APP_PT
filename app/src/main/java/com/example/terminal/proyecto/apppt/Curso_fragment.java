package com.example.terminal.proyecto.apppt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Jair Moreno on 05/08/2017.
 */

public class Curso_fragment extends Fragment {

    final String json_url = "http://192.168.1.77/getDatos.php/";
    Fragment opc1 = new Opcion_abierta();
    Fragment opc2 = new Opcion_multiple();
    Fragment opc3 = new Opcion_vf();
    Fragment result = new Resultados();

    public Curso_fragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.curso, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent;
        intent = new Intent(getActivity(), Curso_activity.class);
        startActivity(intent);

    }
}
