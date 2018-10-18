package inzynierka.animalshelters.activities.favorites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import inzynierka.animalshelters.MainActivity;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.animalShelters.SheltersActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;

public class FavoriteAnimalsActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_animals);
        onCreateDrawer();
        onCreateDrawerMenu();
    }

    @Override
    public void openMainModule()
    {
        Intent intent = new Intent(FavoriteAnimalsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(FavoriteAnimalsActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSearchModule()
    {
        Intent intent = new Intent(FavoriteAnimalsActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAnimalsModule()
    {
        Intent intent = new Intent(FavoriteAnimalsActivity.this, AnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSheltersModule()
    {
        Intent intent = new Intent(FavoriteAnimalsActivity.this, SheltersActivity.class);
        startActivity(intent);
    }
}
