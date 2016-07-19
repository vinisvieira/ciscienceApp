package br.com.ciscience.scienceci.view.activity.impl;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Student;
import br.com.ciscience.scienceci.presenter.IUserPresenter;
import br.com.ciscience.scienceci.presenter.impl.UserPresenter;
import br.com.ciscience.scienceci.util.Constants;
import br.com.ciscience.scienceci.view.activity.IActivity;
import br.com.ciscience.scienceci.view.fragment.IDialog;
import br.com.ciscience.scienceci.view.fragment.IUserView;
import br.com.ciscience.scienceci.view.fragment.impl.StudentLogoutDialog;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IActivity, IUserView, IDialog, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.navigationView) NavigationView navigationView;
    @BindView(R.id.activityMainToolbar) Toolbar toolbar;

    private AppCompatDialogFragment mAppCompatDialogFragment;
    private IUserPresenter mIUserPresenter;

    private int mCurrentFragment = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        this.mIUserPresenter = new UserPresenter(MainActivity.this);
        setSupportActionBar(toolbar);
        setActionBarDrawerToggle();
        loadDefaultMenu();
    }

    private void closeDrawerMenu() {
        this.drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void showFragment(int idFragment) {
        switch (idFragment) {
            case Constants.LOGOUT_DIALOG_FRAGMENT:
                this.closeDrawerMenu();
                this.mAppCompatDialogFragment = new StudentLogoutDialog();
                this.mAppCompatDialogFragment.setCancelable(false);
                this.mAppCompatDialogFragment.show(this.getSupportFragmentManager(), null);
                break;
        }
    }

    @Override
    public void setActionBarDrawerToggle() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setCheckedItemNavigationView(int currentFragment) {
        switch (currentFragment) {
            case Constants.LOGOUT_DIALOG_FRAGMENT:
                this.navigationView.setCheckedItem(R.id.drawer_menu_quiz);
                break;
        }
    }

    @Override
    public void loadDefaultMenu() {
        this.navigationView.getMenu().clear();
        this.navigationView.inflateMenu(R.menu.menu_drawer_layout);
        this.navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_menu_quiz:
                return true;
            case R.id.drawer_menu_profile:
                return true;
            case R.id.drawer_menu_rules:
                return true;
            case R.id.drawer_menu_ranking:
                return true;
            case R.id.drawer_menu_contact:
                return true;
            case R.id.drawer_menu_logout:
                this.showFragment(Constants.LOGOUT_DIALOG_FRAGMENT);
                return true;
        }
        return false;
    }

    @Override
    public void onClickDialog(int dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                this.mIUserPresenter.destroySession();
                this.finishActivity();
                this.startMainActivity();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                this.setCheckedItemNavigationView(this.mCurrentFragment);
                break;
        }
    }

    @Override
    public void userLoggedIn() {

    }

    @Override
    public Student getSession() {
        return this.mIUserPresenter.getSession();
    }

    @Override
    public void login() {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showSnackbarMessage(String message, int duration) {

    }

    @Override
    public void showSnackbarMessage(int resId, int duration) {

    }

    @Override
    public Activity getFragmentActivity() {
        return MainActivity.this;
    }
}
