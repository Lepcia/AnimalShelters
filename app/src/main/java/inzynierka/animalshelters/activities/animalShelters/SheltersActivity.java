package inzynierka.animalshelters.activities.animalShelters;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Bundle;

import inzynierka.animalshelters.NewsBoardActivity;
import inzynierka.animalshelters.R;

import inzynierka.animalshelters.UserService;
import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.photos.PhotosActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;
import inzynierka.animalshelters.activities.settings.SettingsActivity;

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
        Intent intent = new Intent(SheltersActivity.this, NewsBoardActivity.class);
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

    //TODO: po logowaniu pobierać id usera i schroniska
    @Override
    public void openSettingsModule()
    {
        Intent intent = new Intent(SheltersActivity.this, SettingsActivity.class);
        int userId = UserService.getInstance().getmUserId();
        int shelterId = UserService.getInstance().getmUserId();
        intent.putExtra("ShelterId", shelterId);
        intent.putExtra("UserId", userId);
        startActivity(intent);
    }

    @Override
    public void openPhotosModule()
    {
        Intent intent = new Intent(SheltersActivity.this, PhotosActivity.class);
        int shelterId = UserService.getInstance().getmShelterId();
        intent.putExtra("ShelterId", shelterId);
        startActivity(intent);
    }

}
