package com.example.terminal.proyecto.apppt;

import android.app.Activity;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pc on 12/09/2017.
 */

public class Info_personal extends Fragment {

    public static final String FINAL_URL = "http://192.168.1.76:8084/WebServiceForApp/webresources/generic";
    public static final String SIGN_IN = "/updatePassword/";
    public static final String ERROR_FROM_NETWORK_NOT_CONNECTED = "Error, Conexión a internet no disponible";

    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    private Context mContext;
    ImageView imageV;
    EditText editContrasenia;
    private Uri mSelectedImageUri;
    TextView Usuario, Contrasenia, Localidad, Nombre, Edad, Correo;
    FloatingActionButton guardarButton, fotoButton, editUserButton;
    String nomUsuario, passUsuario;
    LineChart chart;
    PieChart pieChart;
    Integer flagUsuario = 0;
    float[] yData = { 5, 10, 15, 30, 40 };
    String[] xData = { "Sony", "Huawei", "LG", "Apple", "SAMSUNG" };
    List<String> userInfo = new ArrayList<>();
    String finalUserName = "";
    String finalPassword = "";
    String finalLocalidad = "";
    String finalEdad = "";
    String finalEmail = "";
    String finalNombreCompleto = "";

    public Info_personal(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.info_personal, container, false);
        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Recuperar variables del usuario desde la actividad de login
        Bundle extras = getActivity().getIntent().getExtras();


