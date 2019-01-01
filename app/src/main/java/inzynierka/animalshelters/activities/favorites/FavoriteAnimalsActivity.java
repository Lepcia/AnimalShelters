package inzynierka.animalshelters.activities.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.NewsBoardActivity;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.animalShelters.SheltersActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;
import inzynierka.animalshelters.activities.settings.SettingsActivity;
import inzynierka.animalshelters.adapters.AnimalListItemAdapter;
import inzynierka.animalshelters.models.AnimalDetailsModel;
import inzynierka.animalshelters.models.AnimalModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class FavoriteAnimalsActivity extends BasicActivity {

    private ListView favoriteAnimalView;
    private AnimalListItemAdapter animalListItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_animals);
        onCreateDrawer();
        onCreateDrawerMenu();

        getFavoriteAnimals();
    }

    private void getFavoriteAnimals() {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        //TODO: zmienić id po zrobieniu autoryzacji
        Client.getById(FavoriteAnimalsActivity.this, Api.FAVORITE_ANIMALS_URL, 1, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        ArrayList<AnimalDetailsModel> animalsArray = new ArrayList<>();
                        animalListItemAdapter = new AnimalListItemAdapter(FavoriteAnimalsActivity.this, animalsArray);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                animalListItemAdapter.add(new AnimalDetailsModel(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        favoriteAnimalView = (ListView) findViewById(R.id.favorite_animals_list);
                        favoriteAnimalView.setAdapter(animalListItemAdapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });
    }

    @Override
    public void openMainModule()
    {
        Intent intent = new Intent(FavoriteAnimalsActivity.this, NewsBoardActivity.class);
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

    @Override
    public void openSettingsModule()
    {
        Intent intent = new Intent(FavoriteAnimalsActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}
