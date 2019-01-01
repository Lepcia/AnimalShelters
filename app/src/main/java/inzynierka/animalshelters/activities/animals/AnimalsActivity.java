package inzynierka.animalshelters.activities.animals;

import android.content.Intent;
import android.os.Bundle;

import inzynierka.animalshelters.NewsBoardActivity;
import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.animalShelters.SheltersActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;
import inzynierka.animalshelters.activities.settings.SettingsActivity;

public class AnimalsActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);
        onCreateDrawer();
        onCreateDrawerMenu();
    }

    @Override
    public void openMainModule()
    {
        Intent intent = new Intent(AnimalsActivity.this, NewsBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(AnimalsActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(AnimalsActivity.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSearchModule()
    {
        Intent intent = new Intent(AnimalsActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSheltersModule()
    {
        Intent intent = new Intent(AnimalsActivity.this, SheltersActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSettingsModule()
    {
        Intent intent = new Intent(AnimalsActivity.this, SettingsActivity.class);
        intent.putExtra("ShelterId", 1);
        intent.putExtra("UserId", 1);
        startActivity(intent);
    }
}
