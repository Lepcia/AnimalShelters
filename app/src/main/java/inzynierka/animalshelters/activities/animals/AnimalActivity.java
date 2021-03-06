package inzynierka.animalshelters.activities.animals;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.NewsBoardActivity;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.UserService;
import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.administration.AdminEditUser;
import inzynierka.animalshelters.activities.animalShelters.SheltersActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.photos.PhotosActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;
import inzynierka.animalshelters.activities.settings.SettingsActivity;
import inzynierka.animalshelters.adapters.PhotoSliderAdapter;
import inzynierka.animalshelters.models.AnimalShelterModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class AnimalActivity extends BasicActivity {

    Context _context;
    ViewPager viewPager;
    private static String MALE = "Male";
    private static String FEMALE = "Female";
    private static String DOG = "Dog";
    private static String CAT = "Cat";
    private ArrayList<String> photos;
    private PhotoSliderAdapter photoSliderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);
        onCreateDrawer();
        onCreateDrawerMenu();
        this._context = this;

        Bundle bundle = getIntent().getExtras();
        if(bundle.getInt("AnimalId") > 0) {
            TextView animalIdTextView = findViewById(R.id.animal_id);
            int animalId = bundle.getInt("AnimalId");
            animalIdTextView.setText(String.valueOf(animalId));
            getAnimalById(animalId);

            viewPager = findViewById(R.id.photoSlider);

            photoSliderAdapter = new PhotoSliderAdapter(_context, photos);
            viewPager.setAdapter(photoSliderAdapter);

            getAnimalPhotos(animalId);
        }
    }

    private void getAnimalById(int id)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(this, Api.ANIMAL_ID_DETAILS_URL, id, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        setFormData(response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });
    }

    private void getAnimalPhotos(int id)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(this, Api.PHOTOS_BY_ID, id, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        photos = new ArrayList<>();
                        for(int i = 0; i < response.length(); i++)
                        {
                            try{
                                JSONObject photo = response.getJSONObject(i);
                                photos.add(photo.getString("content"));
                            }
                            catch(JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        photoSliderAdapter = new PhotoSliderAdapter(_context, photos);
                        viewPager.setAdapter(photoSliderAdapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });

    }

    private void setFormData(JSONObject data)
    {
        try {
            TextView animalName = findViewById(R.id.animal_name);
            animalName.setText(data.getString("name"));

            TextView animalShelterTV = findViewById(R.id.animal_shelter_name);
            JSONObject animalShelter = data.getJSONObject("animalShelter");
            if(animalShelter != null)
            {
                animalShelterTV.setText(animalShelter.getString("name"));
            }

            TextView breed = findViewById(R.id.animal_breed);
            breed.setText(data.getString("breed"));

            TextView age = findViewById(R.id.animal_age);
            age.setText(data.getString("ageString"));

            TextView size = findViewById(R.id.animal_size);
            size.setText(data.getString("size"));

            TextView description = findViewById(R.id.animal_description);
            description.setText(data.getString("description"));

            ImageView sex = findViewById(R.id.animal_sex);
            String sexS = data.getString("sex");
            if(sexS.equals(MALE)) sex.setImageResource(R.drawable.male_brown);
            else if(sexS.equals(FEMALE)) sex.setImageResource(R.drawable.woman_brown);

            ImageView species = findViewById(R.id.animal_species);
            String speciesS = data.getString("species");
            if(speciesS.equals(DOG)) species.setImageResource(R.drawable.dog_brown_big);
            else if (speciesS.equals(CAT)) species.setImageResource(R.drawable.cat_brown_big);

        }
        catch (JSONException e)
        {
            Log.e("Error", e.getMessage());
        }
    }

    @Override
    public void openMainModule()
    {
        Intent intent = new Intent(AnimalActivity.this, NewsBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(AnimalActivity.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSearchModule()
    {
        Intent intent = new Intent(AnimalActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAnimalsModule()
    {
        Intent intent = new Intent(AnimalActivity.this, AnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSheltersModule()
    {
        Intent intent = new Intent(AnimalActivity.this, SheltersActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(AnimalActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSettingsModule()
    {
        Intent intent = new Intent(AnimalActivity.this, SettingsActivity.class);
        int userId = UserService.getInstance().getmUserId();
        int shelterId = UserService.getInstance().getmShelterId();
        intent.putExtra("ShelterId", shelterId);
        intent.putExtra("UserId", userId);
        startActivity(intent);
    }

    @Override
    public void openPhotosModule()
    {
        Intent intent = new Intent(AnimalActivity.this, PhotosActivity.class);
        int shelterId = UserService.getInstance().getmShelterId();
        intent.putExtra("ShelterId", shelterId);
        startActivity(intent);
    }
}
