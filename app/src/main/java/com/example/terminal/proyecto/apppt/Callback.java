package com.example.terminal.proyecto.apppt;

/**
 * Created by pc on 16/10/2017.
 */
//https://stackoverflow.com/questions/36143496/get-android-asynchttpclient-response-after-it-finish
public interface Callback <T> {
    void onResponse(T t);
}
