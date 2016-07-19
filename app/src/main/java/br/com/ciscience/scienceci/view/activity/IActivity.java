package br.com.ciscience.scienceci.view.activity;

/**
 * Created by pedrodimoura on 15/06/16.
 */
public interface IActivity {

    void showFragment(int idFragment);

    void setActionBarDrawerToggle();

    void setCheckedItemNavigationView(int currentFragment);

    void loadDefaultMenu();

}
