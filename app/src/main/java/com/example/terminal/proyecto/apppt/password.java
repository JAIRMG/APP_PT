package com.example.terminal.proyecto.apppt;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class password extends AppCompatActivity {

    public static final String FINAL_URL = "http://192.168.1.88:8084/WebServiceForApp/webresources/generic";
    public static final String SIGN_IN = "/forgotPassword/";
    public static final String ERROR_FROM_NETWORK_NOT_CONNECTED = "Error, Conexión a internet no disponible";

    public Context context;
    Button botonEnviar;
    EditText campoCorreo;
    String correo="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        context = this;

        botonEnviar = (Button) findViewById(R.id.botonRecuperar);
        campoCorreo = (EditText) findViewById(R.id.campoCorreo);

        botonEnviar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                correo = campoCorreo.getText().toString();

                if(!correo.equals("") && correo.contains("@") && correo.contains(".")){


                    String finalJson = "";

                    JSONObject userRequest = new JSONObject();
                    try{
                        userRequest.put("email", correo);

                        finalJson = userRequest.toString();
                    } catch(JSONException ex){
                        System.out.println("Error al crear JSON: "+ex);
                    }

                    new recoverPassword().execute(finalJson);


                } else {
                    Toast.makeText(context, "Verifica que el correo sea correcto",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private class recoverPassword extends AsyncTask<String, Long, String> {


        protected String doInBackground(String... data) {


            String jsonLogin = data[0].replace("{","%7B").replace("}","%7D");
            jsonLogin = "["+jsonLogin+"]";
            Log.i("jsonFinal",jsonLogin);
            return HttpRequest.sendGetRequest(FINAL_URL + SIGN_IN + jsonLogin);
        }

        protected void onPostExecute(String response) {

            if(response==null || response.equals("")) {
                Toast.makeText(getApplicationContext(), "Error en el servidor, Inténtalo más tarde", Toast.LENGTH_SHORT).show();

            }
            else if(response.equals(ERROR_FROM_NETWORK_NOT_CONNECTED)) {
                Toast.makeText(getApplicationContext(), "ERROR_FROM_NETWORK_NOT_CONNECTED", Toast.LENGTH_SHORT).show();

            }
            else if(response.contains("Error") && response.contains("Message")) {
                try {
                    JSONObject json = new JSONObject(response);
                    String message = json.getString("Message");
                    Toast.makeText(getApplicationContext(), "error: "+message, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else{ //if(JSONBuilder.checkJsonStructure(response)) {
                Log.i("MyTAG: ", response);
                CheckIfCorrect(response);

            }

        }

        private void CheckIfCorrect(String response){

            String respuesta = "";

            try{
                JSONArray jsonarray = new JSONArray(response);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    respuesta = jsonobject.getString("respuesta");
                }
            } catch(JSONException ex){
                System.out.println("Error al parsear json "+ ex);
            }
            Log.i("Respuesta: ", response);

            if(respuesta.equals("1")){
                Toast.makeText(getApplicationContext(), "Se ha enviado un correo con tu nueva contraseña", Toast.LENGTH_SHORT).show();
                goHome();
            } else{
                Toast.makeText(getApplicationContext(), "Correo inválido", Toast.LENGTH_SHORT).show();
            }


        }
    }

    public void goHome(){
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

}
