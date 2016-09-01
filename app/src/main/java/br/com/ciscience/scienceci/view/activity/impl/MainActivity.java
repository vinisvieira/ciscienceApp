package br.com.ciscience.scienceci.view.activity.impl;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.ciscience.scienceci.R;
import br.com.ciscience.scienceci.model.entity.impl.Student;
import br.com.ciscience.scienceci.presenter.IUserPresenter;
import br.com.ciscience.scienceci.presenter.impl.UserPresenter;
import br.com.ciscience.scienceci.util.Constants;
import br.com.ciscience.scienceci.util.SystemServices;
import br.com.ciscience.scienceci.view.activity.IActivity;
import br.com.ciscience.scienceci.view.fragment.IDialog;
import br.com.ciscience.scienceci.view.fragment.IUserView;
import br.com.ciscience.scienceci.view.fragment.impl.ContactFragment;
import br.com.ciscience.scienceci.view.fragment.impl.ProfileFragment;
import br.com.ciscience.scienceci.view.fragment.impl.QuizFragment;
import br.com.ciscience.scienceci.view.fragment.impl.RankingFragment;
import br.com.ciscience.scienceci.view.fragment.impl.RulesFragment;
import br.com.ciscience.scienceci.view.fragment.impl.StudentLogoutDialog;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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

    private void syncDrawerMenu() {
        if (!this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                syncDrawerMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeDrawerMenu() {
        this.drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean isCurrentOnDisplay(int idFragment) {
        return (this.mCurrentFragment == idFragment);
    }

    @Override
    public void showFragment(int idFragment) {
        switch (idFragment) {
            case Constants.QUIZ_FRAGMENT:
                if (!this.isCurrentOnDisplay(Constants.QUIZ_FRAGMENT)) {
                    this.mCurrentFragment = Constants.QUIZ_FRAGMENT;

                    this.setCheckedItemNavigationView(Constants.QUIZ_FRAGMENT);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new QuizFragment())
                            .commit();
                    SystemServices.changeToolbarTitle(MainActivity.this, getResources().getString(R.string.drawer_menu_quiz));
                }
                new Handler().postDelayed(this::closeDrawerMenu, 120);
                break;

            case Constants.PROFILE_FRAGMENT:
                if (!this.isCurrentOnDisplay(Constants.PROFILE_FRAGMENT)) {
                    this.mCurrentFragment = Constants.PROFILE_FRAGMENT;

                    this.setCheckedItemNavigationView(Constants.PROFILE_FRAGMENT);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new ProfileFragment())
                            .commit();
                    SystemServices.changeToolbarTitle(MainActivity.this, getResources().getString(R.string.drawer_menu_profile));
                }
                new Handler().postDelayed(this::closeDrawerMenu, 120);
                break;

            case Constants.RANKING_FRAGMENT:
                if (!this.isCurrentOnDisplay(Constants.RANKING_FRAGMENT)) {
                    this.mCurrentFragment = Constants.RANKING_FRAGMENT;

                    this.setCheckedItemNavigationView(Constants.RANKING_FRAGMENT);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new RankingFragment())
                            .commit();
                    SystemServices.changeToolbarTitle(MainActivity.this, getResources().getString(R.string.drawer_menu_ranking));
                }
                new Handler().postDelayed(this::closeDrawerMenu, 120);
                break;

            case Constants.CONTACT_FRAGMENT:
                if (!this.isCurrentOnDisplay(Constants.CONTACT_FRAGMENT)) {
                    this.mCurrentFragment = Constants.CONTACT_FRAGMENT;

                    this.setCheckedItemNavigationView(Constants.CONTACT_FRAGMENT);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new ContactFragment())
                            .commit();
                    SystemServices.changeToolbarTitle(MainActivity.this, getResources().getString(R.string.drawer_menu_contact));
                }
                new Handler().postDelayed(this::closeDrawerMenu, 120);
                break;

            case Constants.RULES_FRAGMENT:
                if (!this.isCurrentOnDisplay(Constants.RULES_FRAGMENT)) {
                    this.mCurrentFragment = Constants.RULES_FRAGMENT;

                    this.setCheckedItemNavigationView(Constants.RULES_FRAGMENT);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new RulesFragment())
                            .commit();
                    SystemServices.changeToolbarTitle(MainActivity.this, getResources().getString(R.string.drawer_menu_rules));
                }
                new Handler().postDelayed(this::closeDrawerMenu, 120);
                break;

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
            case Constants.QUIZ_FRAGMENT:
                this.navigationView.setCheckedItem(R.id.drawer_menu_quiz);
                break;
            case Constants.PROFILE_FRAGMENT:
                this.navigationView.setCheckedItem(R.id.drawer_menu_profile);
                break;
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

        View headerView = this.navigationView.getHeaderView(0);

        TextView mStudentName = (TextView) headerView.findViewById(R.id.text_view_student_name);
        CircleImageView circleImageViewStudentPicture = (CircleImageView) headerView.findViewById(R.id.circleImageViewProfile);
        ImageView imageViewBadge = (ImageView) headerView.findViewById(R.id.imageViewBadge);
        mStudentName.setText(this.mIUserPresenter.getSession().getName());

        if (this.mIUserPresenter.getSession().getMyFile() != null) {
            Picasso
                    .with(MainActivity.this)
                    .load(Constants.BASE_URL + Constants.DATAFILE_URL + this.mIUserPresenter.getSession().getMyFile().getName())
                    .resize(1000, 1000)
                    .onlyScaleDown()
                    .into(circleImageViewStudentPicture);
        }

        Picasso.with(MainActivity.this)
                .load(getResDrawable(this.mIUserPresenter.getSession().getScore().intValue()))
                .into(imageViewBadge);

        this.showFragment(Constants.QUIZ_FRAGMENT);
    }

    public int getResDrawable(int score) {
        if (score <= 25 && score < 35) {
            return R.drawable.b1;
        } else if (score >= 35 && score < 45) {
            return R.drawable.b2;
        } else if (score >= 45 && score < 50) {
            return R.drawable.b3;
        } else if (score >= 50 && score < 100) {
            return R.drawable.b4;
        } else if (score >= 100 && score < 140) {
            return R.drawable.p1;
        } else if (score >= 140 && score < 180) {
            return R.drawable.p2;
        } else if (score >= 180 && score < 220) {
            return R.drawable.p3;
        } else if (score >= 220 && score < 250) {
            return R.drawable.p4;
        } else if (score >= 250 && score < 300) {
            return R.drawable.o1;
        } else if (score >= 300 && score < 400) {
            return R.drawable.o2;
        } else if (score >= 400 && score < 500) {
            return R.drawable.o3;
        } else if (score >= 500) {
            return R.drawable.o4;
        } else {
            return R.drawable.b1;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_menu_quiz:
                this.showFragment(Constants.QUIZ_FRAGMENT);
                return true;
            case R.id.drawer_menu_profile:
                this.showFragment(Constants.PROFILE_FRAGMENT);
                return true;
            case R.id.drawer_menu_rules:
                this.showFragment(Constants.RULES_FRAGMENT);
                return true;
            case R.id.drawer_menu_ranking:
                this.showFragment(Constants.RANKING_FRAGMENT);
                return true;
            case R.id.drawer_menu_contact:
                this.showFragment(Constants.CONTACT_FRAGMENT);
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
    public void recoveryPassword() {

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
