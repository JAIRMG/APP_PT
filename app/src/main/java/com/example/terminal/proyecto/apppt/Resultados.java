package com.example.terminal.proyecto.apppt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Resultados extends Fragment {

    private Button boton;
    private View mivista;
    public Resultados() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mivista =  inflater.inflate(R.layout.fragment_resultados, container, false);

        boton = (Button) mivista.findViewById(R.id.Aceptar);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        // Inflate the layout for this fragment
        return mivista;
    }

}
