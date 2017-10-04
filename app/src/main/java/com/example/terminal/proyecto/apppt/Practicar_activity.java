package com.example.terminal.proyecto.apppt;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Random;

import static android.R.attr.data;

/**
 * Created by pc on 16/09/2017.
 */


public class Practicar_activity extends AppCompatActivity implements sendData {


    private TextView numero;
    private TextView numrandom;
    Context context;
    private int x;
    final String json_url = "http://192.168.1.77/getDatos.php/";
    Fragment opc1 = new Opcion_abierta();
    Fragment opc2 = new Opcion_multiple();
    Fragment opc3 = new Opcion_vf();
    Fragment result = new Resultados();
    private  int tipo;
    public String reactivo = "ini";
    public String respuesta = "init";
    public String subtema;
    public String incorrecta1;
    public String incorrecta2;
    public String incorrecta3;
    public int abierta = 0;
    public int multiple = 0;
    public int vf = 0;
    public int[] arrayRandom = new int[10];
    public int contFragments = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        Log.i("a","practicar_activity");
        x=0;
        setContentView(R.layout.practicar_activity);


       // numero = (TextView) findViewById(R.id.txt_num);
        //numrandom = (TextView) findViewById(R.id.txt_random);

        //Recuperando el subtema
        Intent intent= getIntent();
        Bundle b = intent.getExtras();
        if(b != null)
        {
            subtema =(String) b.get("subtema");
            Toast.makeText(context, "El subtema que presionaste fue "+subtema, Toast.LENGTH_SHORT).show();
        }



        randomReactive();
        countRepeated();
        jsonConnection_first("1","1",subtema,"abierta","1");
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

    public void countRepeated(){

        int[] count = new int[3];
        Arrays.sort(arrayRandom);

        for(int i = 0; i < arrayRandom.length; i++) {
           if(arrayRandom[i] == 1){
               abierta++;
           } else if (arrayRandom[i] == 2){
               multiple++;
           } else vf++;
        }

       // Log.i("Duplicate: ", "abierta: "+abierta+ " multiple: "+multiple +" vf: "+vf);

        count[0] = abierta + 1;
        count[1] = multiple;
        count[2] = vf - 1;

        JSONObject jsonObject = new JSONObject();
        String finalJson = "";
        try{
            jsonObject.put("abierta", Integer.toString(abierta));
            jsonObject.put("multiple", Integer.toString(multiple));
            jsonObject.put("vf", Integer.toString(vf));

            finalJson = jsonObject.toString();
        } catch(JSONException ex){
            System.out.println("Error al crear JSON: "+ex);
        }


        Toast.makeText(context, finalJson,Toast.LENGTH_LONG).show();
       // return finalJson;

    }

    public void jsonConnection(String grado, String nivel, String subtema, String tipo, String flag){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,json_url+"?"+"subtema="+subtema+"&"+"grado="+grado+"&nivel="+nivel,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            reactivo = response.getString("reactivo");
                            respuesta = response.getString("respuesta");
                            /*incorrecta1 = response.getString("incorrecta1");
                            incorrecta2 = response.getString("incorrecta2");
                            incorrecta3 = response.getString("incorrecta3");*/

                            Log.i("reactivo en funcion: ",reactivo);

                        }catch (JSONException e){
                            Log.i("Parse_error", "error al cachar json");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error from php",Toast.LENGTH_LONG).show();
            }
        });

        MySingleton.getInstance(context).add7oRequestQue(jsonObjectRequest);

    }
    public void jsonConnection_first(String grado, String nivel, String subtema, String tipo, String flag){

        Log.i("Duplicate: ", "abierta: "+abierta+ " multiple: "+multiple +" vf: "+vf);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,json_url+"?"+"subtema="+subtema+"&"+"grado="+grado+"&nivel="+nivel,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            reactivo = response.getString("reactivo");
                            respuesta = response.getString("respuesta");
                            /*incorrecta1 = response.getString("incorrecta1");
                            incorrecta2 = response.getString("incorrecta2");
                            incorrecta3 = response.getString("incorrecta3");*/



                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("reactivo_abierta",reactivo);
                            editor.apply();
                            //opc1.setArguments(arguments);
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.vtn_practicar, opc1).commit();

                        }catch (JSONException e){
                            Log.i("Parse_error", "error al cachar json");
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error from php",Toast.LENGTH_LONG).show();
            }
        });

        MySingleton.getInstance(context).add7oRequestQue(jsonObjectRequest);

    }



    @Override
    public void numero2() {

        //jsonConnection();


        Toast.makeText(this, reactivo + respuesta,
                Toast.LENGTH_LONG).show();


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
                jsonConnection("1","1",subtema,"abierta","1");
                contFragments ++;
                break;
            case 3:
                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.vtn_practicar, opc3).commit();
                Log.i("randomm","posición"+contFragments);
                jsonConnection("1","1",subtema,"abierta","1");
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
