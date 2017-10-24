package com.example.terminal.proyecto.apppt;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
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
import java.nio.BufferUnderflowException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.terminal.proyecto.apppt.R.layout.activity_menu;
import static com.example.terminal.proyecto.apppt.R.layout.design_menu_item_action_area;
import static com.example.terminal.proyecto.apppt.R.layout.perfil;


/**
 * Created by Jair Moreno on 05/08/2017.
 */

public class Perfil_fragment extends Fragment {



    private TabLayout tabLayout;
    private ViewPager firstViewPager;
    private CircleImageView circleImageView;

    public Perfil_fragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //tab inside fragment
        //https://stackoverflow.com/questions/44561180/tab-fragments-inside-bottom-navigation-fragment
        View rootView = inflater.inflate(R.layout.perfil, container, false);

        firstViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(firstViewPager);

        //Set Einstein image https://github.com/hdodenhof/CircleImageView

        setupViewPager(firstViewPager);
        return rootView;
        //return inflater.inflate(perfil, container, false);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new Info_personal(), "Perfil");
        adapter.addFragment(new Graficas(), "Estadísticas");
        adapter.addFragment(new Logros(), "Logros");
        viewPager.setAdapter(adapter);
    }

    public static Bitmap generateBorders(ImageView imageView){
        Bitmap mbitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 100, 100, mpaint);// Round Image Corner 100 100 100 100
        return imageRounded;
    }

    public void checkProfileImage(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int imageValue = preferences.getInt("imageValue", 0);

        switch (imageValue){
            case 0:
                circleImageView.setImageResource(R.mipmap.alberteinstein);
                break;
            case 1:
                circleImageView.setImageResource(R.mipmap.tesla);
                break;
            case 2:
                circleImageView.setImageResource(R.mipmap.house);
                break;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Check profile whats the current image profile
        circleImageView = (CircleImageView) getActivity().findViewById(R.id.profile_image);
        circleImageView.setImageResource(R.mipmap.alberteinstein);
        checkProfileImage();


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.image_profile_container, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        final GridView gridView = (GridView) dialogView.findViewById(R.id.gridviewProfile);
        gridView.setAdapter(new ImageProfileAdapter(getActivity()));

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setTitle("¡Cambia tu imagen de perfil!");
                alertDialog.show();
                //alertDialog.getWindow().setLayout(500,600);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                        SharedPreferences.Editor imageValue = preferences.edit();

                        switch (position) {
                            case 0:
                                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(getActivity());
                                alertDialog2.setMessage("¡Esta insignia va de nuestra parte!");
                                alertDialog2.setTitle("¡Hola!").show();
                                imageValue.putInt("imageValue",0);
                                circleImageView.setImageResource(R.mipmap.alberteinstein);
                                break;
                            case 1:
                                alertDialog.setMessage("Necesitas 100 puntos para desbloquear esta insignia");
                                alertDialog.setTitle("Ups").show();
                                imageValue.putInt("imageValue",1);
                                circleImageView.setImageResource(R.mipmap.tesla);
                                break;
                            case 2:
                                alertDialog.setMessage("Necesitas 150 puntos para desbloquear esta insignia");
                                alertDialog.setTitle("Ups").show();
                                imageValue.putInt("imageValue",2);
                                circleImageView.setImageResource(R.mipmap.house);
                                break;
                            case 3:
                                alertDialog.setMessage("Necesitas 200 puntos para desbloquear esta insignia");
                                alertDialog.setTitle("Ups").show();
                                break;
                            case 4:
                                alertDialog.setMessage("Necesitas 250 puntos para desbloquear esta insignia");
                                alertDialog.setTitle("Ups").show();
                                break;
                            case 5:
                                alertDialog.setMessage("Necesitas 300 puntos para desbloquear esta insignia");
                                alertDialog.setTitle("Ups").show();
                                break;
                            case 6:
                                alertDialog.setMessage("Necesitas 350 puntos para desbloquear esta insignia");
                                alertDialog.setTitle("Ups").show();
                                break;
                        }

                        imageValue.apply();
                    }
                });

            }
        });

    }

    public void customAlertDialog(){

    }


    public class ImageProfileAdapter extends BaseAdapter {
        private Context mContext;
        public int puntos = 100;

        public ImageProfileAdapter(Context c){
            mContext = c;
        }

        public int getCount(){
            return Insignia().length;
        }

        public Object getItem(int position){
            return null;
        }

        public long getItemId(int position){
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent){
            CircleImageView circleImageView = new CircleImageView(mContext);
            circleImageView.setLayoutParams(new GridView.LayoutParams(85,85));
            circleImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            circleImageView.setPadding(8,8,8,8);
            circleImageView.setImageResource(Insignia()[position]);
            return circleImageView;
        }

        public Integer[] Insignia(){
            Integer[] misLogros = {
                    R.mipmap.alberteinstein,
                    R.mipmap.tesla,
                    R.mipmap.tesla,
                    R.mipmap.tesla,
                    R.mipmap.tesla,
                    R.mipmap.tesla
            };

            return misLogros;
        }
    }



}
