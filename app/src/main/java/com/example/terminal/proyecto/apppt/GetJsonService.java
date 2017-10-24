package com.example.terminal.proyecto.apppt;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pc on 16/10/2017.
 */

public class GetJsonService {

    final String jsonRemoteUrl = "http://myappmate.000webhostapp.com/sendDatos.php/?abierta=3&multiple=4&vf=3";
    ArrayList<String> arrayList = new ArrayList<String>();
    String Json = "vacio";

/*    public void jsonResponse(String jsonRemoteUrl, Context context){

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,jsonRemoteUrl,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i=0; i<response.length(); i++){

                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                arrayList.add(jsonObject.toString());
                            }catch (JSONException e){
                                Log.i("Parse_error", "Error al recibir json desde el servidor");
                            }

                        }
                        sendArray(arrayList);
                    }



                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ErrorResponse","Error from Volley");
            }
        });

        MySingleton.getInstance(context).add7oRequestQue(jsonArrayRequest);

    }
*/
    public void getData(int id, final Callback<String> callback, String URL) {

        //Inicializamos un cliente de la clase AsyncHttpClient
        AsyncHttpClient client = new AsyncHttpClient();

        //Generamos un String donde almacenamos la URL
        //String HTTP = "http://myappmate.000webhostapp.com/sendDatos.php/?abierta=3&multiple=4&vf=3";
        String HTTP = URL;

        //RequestParams sirve para enviar parametro ya sean de forma GET o POST
        RequestParams params = new RequestParams();

        //Enviamos la variable "token" con valor id
        params.put("token", id);


        //Ahora con el cliente obtenemos la respuesta
        // client.get()  ==  url?parametros=
        // client.post()  == enviar datos de forma POST como si fueran de un formulario.
        client.get(HTTP, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200) {
                    //https://stackoverflow.com/questions/36143496/get-android-asynchttpclient-response-after-it-finish
                    if (callback != null) {
                        callback.onResponse(new String(responseBody));
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("Error", error.toString());
            }
        });

    }






}
