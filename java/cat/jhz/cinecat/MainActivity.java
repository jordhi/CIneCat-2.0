/*
Copyright (C) <2017>  <Jordi Hernandez>
Twitter: @jordikarate
Web: http://www.jordihernandez.cat/cinecat

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package cat.jhz.cinecat;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;

import cat.jhz.cinecat.config.AdsConfig;
import cat.jhz.cinecat.dades.Cicle;
import cat.jhz.cinecat.dades.Cinema;
import cat.jhz.cinecat.dades.Film;
import cat.jhz.cinecat.vistes.MapsActivity;
import cat.jhz.cinecat.vistes.fragment_Infocinecat;
import cat.jhz.cinecat.vistes.fragment_cicles;
import cat.jhz.cinecat.vistes.fragment_cines;
import cat.jhz.cinecat.vistes.fragment_films;
import cat.jhz.cinecat.vistes.fragment_mesInfo;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private Menu menuNav;

    private Film film_selected;
    private Cinema cine_selected;
    private Cicle cicle_selected = null;

    private TextView info;
    private boolean doubleBackPressed = false;


    @Override
    @SuppressWarnings("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, AdsConfig.ID_ADS);
        //MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();


        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        menuNav = nvDrawer.getMenu();

       // info = (TextView) findViewById(R.id.txvInfoBottomActivity);


        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                if(extras.getBoolean(getString(R.string.uptaded_data))) Toast.makeText(MainActivity.this, "Actualitzat", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }

                });
    }

    public void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();

    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;

        switch(menuItem.getItemId()) {
            case R.id.nav_films_fragment:
                fragmentClass = fragment_films.class;
                cicle_selected = null;
                break;
            case R.id.nav_cines_fragment:
                fragmentClass = fragment_cines.class;
                break;
            case R.id.nav_cicles_fragment:
                fragmentClass = fragment_cicles.class;
                break;
            case R.id.nav_infocinecat:
                fragmentClass = fragment_Infocinecat.class;
                break;
            case R.id.nav_sortir:
                finish();
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Insert the fragment by replacing any existing fragment
        switchFragment(fragment);
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer

        mDrawer.closeDrawers();
    }

    public void setActionBarTitle(String title) {
        setTitle(title);
    }

    public void setItemMenuNav(int id) {
        menuNav.getItem(id).setChecked(true);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
       /* switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }*/
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void setFilm_selected(Film film_selected) {
        this.film_selected = film_selected;
    }

    public Film getFilm_selected() {
        return film_selected;
    }

    public Cinema getCine_selected() {
        return cine_selected;
    }

    public void setCine_selected(Cinema cine_selected) {
        this.cine_selected = cine_selected;
    }

    public Cicle getCicle_selected() {
        return cicle_selected;
    }

    public void setCicle_selected(Cicle cicle_selected) {
        this.cicle_selected = cicle_selected;
    }

    public void clickInfo(View v) {
        DialogFragment fragment = new fragment_mesInfo();
        fragment.show(getSupportFragmentManager(),"Info");
    }

    @Override
    public void onBackPressed() {
        if(doubleBackPressed) {
            super.onBackPressed();
            finish();
        }
        FragmentManager fm = getSupportFragmentManager();
        if (mDrawer.isDrawerOpen(GravityCompat.START)) mDrawer.closeDrawer(GravityCompat.START);
        else if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
        doubleBackPressed = true;
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackPressed = false;
            }
        },1000);
    }

    public void setBottomInfo(int color, @Nullable String text) {
        if(text!=null) info.setText(text);
        info.setBackgroundColor(color);
    }

    public void goMap(View v) {
        Intent i = new Intent(this,MapsActivity.class);
        i.putExtra("cineNom",getCine_selected().getNom());
        i.putExtra("addrCine",getCine_selected().getAdreca());
        i.putExtra("PoblacioCine",getCine_selected().getLocalitat());
        startActivity(i);
    }
}
