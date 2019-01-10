package inzynierka.animalshelters.activities.animals;

import android.Manifest;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.NewsBoardActivity;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.UserService;
import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.animalShelters.SheltersActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.photos.PhotosActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;
import inzynierka.animalshelters.activities.settings.SettingsActivity;
import inzynierka.animalshelters.activities.settings.SettingsAnimals;
import inzynierka.animalshelters.helpers.DateFormatHelper;
import inzynierka.animalshelters.helpers.ImageHelper;
import inzynierka.animalshelters.models.AnimalDetailsModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class EditAnimalActivity extends BasicActivity {

    private static Context _context;
    private static final String DOG = "Dog";
    private static final String CAT = "Cat";
    private static final String MALE = "Male";
    private static final String FEMALE = "Female";
    private static final String SMALL = "Small";
    private static final String MEDIUM = "Medium";
    private static final String BIG = "Big";
    private static final int STORAGE_PERMISSION_CODE = 2342;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_animal);
        onCreateDrawer();
        onCreateDrawerMenu();
        this._context = this;
        addListenerOnSave();
        addListenerOnUploadPhoto();

        Bundle bundle = getIntent().getExtras();
        if(bundle.getInt("AnimalId") > 0)
        {
            getAnimalById(bundle.getInt("AnimalId"));
        }

        requestStoragePermission();
    }

    private void getAnimalById(int id)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(_context, Api.ANIMAL_ID_DETAILS_URL, id, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        setFormData(response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        super.onFailure(statusCode, headers, res, t);
                        Log.e("Error", res);
                    }
                });
    }

    private void setFormData(JSONObject data)
    {
        try {
            if(data.has("id")) {
                TextView animalId = findViewById(R.id.animal_id);
                animalId.setText(String.valueOf(data.getInt("id")));
            }

            if(data.has("name")) {
                EditText name = findViewById(R.id.edit_name);
                name.setText(data.getString("name"));
            }

            if(data.has("description")){
                EditText description = findViewById(R.id.edit_description);
                description.setText(data.getString("description"));
            }


            if(data.has("breed")) {
                EditText breed = findViewById(R.id.edit_breed);
                breed.setText(data.getString("breed"));
            }

            if(data.has("dateOfBirth")) {
                Date dateOfBirth = DateFormatHelper.dateFromString(data.getString("dateOfBirth"), DateFormatHelper.FORMAT_POSTGRES_DATE);
                String dateString = DateFormatHelper.stringFromDate(dateOfBirth, DateFormatHelper.FORMAT_POSTGRES_DATE);

                EditText user_birthday = findViewById(R.id.edit_birthday);
                user_birthday.setText(dateString);
            }

            if(data.has("inShelterFrom")) {
                Date dateOfBirth = DateFormatHelper.dateFromString(data.getString("inShelterFrom"), DateFormatHelper.FORMAT_POSTGRES_DATE);
                String dateString = DateFormatHelper.stringFromDate(dateOfBirth, DateFormatHelper.FORMAT_POSTGRES_DATE);

                EditText in_shelter_from = findViewById(R.id.edit_in_shelter_from);
                in_shelter_from.setText(dateString);
            }

            if(data.has("avatar"))
            {
                String encodedPhoto = data.getString("avatar");
                Bitmap bitmap = ImageHelper.getImageBitmap(encodedPhoto);
                ImageView animal_avatar = findViewById(R.id.animal_avatar);
                animal_avatar.setImageBitmap(bitmap);
            }

            JSONObject animalShelter = data.getJSONObject("animalShelter");
            if(animalShelter.getInt("id") > 0) {
                String shelterName = animalShelter.getString("name");
                TextView shelter = findViewById(R.id.selected_shelter);
                shelter.setText(shelterName);
            }

            if(data.has("species")) {
                switch (data.getString("species"))
                {
                    case DOG:
                        RadioButton radioDog = findViewById(R.id.radioDog);
                        radioDog.setChecked(true);
                        break;
                    case CAT:
                        RadioButton radioCat = findViewById(R.id.radioCat);
                        radioCat.setChecked(true);
                        break;
                }
            }

            if(data.has("sex")) {
                switch (data.getString("sex"))
                {
                    case FEMALE:
                        RadioButton radioFemale = findViewById(R.id.radioFemale);
                        radioFemale.setChecked(true);
                        break;
                    case MALE:
                        RadioButton radioMale = findViewById(R.id.radioMale);
                        radioMale.setChecked(true);
                        break;
                }
            }

            if(data.has("size")) {
                switch (data.getString("size"))
                {
                    case SMALL:
                        RadioButton radioSmall = findViewById(R.id.radioSmall);
                        radioSmall.setChecked(true);
                        break;
                    case MEDIUM:
                        RadioButton radioMedium = findViewById(R.id.radioMedium);
                        radioMedium.setChecked(true);
                        break;
                    case BIG:
                        RadioButton radioBig = findViewById(R.id.radioBig);
                        radioBig.setChecked(true);
                        break;
                }
            }

        }
        catch(JSONException e)
        {
            Log.e("Error", e.getMessage());
        }
    }

    private void onSaveBtnClick()
    {
        AnimalDetailsModel animal = new AnimalDetailsModel();
        TextView animalId = findViewById(R.id.animal_id);
        String id = animalId.getText().toString();
        if(id != "") {
            animal.setId(Integer.parseInt(animalId.getText().toString()));
        }
        EditText name = findViewById(R.id.edit_name);
        animal.setName(name.getText().toString());

        EditText description = findViewById(R.id.edit_description);
        animal.setDescription(description.getText().toString());

        EditText breed = findViewById(R.id.edit_breed);
        animal.setBreed(breed.getText().toString());

        EditText birthday = findViewById(R.id.edit_birthday);
        if(birthday.getText().toString() != "")
        {
            animal.setDateOfBirth(DateFormatHelper.dateFromString(birthday.getText().toString(), DateFormatHelper.FORMAT_POSTGRES_DATE));
        }

        EditText in_shelter_from = findViewById(R.id.edit_in_shelter_from);
        if(in_shelter_from.getText().toString() != "")
        {
            animal.setInShelterFrom(DateFormatHelper.dateFromString(in_shelter_from.getText().toString(), DateFormatHelper.FORMAT_POSTGRES_DATE));
        }

        TextView shelter = findViewById(R.id.selected_shelter);
        animal.setShelterName(shelter.getText().toString());

        RadioGroup speciesGroup = findViewById(R.id.species_radio_group);
        int selectedSpeciesId = speciesGroup.getCheckedRadioButtonId();
        RadioButton speciesRadioBtn = findViewById(selectedSpeciesId);

            switch (speciesRadioBtn.getText().toString())
            {
                case DOG:
                    animal.setSpecies(DOG);
                    break;
                case CAT:
                    animal.setSpecies(DOG);
                    break;
            }

            RadioGroup sexRadioGroup = findViewById(R.id.sex_radio_group);
            int selectedSexId = sexRadioGroup.getCheckedRadioButtonId();
            RadioButton sexRadioBtn = findViewById(selectedSexId);

            switch (sexRadioBtn.getText().toString())
            {
                case FEMALE:
                    animal.setSex(FEMALE);
                    break;
                case MALE:
                    animal.setSex(MALE);
                    break;
            }

            RadioGroup sizeRadioGroup = findViewById(R.id.size_radio_group);
            int selectedSizeId = sizeRadioGroup.getCheckedRadioButtonId();
            RadioButton sizeRadioBtn = findViewById(selectedSizeId);

            switch (sizeRadioBtn.getText().toString()) {
                case SMALL:
                    animal.setSize(SMALL);
                    break;
                case MEDIUM:
                    animal.setSize(MEDIUM);
                    break;
                case BIG:
                    animal.setSize(BIG);
                    break;
            }
            if(bitmap != null)
            {
                String encodedPhoto = ImageHelper.encodeBitmap(bitmap);
                animal.setAvatar(encodedPhoto);
            }
        if(id != "" && id != null)
            updateAnimal(animal);
        else
            addAnimal(animal);
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
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_LONG).show();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                if(bitmap != null) {
                    ImageView avatar = (ImageView)findViewById(R.id.animal_avatar);
                    avatar.setImageBitmap(bitmap);
                }
            } catch (IOException e)
            {
            }
        }
    }

    private void updateAnimal(AnimalDetailsModel animal)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Gson gson = new Gson();
        String jsonString = gson.toJson(animal);
        StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");

        Client.update(_context, Api.ANIMAL_ID_URL, stringEntity, animal.getId(), headers.toArray(new Header[headers.size()]), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Intent intent = new Intent(_context, SettingsActivity.class);
                //intent.putExtra("ShelterId", 1);
                //intent.putExtra("UserId", 1);
                //_context.startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.e("Error", res);
            }
        });
    }

    private void addAnimal(AnimalDetailsModel animal)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Gson gson = new Gson();
        String jsonString = gson.toJson(animal);
        StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");

        Client.add(_context, Api.ANIMALS_URL, stringEntity, headers.toArray(new Header[headers.size()]), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Intent intent = new Intent(_context, SettingsActivity.class);
                intent.putExtra("ShelterId", 1);
                intent.putExtra("UserId", 1);
                _context.startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.e("Error", res);
            }
        });
    }

    public void addListenerOnSave()
    {
        FloatingActionButton saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveBtnClick();
            }
        });
    }

    @Override
    public void openMainModule()
    {
        Intent intent = new Intent(EditAnimalActivity.this, NewsBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(EditAnimalActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(EditAnimalActivity.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSearchModule()
    {
        Intent intent = new Intent(EditAnimalActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSheltersModule()
    {
        Intent intent = new Intent(EditAnimalActivity.this, SheltersActivity.class);
        startActivity(intent);
    }


    @Override
    public void openSettingsModule()
    {
        Intent intent = new Intent(EditAnimalActivity.this, SettingsActivity.class);
        int userId = UserService.getInstance().getmUserId();
        int shelterId = UserService.getInstance().getmShelterId();
        intent.putExtra("ShelterId", shelterId);
        intent.putExtra("UserId", userId);
        startActivity(intent);
    }

    @Override
    public void openPhotosModule()
    {
        Intent intent = new Intent(EditAnimalActivity.this, PhotosActivity.class);
        int shelterId = UserService.getInstance().getmShelterId();
        intent.putExtra("ShelterId", shelterId);
        startActivity(intent);
    }
}
