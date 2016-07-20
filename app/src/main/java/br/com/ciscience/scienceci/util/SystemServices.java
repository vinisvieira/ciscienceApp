package br.com.ciscience.scienceci.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pedrodimoura on 20/07/16.
 */
public class SystemServices {

    public static void changeToolbarTitle(Context context, String newTitle) {
        ((AppCompatActivity) context).getSupportActionBar().setTitle(newTitle);
    }

}
