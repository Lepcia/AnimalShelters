package inzynierka.animalshelters.activities.basic;

import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.helpers.Modules;
import inzynierka.animalshelters.models.ModuleDetailsModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class BasicActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;

    protected void onCreateDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_dehaze_white_18dp);

        drawerLayout = findViewById(R.id.drawer);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        drawerLayout.closeDrawers();
                        switch (menuItem.getItemId())
                        {
                            case Modules.ADMIN:
                                openAdminModule();
                                break;
                            case Modules.ANIMAL_SHELTERS:
                                openSheltersModule();
                                break;
                            case Modules.ANIMALS:
                                openAnimalsModule();
                                break;
                            case Modules.FAV_ANIMALS:
                                openFavAnimalsModule();
                                break;
                            case Modules.SEARCH:
                                openSearchModule();
                                break;
                        }
                        return true;
                    }
                });

        drawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(@NonNull View view, float v) {

                    }

                    @Override
                    public void onDrawerOpened(@NonNull View view) {

                    }

                    @Override
                    public void onDrawerClosed(@NonNull View view) {

                    }

                    @Override
                    public void onDrawerStateChanged(int i) {

                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);
        onCreateDrawer();
    }

    protected void onCreateDrawerMenu()
    {
        NavigationView navigationView = findViewById(R.id.nav_view);
        final Menu menu = navigationView.getMenu();

        menu.clear();
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));
        //TODO:
        //po zrobieniu logowania zmienić na zalogowanego usera
        Client.getById(BasicActivity.this, Api.ADMIN_USER_MODULE, 1, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                ModuleDetailsModel module = new ModuleDetailsModel(jsonObject);

                                menu.add(0, module.getId(), module.getOrder(), module.getName()).setIcon(getDrawable(module.getIcon()));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });

    }

    private int getDrawable(String name)
    {
        Resources res = getResources();
        int resId = res.getIdentifier(name, "drawable", getPackageName());
        return resId;
    }

    public void openMainModule()
    { }

    public void openAdminModule()
    { }

    public void openFavAnimalsModule()
    { }

    public void openSearchModule()
    { }

    public void openAnimalsModule()
    {}

    public void openSheltersModule()
    {}


}
