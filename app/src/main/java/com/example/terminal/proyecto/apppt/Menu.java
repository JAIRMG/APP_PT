package com.example.terminal.proyecto.apppt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.BottomNavigationView;

import com.example.terminal.proyecto.apppt.Diagnostico.Diagnostico;

import java.util.ArrayList;


public class Menu extends AppCompatActivity {

    final String jsonRemoteUrl = "http://myappmate.000webhostapp.com/sendDatos.php/?abierta=3&multiple=4&vf=3";
    private BottomNavigationView bottomNavigationView;
    ArrayList<String> arrayList = new ArrayList<String>();
    String a = "jaja";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        final Fragment perfilFragment = new Perfil_fragment();
        final Fragment cursoFragment = new Curso_fragment();
        final Fragment practicarFragment = new Practicar_fragment();
        final Fragment infoFragment = new Informacion_fragment();


        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, perfilFragment).commit();

        }

        // -------------------Diagnóstico------------------//
        Boolean revisaDiagnostico = checkDiagnostico();
        if(!revisaDiagnostico){
            Intent intent;
            intent = new Intent(this, Diagnostico.class);
            startActivity(intent);
        }



        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                if (item.getItemId() == R.id.inicio) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, perfilFragment).commit();
                } else if (item.getItemId() == R.id.curso) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, cursoFragment).commit();
                } else if (item.getItemId() == R.id.practicar) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, practicarFragment).commit();
                } else if (item.getItemId() == R.id.informacion) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, infoFragment).commit();
                }
                return true;
            }
        });

    }



    public Boolean checkDiagnostico(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean check = preferences.getBoolean("DIAGNOSTICO",false);
        return check;
    }
}
