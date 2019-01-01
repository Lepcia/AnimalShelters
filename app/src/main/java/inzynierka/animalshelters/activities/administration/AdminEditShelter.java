package inzynierka.animalshelters.activities.administration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.NewsBoardActivity;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.animalShelters.SheltersActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;
import inzynierka.animalshelters.helpers.DateFormatHelper;
import inzynierka.animalshelters.models.AnimalShelterModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class AdminEditShelter extends BasicActivity {

    Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._context = _context;
        setContentView(R.layout.activity_admin_edit_shelter);
        onCreateDrawer();
        onCreateDrawerMenu();
        addListenerOnSave();

        Bundle bundle = getIntent().getExtras();
        if(bundle.getInt("ShelterId") > 0)
        {
            getShelterById(bundle.getInt("ShelterId"));
        }
    }

    private void getShelterById(int shelterId)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(_context, Api.ANIMAL_SHELTER_ID_URL, shelterId, headers.toArray(new Header[headers.size()]),
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
                TextView userId = findViewById(R.id.shelter_id);
                userId.setText(String.valueOf(data.getInt("id")));
            }

            if(data.has("name")) {
                EditText user_name = findViewById(R.id.edit_name);
                user_name.setText(data.getString("name"));
            }

            if(data.has("phone")) {
                EditText user_surname = findViewById(R.id.edit_phone);
                user_surname.setText(data.getString("phone"));
            }

            if(data.has("email")) {
                EditText user_email = findViewById(R.id.edit_email);
                user_email.setText(data.getString("email"));
            }

            if(data.has("street")) {
                EditText user_name = findViewById(R.id.edit_street);
                user_name.setText(data.getString("street"));
            }

            if(data.has("city")) {
                EditText user_surname = findViewById(R.id.edit_city);
                user_surname.setText(data.getString("city"));
            }

            if(data.has("number")) {
                EditText user_email = findViewById(R.id.edit_street_number);
                user_email.setText(data.getString("number"));
            }

            if(data.has("postalCode")) {
                EditText user_email = findViewById(R.id.edit_postal);
                user_email.setText(data.getString("postalCode"));
            }

        }
        catch(JSONException e)
        {
            Log.e("Error", e.getMessage());
        }
    }

    private void addListenerOnSave()
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
        AnimalShelterModel shelter = new AnimalShelterModel();

        TextView idShelter = findViewById(R.id.shelter_id);
        String id = idShelter.getText().toString();
        if(id != "" && id != null ) {
            shelter.setId(Integer.parseInt(id));
        }

        EditText shelterName = findViewById(R.id.edit_name);
        shelter.setName(shelterName.getText().toString());

        EditText shelterEmail = findViewById(R.id.edit_email);
        shelter.setEmail(shelterEmail.getText().toString());

        EditText shelterPhone = findViewById(R.id.edit_phone);
        shelter.setPhone(shelterPhone.getText().toString());

        EditText shelterStreet = findViewById(R.id.edit_street);
        shelter.setStreet(shelterStreet.getText().toString());

        EditText shelterStreetNumber = findViewById(R.id.edit_street_number);
        shelter.setNumber(shelterStreetNumber.getText().toString());

        EditText shelterCity = findViewById(R.id.edit_city);
        shelter.setCity(shelterCity.getText().toString());

        EditText shelterPostal = findViewById(R.id.edit_postal);
        shelter.setPostalCode(shelterPostal.getText().toString());

        if(id != "" && id != null)
        {
            updateShelter(shelter);
        }
        else addShelter(shelter);

    }

    private void addShelter(AnimalShelterModel shelter)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Gson gson = new Gson();
        String jsonString = gson.toJson(shelter);
        StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");

        Client.add(_context, Api.ANIMAL_SHELTERS_URL, stringEntity, headers.toArray(new Header[headers.size()]), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Intent intent = new Intent(_context, AdminActivity.class);
                _context.startActivity(intent);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.e("Error", res);
            }
        });
    }

    private void updateShelter(AnimalShelterModel shelter)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Gson gson = new Gson();
        String jsonString = gson.toJson(shelter);
        StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");

        Client.update(_context, Api.ANIMAL_SHELTER_ID_URL, stringEntity, shelter.getId(), headers.toArray(new Header[headers.size()]), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Intent intent = new Intent(_context, AdminActivity.class);
                _context.startActivity(intent);
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
        Intent intent = new Intent(AdminEditShelter.this, NewsBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(AdminEditShelter.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSearchModule()
    {
        Intent intent = new Intent(AdminEditShelter.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAnimalsModule()
    {
        Intent intent = new Intent(AdminEditShelter.this, AnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSheltersModule()
    {
        Intent intent = new Intent(AdminEditShelter.this, SheltersActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(AdminEditShelter.this, AdminActivity.class);
        startActivity(intent);
    }
}
