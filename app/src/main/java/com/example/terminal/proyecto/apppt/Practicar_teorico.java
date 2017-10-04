package com.example.terminal.proyecto.apppt;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pc on 16/09/2017.
 */

public class Practicar_teorico extends Fragment {

public Practicar_teorico(){}

    private ExpandableListView expandableListView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.practicar_teorica, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //setContentView(R.layout.practicar_practica);

        expandableListView = (ExpandableListView) getView().findViewById(R.id.Exp1);
        initData();
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listHash);
        expandableListView.setAdapter(listAdapter);

       // button = (Button) getView().findViewById(R.id.empezarPractica);

      

    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Aritmética");
        listDataHeader.add("Álgebra");
        listDataHeader.add("Geometría");
        listDataHeader.add("Trigonometría");
        listDataHeader.add("Probabilidad");

        List<String> aritmetica = new ArrayList<>();
        aritmetica.add("Decimales");
        aritmetica.add("Naturales");
        aritmetica.add("Enteros");
        aritmetica.add("Fraccionarios");

        List<String> algebra = new ArrayList<>();
        algebra.add("Ecuación de primer grado x+a=b");
        algebra.add("Ecuación de primer grado (ax+b=cx+d)");
        algebra.add("Ecuación de segundo grado");
        algebra.add("Factorización");

        List<String> geometria = new ArrayList<>();
        geometria.add("Perímetros y áreas");
        geometria.add("Volumén de cubos");
        geometria.add("Prismas y pirámides");
        geometria.add("Ecuación de la pendiente");

        List<String> trigonometria = new ArrayList<>();
        trigonometria.add("Triángulos isósceles y equilateros");
        trigonometria.add("Ángulos inscritos");
        trigonometria.add("Triángulos rectángulos");
        trigonometria.add("Teorema de Pitágoras");

        List<String> probabilidad = new ArrayList<>();
        probabilidad.add("Triángulos isósceles y equilateros");
        probabilidad.add("Ángulos inscritos");
        probabilidad.add("Triángulos rectángulos");
        probabilidad.add("Teorema de Pitágoras");

        listHash.put(listDataHeader.get(0), aritmetica);
        listHash.put(listDataHeader.get(1), algebra);
        listHash.put(listDataHeader.get(2), geometria);
        listHash.put(listDataHeader.get(3), trigonometria);
        listHash.put(listDataHeader.get(4), probabilidad);
    }



}
