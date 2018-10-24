package inzynierka.animalshelters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.administration.UserListActivity;
import inzynierka.animalshelters.activities.animalShelters.SheltersActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;

public class MainActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateDrawer();
        onCreateDrawerMenu();
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(MainActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(MainActivity.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void  openSearchModule()
    {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void openAnimalsModule()
    {
        Intent intent = new Intent(MainActivity.this, AnimalsActivity.class);
        startActivity(intent);
    }

    public void openSheltersModule()
    {
        Intent intent = new Intent(MainActivity.this, SheltersActivity.class);
        startActivity(intent);
    }
}
