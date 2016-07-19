package br.com.ciscience.scienceci.model.dao.local.impl;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.com.ciscience.scienceci.model.dao.local.IUserLocalAPI;
import br.com.ciscience.scienceci.model.entity.impl.Student;

/**
 * Created by pedrodimoura on 19/07/16.
 */
public class UserLocalAPI implements IUserLocalAPI {

    private static final String KEY_SHARED_PREFERENCES = "USER_SESSION";

    private Context mContext;

    public UserLocalAPI(Context context) {
        this.mContext = context;
    }

    @Override
    public void setSession(Student student) {
        SharedPreferences.Editor sharedPrefsEditor = this.mContext.getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
        sharedPrefsEditor.putString(KEY_SHARED_PREFERENCES, new Gson().toJson(student));
        sharedPrefsEditor.apply();
    }

    @Override
    public Student getSession() {
        return new Gson().fromJson(this.mContext.getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE).getString(KEY_SHARED_PREFERENCES, null), Student.class);
    }

    @Override
    public void destroySession() {
        SharedPreferences.Editor editor = this.mContext.getSharedPreferences(KEY_SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }
}
