package com.example.terminal.proyecto.apppt.Diagnostico;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.terminal.proyecto.apppt.Callback;
import com.example.terminal.proyecto.apppt.GetJsonService;
import com.example.terminal.proyecto.apppt.Opcion_abierta;
import com.example.terminal.proyecto.apppt.Opcion_multiple;
import com.example.terminal.proyecto.apppt.Opcion_vf;
import com.example.terminal.proyecto.apppt.R;
import com.example.terminal.proyecto.apppt.sendData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Diagnostico extends AppCompatActivity implements sendData {

    Context context;
    boolean doubleBackToExitPressedOnce = false; //Para el mensaje de salida
    public int[] ordenDiagnostico = {0,0,2,0,0,0,0,1,0,1,1,0,0,2,0,2,2,0,0,0,3}; //Orden en que estan los reactivos del diagnostico (el 3 me termina la actividad)
    public int indexReactivo = 0; //index de los reactivos del diagnostico
    Fragment opc1 = new Opcion_abierta();
    Fragment opc2 = new Opcion_multiple();
    Fragment opc3 = new Opcion_vf();
    Fragment bienvenida = new Diagnostico_bienvenida_fragment();
    final String url_diagnostico = "http://myappmate.000webhostapp.com/sendExamen.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_diagnostico);


        /*--------------------------------------- */
        /*Se hace la petición para obtener el json y se parsea en la función
        * parseDiagnostico*/
        GetJsonService getJsonService = new GetJsonService();
        getJsonService.getData(1, new Callback<String>() {
            public void onResponse(String json) {
                Log.i("FinalFinal",json);
                parseDiagnostico(json);
            }
        },url_diagnostico);


                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.diagnostico, bienvenida).commit();
                //Necesito que el layout de la vista no tenga nada y que yo reemplace


    }


    //https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("¡No has terminado aún!")
                .setMessage("Si te vas de este pequeño examen te perderás de la sorpresa :)")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("DIAGNOSTICO",true);
                        editor.commit();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();




    }

    @Override
    public void init(int num, int random) {

    }

    @Override
    public void numero(int num, int random) {

    }

    @Override
    public void numero2() {

    }

    @Override
    public void switchFragment() {

        switch (ordenDiagnostico[indexReactivo]){

            case 0: //caso donde se muestran los reactivos de opción múltiple
                indexReactivo++;
                //incrementIndexDiagnostico(indexReactivo);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor;
                editor = preferences.edit();
                editor.putInt("INDEX_DIAGNOSTICO", indexReactivo);
                editor.apply();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.diagnostico, new Opcion_multiple(), "opc0");
                ft.commit();
                //fragmentTransaction(opc2);
                break;

            case 1: //caso donde se muestran los reactivos de opción múltiple
                indexReactivo++;
                //incrementIndexDiagnostico(indexReactivo);
                SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor2;
                editor2 = preferences2.edit();
                editor2.putInt("INDEX_DIAGNOSTICO", indexReactivo);
                editor2.apply();
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ft2.replace(R.id.diagnostico, new Opcion_abierta(), "opc1");
                ft2.commit();
                //fragmentTransaction(opc1);
                break;
            case 2:
                indexReactivo++;
                //incrementIndexDiagnostico(indexReactivo);
                SharedPreferences preferences3 = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor3;
                editor3 = preferences3.edit();
                editor3.putInt("INDEX_DIAGNOSTICO", indexReactivo);
                editor3.apply();
                FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                ft3.replace(R.id.diagnostico, new Opcion_vf(), "opc2");
                ft3.commit();
                //fragmentTransaction(opc3);
                break;

            case 3:
                SharedPreferences preferences4 = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor4 = preferences4.edit();
                editor4.putBoolean("DIAGNOSTICO",true);
                editor4.commit();
                finish();
                //Mostrar puntos obtenidos en el diagnóstico
                SharedPreferences preferences_points = PreferenceManager.getDefaultSharedPreferences(this);
                int puntos = preferences_points.getInt("PUNTAJE_GENERAL", 0);
                Toast.makeText(this, "Respuestas correctas: "+(puntos/10)+" de 20", Toast.LENGTH_LONG).show();
                break;
        }

    }

    public void parseDiagnostico(String json){

        // ------------------------------------------------------------------
        /* Se parsean los reactivos y las respuestas del examen diagnóstico */
        String[] reactivos_diagnostico = new String[20];
        String[] respuesta_diagnostico = new String[20];
        String[] opcion1 = new String[20];
        String[] opcion2 = new String[20];
        String[] opcion3 = new String[20];
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                reactivos_diagnostico[i] = jsonObject.getString("reactivo");
                respuesta_diagnostico[i] = jsonObject.getString("respuesta");
                opcion1[i] = jsonObject.getString("opc1");
                opcion2[i] = jsonObject.getString("opc2");
                opcion3[i] = jsonObject.getString("opc3");
            }
        } catch(JSONException e){
            Log.i("Error","Imposible convertir String a JsonObject");
        }


        // ------------------------------------------------------------------
        /* Se guardan los reactivos y respuestas del diagnostico en sharedPreferences,
         * así como un index que permitirá obtener el número de reactivo */

        StringBuilder sb_reactivos = new StringBuilder();
        StringBuilder sb_respuestas = new StringBuilder();
        StringBuilder sb_opc1 = new StringBuilder();
        StringBuilder sb_opc2 = new StringBuilder();
        StringBuilder sb_opc3 = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb_reactivos.append(reactivos_diagnostico[i]).append("//"); //las separamos por una coma
            sb_respuestas.append(respuesta_diagnostico[i]).append("//");
            sb_opc1.append(opcion1[i]).append("//");
            sb_opc2.append(opcion2[i]).append("//");
            sb_opc3.append(opcion3[i]).append("//");
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("REACTIVOS_DIAGNOSTICO",sb_reactivos.toString());
        editor.putString("RESPUESTAS_DIAGNOSTICO",sb_respuestas.toString());
        editor.putString("OPCION1_DIAGNOSTICO",sb_opc1.toString());
        editor.putString("OPCION2_DIAGNOSTICO",sb_opc2.toString());
        editor.putString("OPCION3_DIAGNOSTICO",sb_opc3.toString());
        editor.putInt("INDEX_DIAGNOSTICO",0);
        editor.apply();

        /*Fragment opc1 = new Opcion_abierta();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, opc1).commit();*/

    }

    public void incrementIndexDiagnostico(int indexReactivo){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor;
        editor = preferences.edit();
        editor.putInt("INDEX_DIAGNOSTICO", indexReactivo);
        editor.apply();
    }

    public void fragmentTransaction(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.diagnostico, fragment).commit();
    }
}
