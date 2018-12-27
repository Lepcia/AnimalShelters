package inzynierka.animalshelters.activities.administration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.NewsBoardActivity;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.animalShelters.SheltersActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;
import inzynierka.animalshelters.helpers.DateFormatHelper;
import inzynierka.animalshelters.models.AnimalShelterSimpleModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class AdminEditUser extends BasicActivity {

    private static Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _context = this;
        setContentView(R.layout.activity_admin_edit_user);
        onCreateDrawer();
        onCreateDrawerMenu();
        getSimpleShelters();

        Bundle bundle = getIntent().getExtras();
        if(bundle.getInt("UserId") > 0)
        {
            getUserById(bundle.getInt("UserId"));
        }
    }

    private void getUserById(int id)
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(_context, Api.USER_ID_DETAILS, id, headers.toArray(new Header[headers.size()]),
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
    //TODO: user type
    private void setFormData(JSONObject data)
    {
        try {
            if(data.has("id")) {
                TextView userId = findViewById(R.id.user_id);
                userId.setText(String.valueOf(data.getInt("id")));
            }

            if(data.has("firstName")) {
                EditText user_name = findViewById(R.id.edit_name);
                user_name.setText(data.getString("firstName"));
            }

            if(data.has("lastName")) {
                EditText user_surname = findViewById(R.id.edit_surname);
                user_surname.setText(data.getString("lastName"));
            }

            if(data.has("email")) {
                EditText user_email = findViewById(R.id.edit_email);
                user_email.setText(data.getString("email"));
            }

            if(data.has("dateOfBirth")) {
                Date dateOfBirth = DateFormatHelper.dateFromString(data.getString("dateOfBirth"), DateFormatHelper.FORMAT_POSTGRES_DATE);
                String dateString = DateFormatHelper.stringFromDate(dateOfBirth, DateFormatHelper.FORMAT_POSTGRES_DATE);

                EditText user_birthday = findViewById(R.id.edit_birthday);
                user_birthday.setText(dateString);
            }

            JSONObject animalShelter = data.getJSONObject("userToAnimalShelter");
            if(animalShelter != null) {
                String shelterName = animalShelter.getString("name");
                Spinner spinner = findViewById(R.id.edit_user_shelter);
                spinner.setSelection(((ArrayAdapter)spinner.getAdapter()).getPosition(shelterName));
            }

        }
        catch(JSONException e)
        {
            Log.e("Error", e.getMessage());
        }
    }

    private void getSimpleShelters()
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.get(_context, Api.ANIMAL_SHELTERS_SIMPLE_URL, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        List<String> sheltersNames = new ArrayList<String>();
                        sheltersNames.add("");
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                AnimalShelterSimpleModel shelter = new AnimalShelterSimpleModel(response.getJSONObject(i));
                                sheltersNames.add(shelter.getName());
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        Spinner spinner = findViewById(R.id.edit_user_shelter);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(_context,
                                android.R.layout.simple_spinner_item, sheltersNames);
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

    //TODO: save/update user
    private void onSaveBtnClick()
    {

    }

    public void addListenerOnSpinnerItemSelection(){
        Spinner spinner = findViewById(R.id.edit_user_shelter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selected = findViewById(R.id.selected_shelter);
                selected.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                TextView selected = findViewById(R.id.selected_shelter);
                selected.setText("");
            }
        });
    }


    @Override
    public void openMainModule()
    {
        Intent intent = new Intent(AdminEditUser.this, NewsBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(AdminEditUser.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSearchModule()
    {
        Intent intent = new Intent(AdminEditUser.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAnimalsModule()
    {
        Intent intent = new Intent(AdminEditUser.this, AnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSheltersModule()
    {
        Intent intent = new Intent(AdminEditUser.this, SheltersActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(AdminEditUser.this, AdminActivity.class);
        startActivity(intent);
    }
}
