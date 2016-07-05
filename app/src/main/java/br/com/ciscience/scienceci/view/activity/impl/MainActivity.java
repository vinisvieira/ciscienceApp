package br.com.ciscience.scienceci.view.activity.impl;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.view.activity.IActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IActivity, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.navigationView) NavigationView navigationView;
    @BindView(R.id.activityMainToolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        setSupportActionBar(toolbar);
        setActionBarDrawerToggle();
        loadDefaultMenu();
    }

    @Override
    public void setActionBarDrawerToggle() {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            case R.id.drawer_menu_profile:
                return true;
            case R.id.drawer_menu_rules:
                return true;
            case R.id.drawer_menu_ranking:
                return true;
            case R.id.drawer_menu_contact:
                return true;
            case R.id.drawer_menu_logout:
                return true;
        }
        return false;
    }
}
