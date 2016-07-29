package br.com.ciscience.scienceci.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pedrodimoura on 20/07/16.
 */
public class SystemServices {

    public static void changeToolbarTitle(Context context, String newTitle) {
        ((AppCompatActivity) context).getSupportActionBar().setTitle(newTitle);
    }

    /**
     * Verifica se a aplicação requisitada está instalada
     * @param packagename Nome do Pacote do APP
     * @param context Contexto da aplicação
     * @return um boolean que indica se a aplicação requisitada está instalada
     */
    public static boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