        if (extras != null){
            userInfo = extras.getStringArrayList("userInfo");
            if(userInfo != null) {
                finalUserName = userInfo.get(0);
                finalPassword = userInfo.get(1);
                finalEmail = userInfo.get(2);
                finalLocalidad = userInfo.get(3);
                finalEdad = userInfo.get(4);
                finalNombreCompleto = userInfo.get(5);

                Toast.makeText(mContext, finalUserName + finalPassword + finalEmail + finalLocalidad + finalEdad + finalNombreCompleto, Toast.LENGTH_LONG).show();
            } else{
                Log.i("Error userInfo","No se pudo recuperar el arreglo de userInfo");
            }
        } else {
            Log.i("Error extras","Extras existe");
        }


    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState); System.out.print("hola perro");
        fotoButton = getView().findViewById(R.id.floatingActionButton);
        imageV = getView().findViewById(R.id.imageView);  //Imagen de perfil
        Usuario = getView().findViewById(R.id.usuario);
        Contrasenia = getView().findViewById(R.id.contrasenia);
        editContrasenia = getView().findViewById(R.id.editContrasenia);
        guardarButton = getView().findViewById(R.id.floatingActionButton3);
        editUserButton = getView().findViewById(R.id.floatingActionButton2);
        Localidad = getView().findViewById(R.id.localidad);
        Nombre = getView().findViewById(R.id.nombre);
        Edad = getView().findViewById(R.id.edad);
        Correo = getView().findViewById(R.id.correo);

        Nombre.setText(finalNombreCompleto);
        Localidad.setText(finalLocalidad);
        Edad.setText(finalEdad);
        Usuario.setText(finalUserName);
        Contrasenia.setText(finalPassword);
        Correo.setText(finalEmail);





        //editUsuario.setVisibility(View.GONE);
        editContrasenia.setVisibility(View.GONE);
        guardarButton.setVisibility(View.GONE);


        fotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //llama método para seleccionar imagen de perfil

                Log.d("onViewCreated","hola .l.");
                SelectImage();
            }
        });


        editUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //llama método para seleccionar imagen de perfil

                Log.d("Button2","Presiono floatingActionButton de edición");
                EditUser();
                flagUsuario++;
            }
        });


        guardarButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                guardarInfoPerfil();
            }
        });


        if(flagUsuario > 0 && (!nomUsuario.isEmpty() || !passUsuario.isEmpty())){
            Contrasenia.setText(passUsuario);
        } /*else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
            builder1.setMessage("Write your message here.");
        }*/

        if (mSelectedImageUri != null) {
            imageV.setImageURI(mSelectedImageUri);
        }


    }



    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }


    public void guardarInfoPerfil(){

        if(editContrasenia.getText().toString().equals("")) {
            Toast.makeText(mContext, "Campo de contraseña vacío", Toast.LENGTH_SHORT).show();
        }
        if (editContrasenia.getText().toString().length()<5) {
            Toast.makeText(mContext, "Contraseña insegura, modíficala", Toast.LENGTH_SHORT).show();
        }
        else{
            passUsuario = editContrasenia.getText().toString();
            Contrasenia.setText(passUsuario);

            guardarButton.setVisibility(View.GONE);
            fotoButton.setVisibility(View.VISIBLE);
            editUserButton.setVisibility(View.VISIBLE);
            editContrasenia.setVisibility(View.GONE);
            Contrasenia.setVisibility(View.VISIBLE);

            String finalJson = "";

            JSONObject userRequest = new JSONObject();
            try {
                userRequest.put("password", passUsuario);
                userRequest.put("email", finalEmail);

                finalJson = userRequest.toString();
            } catch (JSONException ex) {
                System.out.println("Error al crear JSON: " + ex);
            }
            new savePass().execute(finalJson);
        }



    }



    public void EditUser(){

        fotoButton.setVisibility(View.GONE);
        editUserButton.setVisibility(View.GONE);
        guardarButton.setVisibility(View.VISIBLE);

        //Usuario.setVisibility(View.GONE);
        //editUsuario.setVisibility(View.VISIBLE);
        //editUsuario.setText(Usuario.getText());


        Contrasenia.setVisibility(View.GONE);
        editContrasenia.setVisibility(View.VISIBLE);
        editContrasenia.setText(Contrasenia.getText());

    }



    private void SelectImage(){

        final CharSequence[] items={"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Add Image");



        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                mSelectedImageUri = getImageUri(mContext,bmp);
                imageV.setImageURI(mSelectedImageUri);

            }else if(requestCode==SELECT_FILE){

                mSelectedImageUri = data.getData();
                imageV.setImageURI(mSelectedImageUri);
            }

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private class savePass extends AsyncTask<String, Long, String> {


        protected String doInBackground(String... data) {
            String jsonLogin = data[0].replace("{","%7B").replace("}","%7D");
            jsonLogin = "["+jsonLogin+"]";
            Log.i("jsonFinal",jsonLogin);
            return HttpRequest.sendGetRequest(FINAL_URL + SIGN_IN + jsonLogin);
        }

        protected void onPostExecute(String response) {

            if(response==null || response.equals("")) {
                Toast.makeText(mContext, "Error from web service", Toast.LENGTH_SHORT).show();
            }
            else if(response.equals(ERROR_FROM_NETWORK_NOT_CONNECTED)) {
                Toast.makeText(mContext, "ERROR_FROM_NETWORK_NOT_CONNECTED", Toast.LENGTH_SHORT).show();

            }
            else {
                if (response.contains("Error") && response.contains("Message")) {
                    try {
                        JSONObject json = new JSONObject(response);
                        String message = json.getString("Message");
                        Toast.makeText(mContext, "error: " + message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.i("MyTAG: ", response);
                    CheckIfCorrect(response);

                }
            }

        }

        private void CheckIfCorrect(String response){

            String jsonObjects[] = new String[7];
            try{
                JSONArray array = new JSONArray(response);


                JSONObject jsonObj = array.getJSONObject(0);
                jsonObjects[0] = jsonObj.getString("email");
                jsonObjects[1] = jsonObj.getString("password");
                jsonObjects[2] = jsonObj.getString("respuesta");


            } catch(JSONException e){
                System.out.println("Error al parsear json "+ e);
            }
            Log.i("Respuesta: ", response);
            if(jsonObjects[2].equals("1")){
                Toast.makeText(mContext, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(mContext, "Error al actualizar contraseña, intente más tarde", Toast.LENGTH_SHORT).show();
            }


        }
    }



}
