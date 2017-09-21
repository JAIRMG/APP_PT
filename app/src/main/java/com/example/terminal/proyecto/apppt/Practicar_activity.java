package com.example.terminal.proyecto.apppt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by pc on 16/09/2017.
 */


public class Practicar_activity extends AppCompatActivity implements sendData {


    private TextView numero;
    private TextView numrandom;

    private int x;

    Fragment opc1 = new Opcion_abierta();
    Fragment opc2 = new Opcion_multiple();
    Fragment opc3 = new Opcion_vf();
    Fragment result = new Resultados();
    private  int tipo;

    public int[] arrayRandom = new int[10];
    public int contFragments = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("a","practicar_activity");
        x=0;
        setContentView(R.layout.practicar_activity);


        numero = (TextView) findViewById(R.id.txt_num);
        numrandom = (TextView) findViewById(R.id.txt_random);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.vtn_practicar, opc1).commit();

        randomReactive();
    }

    public int random(){
        Random r = new Random();
        int i1 = r.nextInt(3) + 1;
        return i1;
    }

    public int[] randomReactive(){

        int cont = 0;
        Random r = new Random();

        while (cont < 10){
            arrayRandom[cont] = r.nextInt(3) + 1;
            cont++;
        }
        return arrayRandom;
    }

    @Override
    public void init(int num, int random) {

    }

    @Override
    public void numero(int num, int random) {
    /*    x++;
        tipo = random;

        numero.setText(Integer.toString(x));
        numrandom.setText(Integer.toString(tipo));

        switch (tipo){

            case 1:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.vtn_practicar, opc1).commit();
                break;
            case 2:
                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.vtn_practicar, opc2).commit();
                break;
            case 3:
                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.vtn_practicar, opc3).commit();
                break;

            default:
                Log.i("error","error");

        }
        if(x == 10){
            FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction3.replace(R.id.vtn_practicar, result).commit();


        }
*/

    }

    @Override
    public void numero2() {



        switch (arrayRandom[contFragments]){

            case 1:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.vtn_practicar, opc1).commit();
                Log.i("randomm","posición"+contFragments);
                contFragments ++;
                break;
            case 2:
                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.vtn_practicar, opc2).commit();
                Log.i("randomm","posición"+contFragments);
                contFragments ++;
                break;
            case 3:
                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.vtn_practicar, opc3).commit();
                Log.i("randomm","posición"+contFragments);
                contFragments ++;
                break;

            default:
                Log.i("error","error");

        }
        if(contFragments == 10){
            FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction3.replace(R.id.vtn_practicar, result).commit();


        }

    }


}
