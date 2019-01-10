package inzynierka.animalshelters.activities.photos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.NewsBoardActivity;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.UserService;
import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.animalShelters.SheltersActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;
import inzynierka.animalshelters.activities.settings.SettingsActivity;
import inzynierka.animalshelters.helpers.ImageHelper;
import inzynierka.animalshelters.models.AnimalSimpleModel;
import inzynierka.animalshelters.models.PhotoModel;
import inzynierka.animalshelters.models.RoleModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class AddPhotosActivity extends BasicActivity {

    Context _context;
    private static final int STORAGE_PERMISSION_CODE = 2342;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photos);
        this._context = this;
        onCreateDrawer();
        onCreateDrawerMenu();
        addOnSaveBtnListener();
        addListenerOnUploadPhoto();
        requestStoragePermission();

        int ShelterId = UserService.getInstance().getmShelterId();
        RoleModel userRole = UserService.getInstance().getmUserRole();

        if(userRole.getSymbol().equals("ADMIN"))
        {
            getAllSimpleAnimals();
        }

        else if(ShelterId > 0) {
            getSimpleAnimals(ShelterId);
        }
    }

    private void getAllSimpleAnimals()
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.get(_context, Api.ANIMALS_SIMPLE_URL, headers.toArray(new Header[headers.size()]),
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

    private void addPhoto(PhotoModel photo)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Gson gson = new Gson();
        String jsonString = gson.toJson(photo);
        StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");

        Client.add(_context, Api.PHOTOS, stringEntity, headers.toArray(new Header[headers.size()]), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Intent intent = new Intent(_context, PhotosActivity.class);
                intent.putExtra("ShelterId", 1);
                _context.startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.e("Error", res);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                TextView selected = findViewById(R.id.selected_animal);
                selected.setText("");
            }
        });
    }

    public void addOnSaveBtnListener()
    {
        FloatingActionButton saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveBtnClick();
            }
        });
    }

    private void onSaveBtnClick()
    {
        PhotoModel photo = new PhotoModel();

        TextView title = findViewById(R.id.edit_title);
        photo.setTitle(title.getText().toString());

        TextView selected_animal = findViewById(R.id.selected_animal);
        String animal = selected_animal.getText().toString();
        int animalId = Integer.parseInt(animal.substring(animal.indexOf("-") + 2));

        photo.setAnimalId(animalId);

        if(bitmap != null)
        {
            String encodedPhoto = ImageHelper.encodeBitmap(bitmap);
            photo.setContent(encodedPhoto);
        }

        addPhoto(photo);
    }

    private void addListenerOnUploadPhoto()
    {
        Button uploadBtn = findViewById(R.id.addPhotoBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
    }

    private void requestStoragePermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(_context, "Permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(_context, "Permission not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showFileChooser()
    {
        Intent intent =  new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                if(bitmap != null) {
                    ImageView avatar = findViewById(R.id.animal_photo);
                    avatar.setImageBitmap(bitmap);
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void openMainModule()
    {
        Intent intent = new Intent(AddPhotosActivity.this, NewsBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(AddPhotosActivity.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSearchModule()
    {
        Intent intent = new Intent(AddPhotosActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAnimalsModule()
    {
        Intent intent = new Intent(AddPhotosActivity.this, AnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSheltersModule()
    {
        Intent intent = new Intent(AddPhotosActivity.this, SheltersActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(AddPhotosActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSettingsModule()
    {
        Intent intent = new Intent(AddPhotosActivity.this, SettingsActivity.class);
        int idUser = UserService.getInstance().getmUserId();
        int idShelter = UserService.getInstance().getmShelterId();
        intent.putExtra("ShelterId", idShelter);
        intent.putExtra("UserId", idUser);
        startActivity(intent);
    }
}
