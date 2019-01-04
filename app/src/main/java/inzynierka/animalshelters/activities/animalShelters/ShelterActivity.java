package inzynierka.animalshelters.activities.animalShelters;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.NewsBoardActivity;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.animals.AnimalActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.photos.PhotosActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;
import inzynierka.animalshelters.activities.settings.SettingsActivity;
import inzynierka.animalshelters.adapters.PhotoSliderAdapter;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class ShelterActivity extends BasicActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter);
        onCreateDrawer();
        onCreateDrawerMenu();

        Bundle bundle = getIntent().getExtras();
        if(bundle.getInt("ShelterId") > 0) {
            TextView animalIdTextView = findViewById(R.id.shelter_id);
            int shelterId = bundle.getInt("ShelterId");
            animalIdTextView.setText(String.valueOf(shelterId));
            viewPager = findViewById(R.id.photoSlider);

            PhotoSliderAdapter photoSliderAdapter = new PhotoSliderAdapter(this);
            viewPager.setAdapter(photoSliderAdapter);

            getShelterById(shelterId);
        }
    }

    private void getShelterById(int id)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(this, Api.ANIMAL_SHELTER_ID_DETAILS_URL, id, headers.toArray(new Header[headers.size()]),
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

    private void setFormData(JSONObject data)
    {
        try{
            TextView animalShelter = findViewById(R.id.shelter_name);
            animalShelter.setText(data.getString("name"));

            TextView street = findViewById(R.id.shelter_street);
            street.setText(data.getString("street"));

            TextView city = findViewById(R.id.shelter_city);
            city.setText(data.getString("city"));

            TextView postalCode = findViewById(R.id.shelter_postal);
            postalCode.setText(data.getString("postalCode"));

            TextView email = findViewById(R.id.shelter_email);
            email.setText(data.getString("email"));

            TextView phone = findViewById(R.id.shelter_phone);
            phone.setText(data.getString("phone"));
        }
        catch(JSONException e)
        {
            Log.e("Error", e.getMessage());
        }
    }

    @Override
    public void openMainModule()
    {
        Intent intent = new Intent(ShelterActivity.this, NewsBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(ShelterActivity.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSearchModule()
    {
        Intent intent = new Intent(ShelterActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAnimalsModule()
    {
        Intent intent = new Intent(ShelterActivity.this, AnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSheltersModule()
    {
        Intent intent = new Intent(ShelterActivity.this, SheltersActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(ShelterActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSettingsModule()
    {
        Intent intent = new Intent(ShelterActivity.this, SettingsActivity.class);
        intent.putExtra("ShelterId", 1);
        intent.putExtra("UserId", 1);
        startActivity(intent);
    }

    @Override
    public void openPhotosModule()
    {
        Intent intent = new Intent(ShelterActivity.this, PhotosActivity.class);
        intent.putExtra("ShelterId", 1);
        startActivity(intent);
    }
}
