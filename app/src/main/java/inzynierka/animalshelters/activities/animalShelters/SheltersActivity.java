package inzynierka.animalshelters.activities.animalShelters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import inzynierka.animalshelters.MainActivity;
import inzynierka.animalshelters.R;

import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;

public class SheltersActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelters);
        onCreateDrawer();
        onCreateDrawerMenu();
    }

    @Override
    public void openMainModule()
    {
        Intent intent = new Intent(SheltersActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(SheltersActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(SheltersActivity.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSearchModule()
    {
        Intent intent = new Intent(SheltersActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAnimalsModule()
    {
        Intent intent = new Intent(SheltersActivity.this, AnimalsActivity.class);
        startActivity(intent);
    }

}
