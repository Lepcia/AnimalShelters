package inzynierka.animalshelters.activities.photos;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
import inzynierka.animalshelters.activities.animals.AnimalActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;
import inzynierka.animalshelters.activities.settings.SettingsActivity;
import inzynierka.animalshelters.adapters.PhotoGalleryAdapter;
import inzynierka.animalshelters.adapters.PhotoListItemAdapter;
import inzynierka.animalshelters.models.AnimalShelterSimpleModel;
import inzynierka.animalshelters.models.AnimalSimpleModel;
import inzynierka.animalshelters.models.PhotoModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class PhotosActivity extends BasicActivity {

    Context _context;
    private ListView photoView;
    private PhotoListItemAdapter photoListItemAdapter;
    private int ShelterId = -1;
    private ArrayList<PhotoModel> photosArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        this._context = this;
        onCreateDrawer();
        onCreateDrawerMenu();
        addListenerOnAddPhotoBtn();

        Bundle bundle = getIntent().getExtras();
        if(bundle.getInt("ShelterId") > 0) {
            ShelterId = bundle.getInt("ShelterId");
            getSimpleAnimals(ShelterId);
        }
    }

    private void getSimpleAnimals(int idShelter)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(_context, Api.ANIMAL_SHELTER_ANIMALS_SIMPLE_URL, idShelter, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        List<String> animalsNames = new ArrayList<String>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                AnimalSimpleModel animal = new AnimalSimpleModel(response.getJSONObject(i));
                                animalsNames.add(animal.getName() + " - " + animal.getId());
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        Spinner spinner = findViewById(R.id.search_animals);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(_context,
                                android.R.layout.simple_spinner_item, animalsNames);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(dataAdapter);
                        addListenerOnSpinnerItemSelection();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });
    }

    public void addListenerOnAddPhotoBtn()
    {
        FloatingActionButton addPhotoBtn = findViewById(R.id.addPhotoBtn);
        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, AddPhotosActivity.class);
                intent.putExtra("ShelterId", ShelterId);
                startActivity(intent);
            }
        });
    }

    public void addListenerOnSpinnerItemSelection(){
        Spinner spinner = findViewById(R.id.search_animals);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selected = findViewById(R.id.selected_animal);
                selected.setText(parent.getItemAtPosition(position).toString());
                getAnimalPhotos(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                TextView selected = findViewById(R.id.selected_animal);
                selected.setText("");
            }
        });
    }

    private void getAnimalPhotos(String animal)
    {
        String animalName = animal.substring(0, animal.indexOf(" - "));
        String animalId = animal.substring(animal.indexOf("-")+2);
        int id_animal = Integer.parseInt(animalId);

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(_context, Api.PHOTOS_BY_ID, id_animal, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        photosArray = new ArrayList<>();
                        photoListItemAdapter = new PhotoListItemAdapter(_context, photosArray);

                        for(int i = 0; i < response.length(); i++)
                        {
                            try{
                                PhotoModel photo = new PhotoModel(response.getJSONObject(i));
                                photosArray.add(photo);
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        photoView = (ListView) findViewById(R.id.photos_list);
                        photoView.setAdapter(photoListItemAdapter);

                    }
                });
    }

    @Override
    public void openMainModule()
    {
        Intent intent = new Intent(PhotosActivity.this, NewsBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(PhotosActivity.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSearchModule()
    {
        Intent intent = new Intent(PhotosActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAnimalsModule()
    {
        Intent intent = new Intent(PhotosActivity.this, AnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSheltersModule()
    {
        Intent intent = new Intent(PhotosActivity.this, SheltersActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(PhotosActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSettingsModule()
    {
        Intent intent = new Intent(PhotosActivity.this, SettingsActivity.class);
        intent.putExtra("ShelterId", 1);
        intent.putExtra("UserId", 1);
        startActivity(intent);
    }
}
