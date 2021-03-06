package com.example.vachhani.place_order.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vachhani.place_order.Fragments.MenuFragment_;
import com.example.vachhani.place_order.Fragments.OrderFragment_;
import com.example.vachhani.place_order.Fragments.TableFragment_;
import com.example.vachhani.place_order.R;
import com.example.vachhani.place_order.Utils.Utility;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_menu_display)
public class MenuDisplayActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog pd;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigationView;


    @ViewById
    DrawerLayout drawer;


    @ViewById
    Toolbar toolbar;

    @ViewById
    TextView txtTitle;

    @ViewById
    LinearLayout llMenu;


    @AfterViews
    void init() {

        loads();
        pd = Utility.getDialog(this);

        //toolbar setting
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        txtTitle.setText(getString(R.string.select_menu));


        //setting of toggle in drawer
        mToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close);
        drawer.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.drawer);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //load default fragment of drawer activity.
        loadFragment(new MenuFragment_());


        //setting user info in drawer corner
        View header = navigationView.getHeaderView(0);
        TextView username = header.findViewById(R.id.username);
        TextView mobile = header.findViewById(R.id.mobile);
        username.setText(pref.userName().get());
        mobile.setText(pref.mobile_num().get());


    }

    //Method to load fragments as per drawer item selection.
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            txtTitle.setText(getString(R.string.select_menu));
            fragment = new MenuFragment_();

        } else if (id == R.id.nav_tables) {
            txtTitle.setText(getString(R.string.table));
            fragment = new TableFragment_();

        } else if (id == R.id.nav_order) {
            txtTitle.setText(getString(R.string.order));
            fragment = new OrderFragment_();

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBody = "Download the app place order from here !!1";
            String shareSub = "Download App";
            intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            intent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(intent, "Share Using"));


        } else if (id == R.id.nav_logout) {

            pref.clear();

            Toast.makeText(this, "Signed Out Successfully !!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(MenuDisplayActivity.this, RegistrationActivity_.class));

        }
        if (fragment != null) {
            loadFragment(fragment);

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
