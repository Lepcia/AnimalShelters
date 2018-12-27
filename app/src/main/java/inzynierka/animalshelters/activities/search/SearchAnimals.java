package inzynierka.animalshelters.activities.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.animals.AnimalActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.adapters.AnimalListItemAdapter;
import inzynierka.animalshelters.helpers.AdministrationHelper;
import inzynierka.animalshelters.models.AnimalDetailsModel;
import inzynierka.animalshelters.models.AnimalSearchModel;
import inzynierka.animalshelters.models.AnimalShelterSimpleModel;
import inzynierka.animalshelters.rest.Api;
import inzynierka.animalshelters.rest.Client;

public class SearchAnimals extends Fragment {

    private ListView animalView;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search_animals, container, false);
        Toolbar toolbar = (Toolbar)rootView.findViewById(R.id.searchToolbar);
        ((BasicActivity)getActivity()).setSupportActionBar(toolbar);
        searchPanelInit();
        getAnimals();
        initButtons();

        return rootView;
    }

    private void searchPanelInit()
    {
        ImageButton expandBtn = (ImageButton)rootView.findViewById(R.id.expandBtn);

        expandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout searchPanel = (ConstraintLayout)rootView.findViewById(R.id.searchPanel);
                if(searchPanel.getVisibility() == ConstraintLayout.VISIBLE)
                {
                    searchPanel.setVisibility(ConstraintLayout.GONE);
                } else if(searchPanel.getVisibility() == ConstraintLayout.GONE)
                {
                    searchPanel.setVisibility(ConstraintLayout.VISIBLE);
                }
            }
        });

        getSimpleShelters();
    }

    private void getSimpleShelters()
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.get(getContext(), Api.ANIMAL_SHELTERS_SIMPLE_URL, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        List<String> sheltersNames = new ArrayList<String>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                AnimalShelterSimpleModel shelter = new AnimalShelterSimpleModel(response.getJSONObject(i));
                                sheltersNames.add(shelter.getName());
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        Spinner spinner = rootView.findViewById(R.id.search_shelters);
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
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

    public void addListenerOnSpinnerItemSelection(){
        Spinner spinner = rootView.findViewById(R.id.search_shelters);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView selected = rootView.findViewById(R.id.selected_shelter);
                selected.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                TextView selected = rootView.findViewById(R.id.selected_shelter);
                selected.setText("");
            }
        });
    }

    private void initButtons()
    {
        Button searchBtn = (Button) rootView.findViewById(R.id.search_animals_btn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchAnimals();
            }
        });
    }

    private void SearchAnimals()
    {
        AnimalSearchModel animal = new AnimalSearchModel();
        EditText searchName = rootView.findViewById(R.id.search_name);
        animal.setName(searchName.getText().toString());

        TextView searchShelter = rootView.findViewById(R.id.selected_shelter);
        animal.setShelterName(searchShelter.getText().toString());

        EditText searchBreed = rootView.findViewById(R.id.search_breed);
        animal.setBreed(searchBreed.getText().toString());

        RadioGroup sexRadioGroup = rootView.findViewById(R.id.sexRadioGroup);
        int selectedSexId = sexRadioGroup.getCheckedRadioButtonId();
        View sexRadioButton = sexRadioGroup.findViewById(selectedSexId);
        RadioButton sexRadioBtn =(RadioButton) sexRadioGroup.getChildAt(sexRadioGroup.indexOfChild(sexRadioButton));
        switch (sexRadioBtn.getId())
        {
            case R.id.radioSexMale:
                animal.setSex("Male");
                break;
            case R.id.radioSexFemale:
                animal.setSex("Female");
                break;
        };

        RadioGroup speciesRadioGroup = rootView.findViewById(R.id.speciesRadioGroup);
        int selectedSpeciesId = speciesRadioGroup.getCheckedRadioButtonId();
        View speciesRadioButton = speciesRadioGroup.findViewById(selectedSpeciesId);
        RadioButton speciesRadioBtn = (RadioButton) speciesRadioGroup.getChildAt(speciesRadioGroup.indexOfChild(speciesRadioButton));
        switch (speciesRadioBtn.getId()){
            case R.id.radioCat:
                animal.setSpecies("Cat");
                break;
            case R.id.radioDog:
                animal.setSpecies("Dog");
                break;
        }

        RadioGroup accuracyRadioGroup = rootView.findViewById(R.id.accuracyRadioGroup);
        int selectedAccuracyId = accuracyRadioGroup.getCheckedRadioButtonId();
        View accuracyRadioButton = accuracyRadioGroup.findViewById(selectedAccuracyId);
        RadioButton accuracyRadioBtn = (RadioButton) accuracyRadioGroup.getChildAt(accuracyRadioGroup.indexOfChild(accuracyRadioButton));
        switch ((accuracyRadioBtn.getId())){
            case R.id.radioWeeks:
                animal.setAgeAccuracy("Weeks");
                break;
            case R.id.radioMonths:
                animal.setAgeAccuracy("Months");
                break;
            case R.id.radioYears:
                animal.setAgeAccuracy("Years");
                break;
        }

        EditText ageFrom = rootView.findViewById(R.id.search_age_from);
        String ageFromS = ageFrom.getText().toString().isEmpty() ? "0" : ageFrom.getText().toString();
        animal.setAgeFrom(Integer.parseInt(ageFromS));

        EditText ageTo = rootView.findViewById(R.id.search_age_to);
        String ageToS = ageTo.getText().toString().isEmpty() ? "1000000" : ageTo.getText().toString();
        animal.setAgeTo(Integer.parseInt(ageToS));

        Gson gson = new Gson();
        String jsonString = gson.toJson(animal);
        StringEntity stringEntity = new StringEntity(jsonString, "UTF-8");

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.add(getContext(), Api.ANIMALS_SEARCH, stringEntity, 1, headers.toArray(new Header[headers.size()]), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                ArrayList<AnimalDetailsModel> animalsArray = new ArrayList<>();
                AnimalListItemAdapter animalListItemAdapter = new AnimalListItemAdapter(getContext(), animalsArray);

                for (int i = 0; i < response.length(); i++) {
                    try {
                        AnimalDetailsModel animal = new AnimalDetailsModel(response.getJSONObject(i));
                        animalListItemAdapter.add(animal);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                animalView = (ListView) rootView.findViewById(R.id.animalsList);
                animalView.setAdapter(animalListItemAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.e("Error", res);
            }
        });

    }

    private void getAnimals()
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.getById(getContext(), Api.ANIMALS_BY_USER, 1, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        ArrayList<AnimalDetailsModel> animalsArray = new ArrayList<>();
                        AnimalListItemAdapter animalListItemAdapter = new AnimalListItemAdapter(getContext(), animalsArray);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                AnimalDetailsModel animal = new AnimalDetailsModel(response.getJSONObject(i));
                                animalListItemAdapter.add(animal);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        animalView = (ListView) rootView.findViewById(R.id.animalsList);
                        animalView.setAdapter(animalListItemAdapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Log.e("Error", res);
                    }
                });
    }
}
