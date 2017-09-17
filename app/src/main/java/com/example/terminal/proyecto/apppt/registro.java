package com.example.terminal.proyecto.apppt;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class registro extends AppCompatActivity {

    public static final String FINAL_URL = "http://192.168.1.88:8084/WebServiceForApp/webresources/generic";
    public static final String SIGN_IN = "/register/";
    public static final String ERROR_FROM_NETWORK_NOT_CONNECTED = "Error, Conexión a internet no disponible";

    public Context context;
    String nombre="",localidad="",apellido="",correo="",contrasenia1="",
           contrasenia2="",usuario="", genero = "";
    Integer edad=0;
    EditText editNombre, editApellido, editCorreo, editContrasenia1,
             editContrasenia12, editUsuario;
    Spinner spinnerLocalidad, spinnerEdad, spinnerGenero;
    String[] estados = new String[]{"CDMX","Aguascalientes","Baja California",
            "Baja California Sur", "Campeche", "Chiapas", "Chihuahua",
            "Coahuila de Zaragoza", "Colima", "Colima", "Durango", "Guanajuato",
            "Guerrero", "Hidalgo", "Jalisco", "México", "Michoacán de Ocampo",
            "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro de Arteaga",
            "Quintana Roo", "San Luis Potosí", "Sinaloa", "Sonora","Tabasco",
            "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas"};
    Integer[] edades = new Integer[]{10,11,12,13,14,15,16};
    String[] generos = new String[]{"Masculino","Femenino"};


    Button registrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        context = this;


        registrar = (Button) findViewById(R.id.botonRegistro);
        editNombre = (EditText) findViewById(R.id.nombre);
        editApellido = (EditText) findViewById(R.id.apellido);
        editUsuario = (EditText) findViewById(R.id.usuario);
        editCorreo = (EditText) findViewById(R.id.correo);
        editContrasenia1 = (EditText) findViewById(R.id.contrasenia1);
        editContrasenia12 = (EditText) findViewById(R.id.contrasenia2);
        spinnerLocalidad = (Spinner) findViewById(R.id.localidad);
        spinnerEdad = (Spinner) findViewById(R.id.edad);
        spinnerGenero = (Spinner) findViewById(R.id.genero);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, estados);
        spinnerLocalidad.setAdapter(adapter);
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, edades);
        spinnerEdad.setAdapter(adapter2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, generos);
        spinnerGenero.setAdapter(adapter3);



        registrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                nombre = editNombre.getText().toString();
                apellido = editApellido.getText().toString();
                usuario = editUsuario.getText().toString();
                correo = editCorreo.getText().toString();
                contrasenia1 = editContrasenia1.getText().toString();
                contrasenia2 = editContrasenia12.getText().toString();
                localidad = spinnerLocalidad.getSelectedItem().toString().toLowerCase();
                edad = (Integer) spinnerEdad.getSelectedItem();
                genero = spinnerGenero.getSelectedItem().toString().toLowerCase();
                nombre = nombre + " "+apellido;

                Log.d("spinner","localidad: "+localidad + " edad: "+edad);

                InputValidatorHelper myInput = new InputValidatorHelper();


                if(!myInput.containsDigit(nombre) || !myInput.containsDigit(apellido)){
                    Toast.makeText(context, "El campo nombre y apellido solo pueden tener letras",
                            Toast.LENGTH_LONG).show();
                } else if (!myInput.isEmpty(nombre) || !myInput.isEmpty(apellido) ||
                !myInput.isEmpty(usuario) || !myInput.isEmpty(contrasenia1)){
                    Toast.makeText(context, "Campos vacíos",
                            Toast.LENGTH_LONG).show();
                } else if (!myInput.checkPass(contrasenia1,contrasenia2)){
                    Toast.makeText(context, "Contraseñas no coinciden",
                            Toast.LENGTH_LONG).show();
                } else if (!myInput.checkLength(contrasenia1)){
                    Toast.makeText(context, "Contraseña muy insegura, modifícala",
                            Toast.LENGTH_LONG).show();
                } else if (!myInput.isValidEmail(correo)){
                    Toast.makeText(context, "Correo incorrecto",
                            Toast.LENGTH_LONG).show();
                }else{

                    String finalJson = "";

                    JSONObject userRequest = new JSONObject();
                    try{
                        userRequest.put("userName", usuario);
                        userRequest.put("password", contrasenia1);
                        userRequest.put("email", correo);
                        userRequest.put("nombre", nombre);
                        userRequest.put("localidad", localidad);
                        userRequest.put("genero", genero);
                        userRequest.put("edad", String.valueOf(edad));

                        finalJson = userRequest.toString();
                    } catch(JSONException ex){
                        System.out.println("Error al crear JSON: "+ex);
                    }

                    new registerUser().execute(finalJson);

                }

            }
        });


    }

    public void goHome(){

            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
    }

    private class InputValidatorHelper {

        private boolean isValidEmail(String string){
            return string.contains("@") && string.contains(".");
        }

        private boolean isEmpty(String string){

            return !string.equals("");

        }

        private  boolean containsDigit(String s) {
            boolean containsDigit = false;

            if (s != null && !s.isEmpty()) {
                for (char c : s.toCharArray()) {
                    if (containsDigit = Character.isDigit(c)) {
                        break;
                    }
                }
            }

            return !containsDigit;
        }

        private boolean checkPass(String pass1, String pass2){
            return pass1.equals(pass2);

        }

        private boolean checkLength(String pass1){
            return pass1.length() >= 5;

        }



    }

    private class registerUser extends AsyncTask<String, Long, String> {


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

            String checkMail = "";
            String respuesta = "";

           try{
            JSONArray jsonarray = new JSONArray(response);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject jsonobject = jsonarray.getJSONObject(i);
                 respuesta = jsonobject.getString("respuesta");
                 checkMail = jsonobject.getString("validEmail");
            }
           } catch(JSONException ex){
               System.out.println("Error al parsear json "+ ex);
           }
            Log.i("Respuesta: ", response);
            Log.i("aver: ", respuesta + checkMail);
            if(respuesta.equals("1")){
                if(checkMail.equals("1")){
                    Toast.makeText(getApplicationContext(), "¡Bienvenido!, Ya puedes inicar sesión", Toast.LENGTH_SHORT).show();
                    goHome();
                } else {
                    Toast.makeText(getApplicationContext(), "Correo inválido", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(getApplicationContext(), "Usuario o correo registrados previamente, intenta con uno nuevo", Toast.LENGTH_SHORT).show();
            }


        }
    }


}
