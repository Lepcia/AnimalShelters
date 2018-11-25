package inzynierka.animalshelters.activities.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.administration.UserListActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.adapters.AnimalListItemAdapter;
import inzynierka.animalshelters.adapters.UserListItemAdapter;
import inzynierka.animalshelters.models.AnimalModel;
import inzynierka.animalshelters.models.AnimalSearchModel;
import inzynierka.animalshelters.models.UserModel;
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
        getAnimals();
        searchPanelInit();
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

        EditText searchBreed = rootView.findViewById(R.id.search_breed);
        animal.setBreed(searchBreed.getText().toString());

        RadioGroup sexRadioGroup = rootView.findViewById(R.id.sexRadioGroup);
        int selectedId = sexRadioGroup.getCheckedRadioButtonId();
        switch (selectedId)
        {
            case R.id.radioSexMale:
                animal.setSex("Male");
                break;
            case R.id.radioSexFemale:
                animal.setSex("Female");
                break;
        };

        RadioGroup speciesRadioGroup = rootView.findViewById(R.id.speciesRadioGroup);
        selectedId = speciesRadioGroup.getCheckedRadioButtonId();
        switch (selectedId){
            case R.id.radioCat:
                animal.setSpecies("Cat");
                break;
            case R.id.radioDog:
                animal.setSpecies("Dog");
                break;
        }

        EditText ageFrom = rootView.findViewById(R.id.search_age_from);
        animal.setAgeFrom(Integer.parseInt(ageFrom.getText().toString()));

        EditText ageTo = rootView.findViewById(R.id.search_age_to);
        animal.setAgeTo(Integer.parseInt(ageTo.getText().toString()));



    }

    private void getAnimals()
    {
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Content-Type", "application/json"));

        Client.get(getContext(), Api.ANIMALS_ALL_URL, headers.toArray(new Header[headers.size()]),
                null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        ArrayList<AnimalModel> animalsArray = new ArrayList<>();
                        AnimalListItemAdapter animalListItemAdapter = new AnimalListItemAdapter(getContext(), animalsArray);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                animalListItemAdapter.add(new AnimalModel(response.getJSONObject(i)));
                                animalListItemAdapter.add(new AnimalModel(response.getJSONObject(i)));
                                animalListItemAdapter.add(new AnimalModel(response.getJSONObject(i)));
                                animalListItemAdapter.add(new AnimalModel(response.getJSONObject(i)));
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
